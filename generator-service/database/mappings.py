class_type_map = {
    "class": 1,
    "abstract": 2,
    "interface": 3,
}

access_type_map = {
    "private": 1,
    "protected": 2,
    "public": 3,
}

relationship_type_map = {
    "extends": 1,
    "implements": 2,
    "association": 3,
    "aggregation": 4,
    "composition": 5,
    "inner": 6,
}

boolean_map = {
    "false": 0,
    "true": 1,
}


def invert_map(map):
    return {v: k for k, v in map.items()}
