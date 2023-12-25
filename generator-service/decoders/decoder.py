import traceback
from urllib.parse import unquote
import zlib
from bs4 import BeautifulSoup as bs
import base64


class Decoder:
    """
    Decode and decompress the DrawIO XML
    """

    def convert(self, encoded_xml):
        """
        References:
          https://drawio-app.com/extracting-the-xml-from-mxfiles/
          https://github.com/pzl/drawio-read/blob/master/read.py

        Convert the encoded DrawIO file content to raw XML

        Paramters:
          encoded_xml: encoded .drawio file content

        Returns:
          decoded_xml: decode and decompressed xml
        """

        try:
            drawio_file_raw = bs(encoded_xml, "lxml")
            diagram_tag = drawio_file_raw.find("diagram")
            diagram_tag_text = base64.b64decode(diagram_tag.text)

            decoded_xml = unquote(zlib.decompress(diagram_tag_text, -15).decode("utf8"))

            return decoded_xml

        except Exception as e:
            traceback.print_exc()
            print(f"DecodeAndDecompress.convert ERROR: {e}")
            return False
