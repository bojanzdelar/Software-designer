import json


def read_file(file_name):
    with open(file_name, "r") as f:
        return f.read()


def save_to_file(file_name, data):
    with open(file_name, "w") as f:
        f.write(data)


def json_to_file(file_name, data):
    save_to_file(file_name, json.dumps(data, indent=2))
