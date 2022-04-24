package homeworks.b;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Main
 */
public class Main {

    private static final int FILE_NOT_FOUND = 1;
    private static final String OUTPUT_FILENAME = ".\\output.xml";

    public static void main(String[] args) {
        String inputFilename;
        String settingsFilename;
        Node someArray;
        Node someValue;
        List<Element> elementsToSort;
        File settingsFile;
        File inputFile;
        Document dataDocument;

        try (Scanner input = new Scanner(System.in)) {
            settingsFilename = input.nextLine();
            inputFilename = input.nextLine();
        }

        try {
            settingsFile = new File(settingsFilename);
            inputFile = new File(inputFilename);
            if (!settingsFile.exists()) {
                System.out.println("Файл с настройками не был найден. Проверьте правильность введенного пути.");
                System.exit(FILE_NOT_FOUND);
            }
            if (!inputFile.exists()) {
                System.out.println("Файл с данными не был найден. Проверьте правильность введенного пути.");
                System.exit(FILE_NOT_FOUND);
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document settingsDocument = dBuilder.parse(settingsFile);
            settingsDocument.getDocumentElement().normalize();
            someArray = settingsDocument.getElementsByTagName("array").item(0).getAttributes()
                    .getNamedItem("name");
            someValue = settingsDocument.getElementsByTagName("attributeName").item(0).getAttributes()
                    .getNamedItem("value");

            dataDocument = dBuilder.parse(inputFile);
            dataDocument.getDocumentElement().normalize();

            NodeList arraysToSort = dataDocument.getElementsByTagName(someArray.getTextContent());

            // на случай, если тегов c названием "someArray" больше одного
            for (int i = 0; i < arraysToSort.getLength(); i++) {
                Node nodeWithArrayToSort = arraysToSort.item(i);

                if (nodeWithArrayToSort.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementWithArrayToSort = (Element) nodeWithArrayToSort;
                    // получаем список элементов, которые собственно надо отсортировать
                    NodeList arrayToSort = elementWithArrayToSort.getChildNodes();

                    elementsToSort = new ArrayList<>();
                    for (int j = 0; j < arrayToSort.getLength(); j++) {
                        if (arrayToSort.item(j).getNodeType() == Node.ELEMENT_NODE) {
                            elementsToSort.add((Element) arrayToSort.item(j));
                        }
                        // nodeWithArrayToSort.removeChild(arrayToSort.item(j));
                    }

                    Collections.sort(elementsToSort,
                            (Element e1, Element e2) -> e1.getAttribute(someValue.getTextContent())
                                    .compareTo(e2.getAttribute(someValue.getTextContent())));

                    for (int j = 0; j < elementsToSort.size(); j++) {
                        // nodeWithArrayToSort.appendChild(elementsToSort.get(j));
                        nodeWithArrayToSort.replaceChild(elementsToSort.get(j), arrayToSort.item(j));
                    }
                }
            }

            File outputFile = new File(OUTPUT_FILENAME);
            StreamResult result = new StreamResult(outputFile);

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperties(new Properties(new Properties()));

            DOMSource source = new DOMSource(dataDocument);

            transformer.transform(source, result);

        } catch (ParserConfigurationException e) {
            // TODO: handle exception
        } catch (SAXException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        } catch (TransformerConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}