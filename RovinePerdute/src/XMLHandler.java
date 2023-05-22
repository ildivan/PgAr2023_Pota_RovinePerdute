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
        List<String> names = cityMap.get("name");
        List<String> xs = cityMap.get("x");
        List<String> ys = cityMap.get("y");
        List<String> hs = cityMap.get("h");

        ArrayList<City> cities = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            cities.add(new City(
                    Integer.parseInt(ids.get(i)),
                    names.get(i),
                    Integer.parseInt(xs.get(i)),
                    Integer.parseInt(ys.get(i)),
                    Integer.parseInt(hs.get(i))
            ));
        }

        Map<String, List<String>> links = readLinksBetweenCities(filepath);
        for (String id : links.keySet()){
            City city = getCityFromId(cities,Integer.parseInt(id));
            for (String linkedCityId : links.get(id)){
                city.addConnection(getCityFromId(cities,Integer.parseInt(linkedCityId)));
            }
        }

        return cities;
    }

    public static City getCityFromId(List<City> cities, int id){
        for (City city : cities){
            if(city.getId() == id){
                return city;
            }
        }
        throw new CityNotFoundException();
    }

    public static Map<String, List<String>> readLinksBetweenCities(String filepath)
            throws FileNotFoundException, XMLStreamException {
        XMLStreamReader xmlr;
        xmlr = xmlif.createXMLStreamReader(filepath,
                new FileInputStream(filepath));

        Map<String, List<String>> links = new HashMap<>();

        String lastId = "0";
        while(xmlr.hasNext()) {
            if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) {
                for (int i = 0; i < xmlr.getAttributeCount(); i++) {
                    if(xmlr.getAttributeLocalName(i).equals("id")){
                        lastId = xmlr.getAttributeValue(i);
                        links.put(lastId,new ArrayList<>());
                    } else if (xmlr.getAttributeLocalName(i).equals("to")) {
                        links.get(lastId).add(xmlr.getAttributeValue(i));
                    }
                }
            }
            xmlr.next();
        }

        return links;
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

        while(xmlr.hasNext()) {
            if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) {
                for (int i = 0; i < xmlr.getAttributeCount(); i++) {
                    String attribute = xmlr.getAttributeLocalName(i);
                    String value = xmlr.getAttributeValue(i);
                    if(data.get(attribute) != null){
                        data.get(attribute).add(value);
                    }
                }
            }
            xmlr.next();
        }

        return data;
    }
}
