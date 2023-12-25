from decoders.decoder import Decoder
from parsers.style_parser import StyleParser
from parsers.syntax_parser import SyntaxParser
from generators.java_generator import JavaCodeGenerator
from utilities.utility import read_file, save_to_file, json_to_file


encoded_xml = read_file("examples/example_diagram.drawio")

decoded_xml = Decoder().convert(encoded_xml)
save_to_file("examples/example_diagram.xml", decoded_xml)

style_tree = StyleParser().parse(decoded_xml)
json_to_file("examples/example_style_tree.json", style_tree)

syntax_tree = SyntaxParser().parse(style_tree)
json_to_file("examples/example_syntax_tree.json", syntax_tree)

JavaCodeGenerator().generate_code(syntax_tree, "examples/code")
