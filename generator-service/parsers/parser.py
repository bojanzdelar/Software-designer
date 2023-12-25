from abc import ABC, abstractmethod


class Parser(ABC):
    """
    Interface contract for parsing
    """

    @abstractmethod
    def parse():
        pass
