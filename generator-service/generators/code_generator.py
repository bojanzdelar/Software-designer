from abc import ABC, abstractmethod


class CodeGenerator(ABC):
    """
    Interface contract for code generation
    """

    @abstractmethod
    def generate_code():
        pass

    @abstractmethod
    def generate_classes():
        pass

    @abstractmethod
    def generate_properties():
        pass

    @abstractmethod
    def get_associations():
        pass

    @abstractmethod
    def get_aggregation_children():
        pass

    @abstractmethod
    def get_aggregation_parents():
        pass

    @abstractmethod
    def get_composition_children():
        pass

    @abstractmethod
    def get_composition_parents():
        pass

    @abstractmethod
    def get_parent_properties():
        pass

    @abstractmethod
    def generate_constructors():
        pass

    @abstractmethod
    def generate_methods():
        pass

    @abstractmethod
    def get_abstract_methods():
        pass

    @abstractmethod
    def generate_files():
        pass
