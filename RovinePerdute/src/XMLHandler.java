import javax.xml.stream.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

/**
 * Class that handles reading and writing with XML files.
 */
public class XMLHandler {
    private static final XMLInputFactory xmlif = XMLInputFactory.newInstance();
    private static final XMLOutputFactory xmlof = XMLOutputFactory.newInstance();

    /**
     * Reads cities and connections from specified filepath
     */
    public static List<City> getCities(String filepath) throws XMLStreamException, FileNotFoundException {
        String[] attributes = { "size", "id", "name", "x", "y", "h" };
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
                    Integer.parseInt(hs.get(i))));
        }

        Map<String, List<String>> links = readLinksBetweenCities(filepath);
        for (String id : links.keySet()) {
            City city = getCityFromId(cities, Integer.parseInt(id));
            for (String linkedCityId : links.get(id)) {
                city.addConnection(getCityFromId(cities, Integer.parseInt(linkedCityId)));
            }
        }

        return cities;
    }

    /**
     * @param cities A list of cities
     * @param id     The id of the city to find
     * @return The city from the list given its id
     */
    public static City getCityFromId(List<City> cities, int id) {
        for (City city : cities) {
            if (city.getId() == id) {
                return city;
            }
        }
        throw new CityNotFoundException();
    }

    /**
     * Gets the connections between cities as a map that maps ids to lists of ids,
     * Each id is mapped to a list of the ids the city is connected to.
     * 
     * @param filepath Where to look for the links.
     * @return The map containing all connections.
     */
    public static Map<String, List<String>> readLinksBetweenCities(String filepath)
            throws FileNotFoundException, XMLStreamException {
        XMLStreamReader xmlr;
        xmlr = xmlif.createXMLStreamReader(filepath,
                new FileInputStream(filepath));

        Map<String, List<String>> links = new HashMap<>();

        String lastId = "0";
        while (xmlr.hasNext()) {
            if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) {
                for (int i = 0; i < xmlr.getAttributeCount(); i++) {
                    if (xmlr.getAttributeLocalName(i).equals("id")) {
                        lastId = xmlr.getAttributeValue(i);
                        links.put(lastId, new ArrayList<>());
                    } else if (xmlr.getAttributeLocalName(i).equals("to")) {
                        links.get(lastId).add(xmlr.getAttributeValue(i));
                    }
                }
            }
            xmlr.next();
        }

        return links;
    }

    /**
     * Generic method to get all attribute values mapped as lists to their attribute
     * name.
     * 
     * @param filepath       Where to look for attributes.
     * @param attributeNames The attribute names to look for.
     * @return Map of the attribute names and lists of values.
     */
    public static Map<String, List<String>> readFromFile(String filepath, String... attributeNames)
            throws FileNotFoundException, XMLStreamException {
        Map<String, List<String>> data = new HashMap<>();
        // Initialize all tags with empty array lists.
        for (String tag : attributeNames) {
            data.put(tag, new ArrayList<>());
        }

        XMLStreamReader xmlr;
        xmlr = xmlif.createXMLStreamReader(filepath,
                new FileInputStream(filepath));

        while (xmlr.hasNext()) {
            if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) {
                for (int i = 0; i < xmlr.getAttributeCount(); i++) {
                    String attribute = xmlr.getAttributeLocalName(i);
                    String value = xmlr.getAttributeValue(i);
                    if (data.get(attribute) != null) {
                        data.get(attribute).add(value);
                    }
                }
            }
            xmlr.next();
        }

        return data;
    }

    public static void writeOutput(PathFinder p1, PathFinder p2, String filepath){
        XMLStreamWriter xmlw;
        try {
            xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(filepath), "utf-8");
            xmlw.writeStartDocument("utf-8", "1.0");
            xmlw.writeStartElement("routes");

            writeRoute(xmlw, p1, "Tonatiuh");
            writeRoute(xmlw, p2, "Metztli");

            xmlw.writeEndElement();
            xmlw.writeEndDocument();
            xmlw.flush();
            xmlw.close();
        } catch (Exception e) {
            System.out.println("Errore nella scrittura");
        }
    }

    public static void writeRoute(XMLStreamWriter xmlw , PathFinder p, String teamName) throws XMLStreamException {
        xmlw.writeStartElement("route");
        xmlw.writeAttribute("team",teamName);
        xmlw.writeAttribute("cost", Double.toString(p.getPathCost()));
        xmlw.writeAttribute("cities", Integer.toString(p.getNumberOfCities()));
        ArrayDeque<City> route = p.getOptimalRoute();

        while(!route.isEmpty()) {
            writeCity(xmlw,route.pop());
        }

        xmlw.writeEndElement();
    }

    //Writes xml tag that does not have any sub-tags or any attributes.
    private static void writeCity(XMLStreamWriter xmlw, City city) throws XMLStreamException {
        xmlw.writeStartElement("city");
        xmlw.writeAttribute("id",Integer.toString(city.getId()));
        xmlw.writeAttribute("name",city.getName());
        xmlw.writeEndElement();
    }
}

