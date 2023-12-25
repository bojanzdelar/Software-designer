import traceback
from parsers.parser import Parser


class SyntaxParser(Parser):
    """
    Parse the style tree into the syntax tree
    """

    def parse(self, style_tree):
        """
        Convert the style tree to sytnax tree

        Parameters:
          style_tree: style tree of the drawio file

        Returns:
          syntax_tree: the syntax tree that is used by the generators
        """

        print("<<< CONVERTING STYLE TREE TO SYNTAX TREE >>>")

        try:
            syntax_tree = dict()
            cells = style_tree["root"]["cells"]
            relationships = style_tree["root"]["relationships"]
            parent = style_tree["root"]["id"]

            propertiesDone = False

            _id = 0
            for key, value in cells.items():
                if (
                    value["parent_id"] in relationships.keys()
                    or "endArrow" in value["style"].keys()
                ):
                    # skip the label for relationships
                    continue

                if (
                    value["parent_id"] == parent
                    and value["style"]["type"].lower() == "swimlane"
                    or value["style"]["type"].lower() == "html"
                ):
                    # start of a new cell
                    syntax_tree[key] = self._tree_template(value)
                    propertiesDone = False
                    _id = 0
                else:
                    # properties and methods in the cell
                    if (
                        value["style"]["type"].lower() == "line"
                        and value["parent_id"] in syntax_tree.keys()
                    ):  # line seperating the properties and methods
                        propertiesDone = True
                        _id = 0
                    else:
                        if not propertiesDone:  # properties
                            syntax_tree[value["parent_id"]]["properties"] = {
                                **syntax_tree[value["parent_id"]]["properties"],
                                **self._properties_template(value, _id),
                            }
                            _id += len(value["values"])
                        else:  # methods
                            syntax_tree[value["parent_id"]]["methods"] = {
                                **syntax_tree[value["parent_id"]]["methods"],
                                **self._methods_template(value, _id),
                            }
                            _id += len(value["values"])

            for relationship in relationships.keys():
                self._add_relationships(syntax_tree, relationships[relationship])

            return syntax_tree
        except Exception as e:
            traceback.print_exc()
            print(f"SyntaxParser.parse ERROR: {e}")
            return False

    def _tree_template(self, main_cell):
        """
        Create the template that will house each cell

        Parameters:
          main_cell: the starting, parent cell

        Returns:
          template: the starting template (dictionary)
        """

        template = {
            "type": "class",
            "inner": "false",
            "name": main_cell["values"][0] if len(main_cell["values"]) > 0 else "",
            "properties": {},
            "methods": {},
            "relationships": {
                "implements": [],
                "extends": [],
                "association": [],
                "aggregationChildren": [],
                "aggregationParents": [],
                "compositionChildren": [],
                "compositionParents": [],
                "inner": [],
            },
        }

        if main_cell["style"]["type"] == "html":
            values_length = len(main_cell["values"])
            name = main_cell["values"][0] if values_length > 0 else None
            properties = {
                "values": main_cell["values"][1] if values_length > 1 else None
            }
            methods = {"values": main_cell["values"][2] if values_length > 2 else None}

            template["name"] = name[0]
            template["properties"] = (
                self._properties_template(properties, 0) if not None else []
            )
            template["methods"] = self._methods_template(methods, 0) if not None else []

        if "fontStyle" in main_cell["style"] and main_cell["style"]["fontStyle"] == "2":
            # if the fontStyle is italic, then it is an abstract class
            template["type"] = "abstract"
        elif template["name"].lower().startswith("<<interface>>"):
            template["type"] = "interface"
            template["name"] = template["name"][13:].strip()

        return template

    def _properties_template(self, property_dict, _id):
        """
        Create the template for properties

        Parameters:
          property_dict: the properties dictionary from the style tree
          _id: id for the keys in the dictionary

        Returns:
          template: the properties tempate (dictionary)
        """

        values = property_dict["values"]
        template = dict()

        for val in values:
            if len(val) == 0:
                continue

            _id += 1

            val = val.strip()
            access_modifier_symbol = val[0]
            temp_val = val[1:].split(":")

            template[_id] = {
                "access": self._get_access_modifier(access_modifier_symbol),
                "name": temp_val[0].strip(),
                "type": temp_val[1].strip(),
            }

        return template

    def _methods_template(self, method_dict, _id):
        """
        Create the template for methods

        Parameters:
          method_dict: the methods dictionary from the style tree
          _id: id for the keys in the dictionary

        Returns:
          template: the methods tempate (dictionary)
        """

        values = method_dict["values"]
        styles = method_dict["style"]

        template = dict()

        for val in values:
            if len(val) == 0:
                continue

            _id += 1

            val = val.strip()
            access_modifier_symbol = val[0]
            val = val[1:]  # remove the access modifier symbol

            parameters = val[val.find("(") + 1 : val.find(")")].split(",")
            # check if there are no parameters
            if len(parameters[0]) == 0:
                parameters = []
            else:
                parameters = [
                    (p.split(":")[0].strip(), p.split(":")[1].strip())
                    for p in parameters
                ]
                parameters = [{"name": p[0], "type": p[1]} for p in parameters]

            template[_id] = {
                "access": self._get_access_modifier(access_modifier_symbol),
                "abstract": "true"
                if "fontStyle" in styles and styles["fontStyle"] == "2"
                else "false",
                "name": val[: val.find("(")].strip(),
                "parameters": {(i + 1): parameters[i] for i in range(len(parameters))},
                "return_type": val[val.rfind(":") + 1 :].strip(),
            }

        return template

    def _get_access_modifier(self, symbol):
        """
        Return the access modifier

        Parameters:
          symbol: symbol representing the access modifier

        Returns:
          text: the text of the access modifier symbol
        """

        access_modifier_dict = {"+": "public", "#": "protected", "-": "private"}

        return access_modifier_dict[symbol]

    def _add_relationships(self, syntax_tree, relationship):
        """
        Add the relationship for the cells in the syntax tree

        Parameters:
          syntax_tree: syntax tree to add the relationship to
          relationship: relationship to be added to the syntax tree
        """

        source = relationship["source"]
        target = relationship["target"]
        style = relationship["style"]

        source_cell = syntax_tree[source]
        target_cell = syntax_tree[target]

        if "endArrow" in style.keys() and (
            style["endArrow"].lower() == "block" or style["endArrow"].lower() == "none"
        ):
            if style["endArrow"].lower() == "none" or style["endFill"].lower() == "1":
                # association
                source_cell["relationships"]["association"] += [target]
                target_cell["relationships"]["association"] += [source]
            elif "dashed" in style.keys() and style["dashed"] == "1":
                # implements
                source_cell["relationships"]["implements"] += [target]
            else:
                # extends
                source_cell["relationships"]["extends"] += [target]
        elif (
            "endArrow" in style.keys() and style["endArrow"].lower() == "diamondthin"
        ) or (
            "startArrow" in style.keys()
            and style["startArrow"].lower() == "diamondthin"
        ):
            if ("endFill" in style.keys() and style["endFill"] == "1") or (
                "startFill" in style.keys() and style["startFill"] == "1"
            ):
                # composition
                source_cell["relationships"]["compositionChildren"] += [target]
                target_cell["relationships"]["compositionParents"] += [source]
            else:
                # aggregation
                source_cell["relationships"]["aggregationChildren"] += [target]
                target_cell["relationships"]["aggregationParents"] += [source]
        elif (
            "startArrow" in style.keys() and style["startArrow"].lower() == "circleplus"
        ):
            # inner class
            source_cell["relationships"]["inner"] += [target]
            target_cell["inner"] = "true"
