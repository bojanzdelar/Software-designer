from config import mysql
from database.mappings import (
    class_type_map,
    access_type_map,
    relationship_type_map,
    boolean_map,
)


class Repository:
    """
    This class is responsible for saving the data to the database.
    """

    def save_classes(self, diagram_id, syntax_tree):
        """
        Save the classes to the database

        Parameters:
            diagram_id: the id of the diagram
            syntax_tree: the syntax tree of the diagram
        """

        db = mysql.get_db()
        cursor = db.cursor()

        cursor.execute("DELETE FROM class WHERE diagram_id = %s", (diagram_id,))

        for class_id, class_data in syntax_tree.items():
            cursor.execute(
                "INSERT INTO class (id, class_type_id, name_, inner_class, diagram_id) VALUES (%s, %s, %s, %s, %s)",
                (
                    class_id,
                    class_type_map[class_data["type"]],
                    class_data["name"],
                    boolean_map[class_data["inner"]],
                    diagram_id,
                ),
            )

            for _, property_data in class_data["properties"].items():
                cursor.execute(
                    "INSERT INTO property (access_type_id, name_, type_, class_id) VALUES (%s, %s, %s, %s)",
                    (
                        access_type_map[property_data["access"]],
                        property_data["name"],
                        property_data["type"],
                        class_id,
                    ),
                )

            for _, method_data in class_data["methods"].items():
                cursor.execute(
                    "INSERT INTO method (access_type_id, abstract, name_, return_type, class_id) VALUES (%s,  %s, %s, %s, %s)",
                    (
                        access_type_map[method_data["access"]],
                        boolean_map[method_data["abstract"]],
                        method_data["name"],
                        method_data["return_type"],
                        class_id,
                    ),
                )
                method_id = cursor.lastrowid

                for _, parameter_data in method_data["parameters"].items():
                    cursor.execute(
                        "INSERT INTO parameter (name_, type_, method_id) VALUES (%s, %s, %s)",
                        (parameter_data["name"], parameter_data["type"], method_id),
                    )

        # Create relationships after all classes have been created
        for class_id, class_data in syntax_tree.items():
            for class_in_relationship in class_data["relationships"]["implements"]:
                cursor.execute(
                    "INSERT INTO relationship (relationship_type_id, parent_class_id, child_class_id) VALUES (%s, %s, %s)",
                    (
                        relationship_type_map["implements"],
                        class_in_relationship,
                        class_id,
                    ),
                )

            for class_in_relationship in class_data["relationships"]["extends"]:
                cursor.execute(
                    "INSERT INTO relationship (relationship_type_id, parent_class_id, child_class_id) VALUES (%s, %s, %s)",
                    (relationship_type_map["extends"], class_in_relationship, class_id),
                )

            for class_in_relationship in class_data["relationships"]["association"]:
                cursor.execute(
                    "INSERT INTO relationship (relationship_type_id, parent_class_id, child_class_id) VALUES (%s, %s, %s)",
                    (
                        relationship_type_map["association"],
                        class_in_relationship,
                        class_id,
                    ),
                )

            for class_in_relationship in class_data["relationships"][
                "aggregationParents"
            ]:
                cursor.execute(
                    "INSERT INTO relationship (relationship_type_id, parent_class_id, child_class_id) VALUES (%s, %s, %s)",
                    (
                        relationship_type_map["aggregation"],
                        class_in_relationship,
                        class_id,
                    ),
                )

            for class_in_relationship in class_data["relationships"][
                "compositionParents"
            ]:
                cursor.execute(
                    "INSERT INTO relationship (relationship_type_id, parent_class_id, child_class_id) VALUES (%s, %s, %s)",
                    (
                        relationship_type_map["composition"],
                        class_in_relationship,
                        class_id,
                    ),
                )

            for class_in_relationship in class_data["relationships"]["inner"]:
                cursor.execute(
                    "INSERT INTO relationship (relationship_type_id, parent_class_id, child_class_id) VALUES (%s, %s, %s)",
                    (relationship_type_map["inner"], class_id, class_in_relationship),
                )

        db.commit()