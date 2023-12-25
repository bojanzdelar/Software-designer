import os
import shutil
from flask import request, send_file, after_this_request
import py_eureka_client.eureka_client as eureka_client
from config import app, aws_auth
from decoders.decoder import Decoder
from parsers.style_parser import StyleParser
from parsers.syntax_parser import SyntaxParser
from generators.java_generator import JavaCodeGenerator
from database.repository import Repository

server_port = int(os.getenv("SERVER_PORT"))
eureka_host = os.getenv("EUREKA_HOST")
eureka_port = int(os.getenv("EUREKA_PORT"))

eureka_client.init(
    eureka_server=f"http://{eureka_host}:{eureka_port}/eureka",
    app_name="generator-service",
    instance_port=server_port,
)

@app.route("/api/generate-code", methods=["POST"])
@aws_auth.authentication_required
def generate_code():
    @after_this_request
    def _(response):
        shutil.rmtree("temp", ignore_errors=True)
        return response

    try:
        encoded_xml = request.json["data"]
    except:
        return "No data provided", 400

    decoded_xml = Decoder().convert(encoded_xml)
    style_tree = StyleParser().parse(decoded_xml)
    syntax_tree = SyntaxParser().parse(style_tree)
    code = JavaCodeGenerator().generate_code(syntax_tree, "temp/code")

    if not decoded_xml or not style_tree or not syntax_tree or not code:
        return "Bad data provided", 400

    diagram_id = request.json["id"]
    diagram_title = request.json["title"]
    Repository().save_classes(diagram_id, syntax_tree)

    shutil.make_archive("temp/generated-code", "zip", "temp/code")
    return send_file(
        "temp/generated-code.zip",
        as_attachment=True,
        download_name=f"{diagram_title}.zip",
    )