package edu.badpals.magictg.services;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Iterator;
import java.util.Map;

public class DataExporter {


    public static void exportData(String fileName, String format, String data) {
        String exportDir = "exports";
        File dir = new File(exportDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(exportDir).append("/").append(fileName).append(".").append(format.toLowerCase());
        String filePath = sb.toString();

        switch (format) {
            case "JSON":
                saveAsJson(filePath, data);
                break;
            case "XML":
                saveAsXml(filePath, data);
                break;
            case "BIN":
                saveAsBinary(filePath, data);
                break;
            case "TXT":
                saveAsText(filePath, data);
                break;
            default:
                break;
        }
    }

    private static void saveAsXml(String filePath, String data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(data);

            // Crear XML
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            //Crear root
            Element rootElement = doc.createElement("selectedCard");  // Puedes ajustar el nombre del elemento raíz
            doc.appendChild(rootElement);
            buildXML(rootNode, doc, rootElement);

            //guardar el documento XML
            Source source = new DOMSource(doc);
            Result result = new StreamResult(new File(filePath));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);



        }catch (IOException | ParserConfigurationException | TransformerException e){
            e.printStackTrace();

        }
    }
    private static void buildXML(JsonNode node, Document document, Element parent){
        //Crear estructura del XML
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                Element childElement = document.createElement(field.getKey());
                parent.appendChild(childElement);
                //Recursividad para hijos
                buildXML(field.getValue(), document, childElement);
            }
        }else if (node.isArray()){
            for (JsonNode arrayItem: node) {
                    if (arrayItem.isObject()){
                        buildXML(arrayItem, document, parent);
                    } else{
                    Element childElement = document.createElement("value");
                    childElement.setTextContent(arrayItem.asText());
                    parent.appendChild(childElement);
                }
            }
        } else {
            parent.setTextContent(node.asText());
        }
    }


    private static void saveAsJson(String filePath, String data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static void saveAsBinary(String filePath, String data) {
        ObjectMapper om = new ObjectMapper();
        try {
            Object jsonObject = om.readValue(data, Object.class);
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(jsonObject);
            objectOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveAsText(String filePath, String data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
