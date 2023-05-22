import javax.xml.stream.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLHandler {
    private static final XMLInputFactory xmlif = XMLInputFactory.newInstance();
    private static final XMLOutputFactory xmlof = XMLOutputFactory.newInstance();


    public static List<City> getCities(String filepath) throws XMLStreamException, FileNotFoundException {
        String[] attributes = {"size", "id", "name", "x", "y", "h"};
        Map<String, List<String>> cityMap = readFromFile(filepath, attributes);

        List<String> ids = cityMap.get("id");
        List<String> names = cityMap.get("names");
        List<String> xs = cityMap.get("x");
        List<String> ys = cityMap.get("y");
        List<String> hs = cityMap.get("h");

        ArrayList<City> cities = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            cities.add(new City(
                    ids.get(i),
                    names.get(i),
                    xs.get(i),
                    ys.get(i),
                    hs.get(i)
            ));
        }

        return cities;
    }

    //Generic method to read from a XML file given the file path
    // and the list of tags containing data to search for.
    public static Map<String, List<String>> readFromFile(String filepath, String...attributeNames)
            throws FileNotFoundException, XMLStreamException {
        Map<String,List<String>> data = new HashMap<>();
        //Initialize all tags with empty array lists.
        for (String tag : attributeNames) {
            data.put(tag,new ArrayList<>());
        }

        XMLStreamReader xmlr;
        xmlr = xmlif.createXMLStreamReader(filepath,
                new FileInputStream(filepath));

        String lastTag = "";
        while(xmlr.hasNext()) {
            if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) {
                for (int i = 0; i < xmlr.getAttributeCount(); i++) {
                    String attribute = xmlr.getAttributeLocalName(i);
                    String value = xmlr.getAttributeValue(i);
                    data.get(attribute).add(value);
                }
            }
            xmlr.next();
        }

        return data;
    }
}
