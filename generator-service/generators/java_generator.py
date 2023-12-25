import traceback
import re
import os
from generators.code_generator import CodeGenerator


class JavaCodeGenerator(CodeGenerator):
    """
    Generate Java code
    """

    def generate_code(self, syntax_tree, file_path):
        """
        Use the syntax tree to generate code files for the UML class diagrams

        Parameters:
            syntax_tree: syntax_tree of the drawio file
            file_path: path for the code files to be written to
        """

        print("<<< GENERATING CODE FILES FROM SYNTAX TREE >>>")

        files = []

        try:
            # get all classes that are not inner classes
            non_inner = [
                (_id, _class)
                for _id, _class in syntax_tree.items()
                if _class["inner"] == "false"
            ]

            for _, _class in non_inner:
                file = self.generate_classes(_class, syntax_tree)
                files.append([_class["name"], file])

            self.generate_files(file_path.strip("/"), files)
            return True

        except Exception as e:
            traceback.print_exc()
            print(f"JavaCodeGenerator.generate_code ERROR: {e}")
            return False

    def generate_classes(self, _class, syntax_tree, indent=1):
        """
        Generate the class header

        Parameters:
            _class: dictionary of class to be generated
            syntax_tree: syntax tree dictionary
            indent: indentation level

        Returns:
            class_string: class string
        """

        class_string = ""
        tabs = "\t" * (indent - 1)

        inheritance = ""
        if len(_class["relationships"]["extends"]) > 0:
            inheritance += "extends "
            inheritance += ",".join(
                [syntax_tree[r]["name"] for r in _class["relationships"]["extends"]]
            ).strip(",")

        implementation = ""
        if len(_class["relationships"]["implements"]) > 0:
            implementation += "implements "
            implementation += ",".join(
                [syntax_tree[r]["name"] for r in _class["relationships"]["implements"]]
            ).strip(",")

        abstract_methods = list()
        self.get_abstract_methods(
            syntax_tree,
            _class["relationships"]["implements"],
            _class["relationships"]["extends"],
            abstract_methods,
        )
        abstract_methods.reverse()  # reverse the list so that the abstract methods are in the correct order
        abstract_methods = [
            m for i, m in enumerate(abstract_methods) if m not in abstract_methods[:i]
        ]  # remove duplicate abstract methods

        associations = self.get_associations(syntax_tree, _class["relationships"])
        aggregation_children = self.get_aggregation_children(
            syntax_tree, _class["relationships"]
        )
        aggregation_parents = self.get_aggregation_parents(
            syntax_tree, _class["relationships"]
        )
        composition_children = self.get_composition_children(
            syntax_tree, _class["relationships"]
        )
        composition_parents = self.get_composition_parents(
            syntax_tree, _class["relationships"]
        )
        # join all associations
        all_associations = (
            associations
            + aggregation_children
            + aggregation_parents
            + composition_children
            + composition_parents
        )

        type_of_class = "public class" if _class["type"] == "class" else _class["type"]
        type_of_class = (
            _class["type"] + " class" if _class["type"] == "abstract" else type_of_class
        )

        class_string = (
            f"{tabs}{type_of_class} {_class['name']} {inheritance} {implementation}"
            + " {\n"
        )
        class_string = re.sub(" +", " ", class_string)

        class_string += "\n"
        class_string += self.generate_properties(
            _class["properties"], all_associations, indent
        )
        if (
            _class["properties"]
            or _class["relationships"]["association"]
            or _class["relationships"]["aggregationChildren"]
            or _class["relationships"]["aggregationParents"]
            or _class["relationships"]["compositionChildren"]
            or _class["relationships"]["compositionParents"]
        ):
            class_string += "\n"

        if "class" in type_of_class:
            class_string += self.generate_constructors(
                syntax_tree,
                _class["name"],
                _class["properties"],
                all_associations,
                _class["relationships"]["extends"],
                indent,
            )

        class_string += self.generate_methods(
            _class["methods"],
            _class["properties"],
            all_associations,
            _class["type"],
            abstract_methods,
            indent,
        )

        for inner_class_id in _class["relationships"]["inner"]:
            inner_class = syntax_tree[inner_class_id]
            class_string += self.generate_classes(inner_class, syntax_tree, indent + 1)
            class_string += "\n"

        class_string += f"{tabs}}}\n"

        return class_string

    def generate_properties(self, properties, all_asssociations, indent):
        """
        Generate properties for the class

        Parameters:
            properties: dictionary of properties
            all_asssociations: list of associations, aggreatation children, aggregation parents, composition children, composition parents
            indent: indentation level

        Returns:
            properties_string: string of the properties
        """

        properties_string = ""
        tabs = "\t" * indent
        for _, _property_value in properties.items():
            p = f"{tabs}{_property_value['access']} {_property_value['type']} {_property_value['name']};\n"
            properties_string += p

        for association in all_asssociations:
            p = f"{tabs}private {association} {association[0].lower() + association[1:]};\n"
            properties_string += p

        return properties_string

    def get_associations(self, syntax_tree, relationships):
        """
        Get the associations for the class

        Parameters:
            syntax_tree: syntax_tree of the drawio file
            relationships: dictionary of relationships

        Returns:
            associations: list of associations
        """

        associations = list()
        for association in relationships["association"]:
            associations.append(syntax_tree[association]["name"])

        return associations

    def get_aggregation_children(self, syntax_tree, relationships):
        """
        Get the aggregation children for the class

        Parameters:
            syntax_tree: syntax_tree of the drawio file
            relationships: dictionary of relationships

        Returns:
            aggregation_children: list of aggregation children
        """

        aggregation_children = list()
        for aggregation in relationships["aggregationChildren"]:
            aggregation_children.append(syntax_tree[aggregation]["name"])

        return aggregation_children

    def get_aggregation_parents(self, syntax_tree, relationships):
        """
        Get the aggregation parents for the class

        Parameters:
            syntax_tree: syntax_tree of the drawio file
            relationships: dictionary of relationships

        Returns:
            aggregation_parents: list of aggregation parents
        """

        aggregation_parents = list()
        for aggregation in relationships["aggregationParents"]:
            aggregation_parents.append(syntax_tree[aggregation]["name"])

        return aggregation_parents

    def get_composition_children(self, syntax_tree, relationships):
        """
        Get the composition children for the class

        Parameters:
            syntax_tree: syntax_tree of the drawio file
            relationships: dictionary of relationships

        Returns:
            composition_children: list of composition children
        """

        composition_children = list()
        for composition in relationships["compositionChildren"]:
            composition_children.append(syntax_tree[composition]["name"])

        return composition_children

    def get_composition_parents(self, syntax_tree, relationships):
        """
        Get the composition parents for the class

        Parameters:
            syntax_tree: syntax_tree of the drawio file
            relationships: dictionary of relationships

        Returns:
            composition_parents: list of composition parents
        """

        composition_parents = list()
        for composition in relationships["compositionParents"]:
            composition_parents.append(syntax_tree[composition]["name"])

        return composition_parents

    def get_parent_properties(self, syntax_tree, extends, parent_properties):
        """
        Get the properties of the parent class

        Parameters:
            syntax_tree: syntax_tree of the drawio file
            extends: list of classes that the class extends
            parent_properties: list of parent properties

        Returns:
            parent_properties: list of parent properties
        """

        for e in extends:
            properties = list(syntax_tree[e]["properties"].values())
            if properties:
                parent_properties.insert(0, *properties)

            self.get_parent_properties(
                syntax_tree,
                syntax_tree[e]["relationships"]["extends"],
                parent_properties,
            )

        return parent_properties

    def generate_constructors(
        self, syntax_tree, class_name, properties, all_associations, extends, indent
    ):
        """
        Generate constructors for the class

        Parameters:
            syntax_tree: syntax_tree of the drawio file
            class_name: name of the class
            properties: dictionary of properties
            all_asssociations: list of associations, aggreatation children, aggregation parents, composition children, composition parents
            extends: list of classes that the class extends
            indent: indentation level

        Returns:
            constructors_string: string of the constructors
        """

        parent_params = self.get_parent_properties(syntax_tree, extends, list())

        if not properties and not all_associations and not parent_params:
            return ""

        tabs = "\t" * indent
        constructors_string = f"{tabs}public {class_name}() {{}}\n\n"

        child_params = [
            {"type": p["type"], "name": p["name"]} for p in properties.values()
        ] + [{"type": a, "name": a[0].lower() + a[1:]} for a in all_associations]

        parent_params = self.get_parent_properties(syntax_tree, extends, list())

        all_params = parent_params + child_params

        constructor_signature = ", ".join(
            [f"{p['type']} {p['name']}" for p in all_params]
        )

        constructor_body = ""
        if extends:
            super_args = ", ".join([p["name"] for p in parent_params])
            constructor_body = f"\t{tabs}super({super_args});\n"

        constructor_body += "".join(
            [f"\t{tabs}this.{p['name']} = {p['name']}\n" for p in child_params]
        )

        constructors_string += f"{tabs}public {class_name}({constructor_signature}) {{\n{constructor_body}\t}}\n\n"

        return constructors_string

    def generate_methods(
        self,
        methods,
        properties,
        all_asssociations,
        class_type,
        abstract_methods,
        indent,
    ):
        """
        Generate methods for the class

        Parameters:
            methods: dictionary of methods
            properties: dictionary of properties
            all_asssociations: list of associations, aggreatation children, aggregation parents, composition children, composition parents
            class_type: type of current class
            abstract_methods: list of abstract methods
            indent: indentation level

        Returns:
            methods_string: string of the methods
        """

        # getter and setter methods
        methods_string = ""
        tabs = "\t" * indent
        if class_type == "class" or class_type == "abstract":
            # normal properties
            for _, _property_value in properties.items():
                if _property_value["access"] == "private":
                    getter = (
                        f"{tabs}public {_property_value['type']} get{_property_value['name'][0].upper() + _property_value['name'][1:]}()"
                        f" {{\n {tabs}\treturn this.{_property_value['name']}; \n{tabs}}}\n"
                    )
                    methods_string += getter + "\n"

                    setter = (
                        f"{tabs}public void set{_property_value['name'][0].upper() + _property_value['name'][1:]}({_property_value['type']} {_property_value['name']})"
                        f" {{\n {tabs}\tthis.{_property_value['name']} = {_property_value['name']}; \n{tabs}}}\n"
                    )
                    methods_string += setter + "\n"

            # all association properties
            for association in all_asssociations:
                getter = (
                    f"{tabs}public {association} get{association[0].upper() + association[1:]}()"
                    f" {{\n {tabs}\treturn this.{association[0].lower() + association[1:]}; \n{tabs}}}\n"
                )
                methods_string += getter + "\n"

                setter = (
                    f"{tabs}public void set{association[0].upper() + association[1:]}({association} {association[0].lower() + association[1:]})"
                    f" {{\n {tabs}\tthis.{association[0].lower() + association[1:]} = {association[0].lower() + association[1:]}; \n{tabs}}}\n"
                )
                methods_string += setter + "\n"

        # abstract methods
        if class_type == "class":
            for abstract_method in abstract_methods:
                comment = "// TODO: Must be implemented!"
                m = (
                    f"{tabs}{abstract_method['access']} {abstract_method['return_type']} {abstract_method['name']}()"
                    f" {{\n {tabs}\t{comment} \n{tabs}}}\n"
                )
                methods_string += m + "\n"

        # normal methods
        for _, method_value in methods.items():
            if not method_value["parameters"]:
                params = "()"
            else:
                params = (
                    "("
                    + ", ".join(
                        [
                            f"{p['type']} {p['name']}"
                            for p in method_value["parameters"].values()
                        ]
                    )
                    + ")"
                )

            if method_value["abstract"] == "true" and class_type != "interface":
                m = f"{tabs}{method_value['access']} abstract {method_value['return_type']} {method_value['name']}{params};\n"
            elif method_value["abstract"] == "true" and class_type == "interface":
                m = f"{tabs}{method_value['access']} {method_value['return_type']} {method_value['name']}{params};\n"
            else:
                m = f"{tabs}{method_value['access']} {method_value['return_type']} {method_value['name']}{params} {{\n\n{tabs}}}\n"

            methods_string += m + "\n"

        return methods_string

    def get_abstract_methods(self, syntax_tree, implements, extends, abstract_methods):
        """
        Get the abstract methods that require implementation

        Parameters:
            syntax_tree: syntax_tree of the drawio file
            implements: list of interfaces
            extends: list of classes
            abstract_methods: list of interface methods
        """

        for i in implements:
            interface_obj = syntax_tree[i]
            methods = list(
                interface_obj["methods"].values()
            )  # take all methods from interface
            methods.reverse()  # reverse the list so that the methods are in the correct order
            abstract_methods += methods
            self.get_abstract_methods(
                syntax_tree,
                interface_obj["relationships"]["extends"],
                interface_obj["relationships"]["extends"],
                abstract_methods,
            )

        for e in extends:
            class_obj = syntax_tree[e]
            methods = [
                m for m in class_obj["methods"].values() if m["abstract"] == "true"
            ]  # take only abstract methods from class
            methods.reverse()  # reverse the list so that the methods are in the correct order
            abstract_methods += methods
            self.get_abstract_methods(
                syntax_tree,
                class_obj["relationships"]["implements"],
                class_obj["relationships"]["extends"],
                abstract_methods,
            )

    def generate_files(self, file_path, files):
        """
        Write generated code to file

        Parameters:
            file_path: path for the code files to be written to
            files: list of files to be written

        Returns:
            boolean: True if successful, False if unsuccessful
            file_path: path for the code files to be written to
            files: list of files to be written
        """

        print(f"<<< WRITING FILES TO {file_path} >>>")

        try:
            os.makedirs(file_path, exist_ok=True)
            for file in files:
                file_name = file[0] + ".java"
                file_contents = file[1]
                with open(file_path + f"/{file_name}", "w") as f:
                    f.write(file_contents)
        except Exception as e:
            traceback.print_exc()
            print(f"JavaCodeGenerator.generate_files ERROR: {e}")
