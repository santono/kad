package kad.reports.service;

import kad.reports.domain.CityEntity;
import kad.reports.domain.RoadPointEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;


@Service
public class YaService {
    @Autowired
    private CityService cityService;
    private static final String urlTemplate = "https://geocode-maps.yandex.ru/1.x/?geocode=";
    @Autowired
    protected RestTemplate restTemplate;

    protected static Logger logger = Logger.getLogger("service");

    public void performCodingForAllCityes() {
        List<CityEntity> l = cityService.getAll();
        for (CityEntity cityEntity : l) {
            if (cityEntity.getName().indexOf("Украина") < 0) {
                if (cityEntity.getId() != 7) {
                    if (cityEntity.getName().trim().indexOf(" ") < 0) {
                        makeCoordRec(cityEntity);
                    }
                }
            }
        }
    }

    public void makeCoordRec(CityEntity cityEntity) {
        String result = restTemplate.getForObject(urlTemplate + "Крым,{city}",
                //               http://search.yahooapis.com/
                //       NewsSearchService/V1/newsSearch?appid={appid}&
                //       query={query}&results={results}&language={language}",
                String.class, cityEntity.getName());
        //     logger.debug(result);
        if (result.length() > 10000) {
            result = "gt 10000";
        }
        cityEntity.setYmapl(result);
        cityService.saveRecord(cityEntity);
    }

    public void makeXXYY(CityEntity cityEntity) {
        StreamSource source = new StreamSource(new ByteArrayInputStream(cityEntity.getYmapl().getBytes()));
// Define DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        dbf.setIgnoringComments(false);
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setNamespaceAware(true);
// Define DocumentBuilder
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {

        }

// Define InputSource
        InputSource is = new InputSource();
        is.setSystemId(source.getSystemId());
        is.setByteStream(source.getInputStream());
        is.setCharacterStream(source.getReader());
        is.setEncoding("UTF-8");
        Document doc;
        try {
            doc = db.parse(is);
        } catch (Exception e) {
            logger.debug(" doc null for id " + cityEntity.getId());
            doc = null;
            return;
        }

// Get items
        String xx, yy, temp;
        String balloon;
        NodeList itemElements = doc.getElementsByTagName("pos");
        int length = itemElements.getLength();
// Define lists for titles and links
        if (length > 0) {
            temp = itemElements.item(0).getChildNodes().item(0).getNodeValue();
            if (temp == null) {
                logger.debug("temp is null");
            } else {
                logger.debug("temp=" + temp);
            }
        } else {
            temp = "0.0000 0.0000";
        }
        NodeList itemElements1 = doc.getElementsByTagName("LocalityName");
// Loop over all item elements
        length = itemElements1.getLength();
        if (length > 0) {
            balloon = itemElements1.item(0).getChildNodes().item(0).getNodeValue();
        } else {
            balloon = "";
        }
        int i = temp.trim().indexOf(" ");
        if (i > 0) {
            xx = temp.trim().substring(0, i - 1);
            yy = temp.trim().substring(i + 1, temp.trim().length());
        } else {
            xx = "";
            yy = "";
        }
        cityEntity.setXx(xx);
        cityEntity.setYy(yy);
        cityEntity.setBalloon(balloon);
        cityService.saveRecord(cityEntity);
    }

    public void extractCodingForAllCityes() {
        List<CityEntity> l = cityService.getAll();
        for (CityEntity cityEntity : l) {
            if (cityEntity.getYmapl() != null) {
                if (cityEntity.getYmapl().length() > 0) {
                    makeXXYY(cityEntity);
                }
            }
        }
    }

    public void fillXXYYForRoadPoint(RoadPointEntity rpEntity) {
        String result = restTemplate.getForObject(urlTemplate + "Крым,{city}",
                String.class, rpEntity.getBalloon());
        if ((result.indexOf("pos") < 0) && (result.indexOf("POS") < 0)) {
            return;
        }
        StreamSource source = new StreamSource(new ByteArrayInputStream(result.getBytes()));
// Define DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        dbf.setIgnoringComments(false);
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setNamespaceAware(true);
// Define DocumentBuilder
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {

        }

// Define InputSource
        InputSource is = new InputSource();
        is.setSystemId(source.getSystemId());
        is.setByteStream(source.getInputStream());
        is.setCharacterStream(source.getReader());
        is.setEncoding("UTF-8");
        Document doc;
        try {
            doc = db.parse(is);
        } catch (Exception e) {
            logger.debug(" doc null for id " + rpEntity.getBalloon());
            doc = null;
            return;
        }

// Get items
        String xx, yy, temp;
        String balloon;
        NodeList itemElements = doc.getElementsByTagName("pos");
        int length = itemElements.getLength();
// Define lists for titles and links
        if (length > 0) {
            temp = itemElements.item(0).getChildNodes().item(0).getNodeValue();
            if (temp == null) {
                logger.debug("temp is null");
            } else {
                logger.debug("temp=" + temp);
            }
        } else {
            temp = "0.0000 0.0000";
        }
        NodeList itemElements1 = doc.getElementsByTagName("LocalityName");
// Loop over all item elements
        length = itemElements1.getLength();
        if (length > 0) {
            balloon = itemElements1.item(0).getChildNodes().item(0).getNodeValue();
        } else {
            balloon = "";
        }
        int i = temp.trim().indexOf(" ");
        if (i > 0) {
            xx = temp.trim().substring(0, i - 1);
            yy = temp.trim().substring(i + 1, temp.trim().length());
        } else {
            xx = "";
            yy = "";
        }
        Double xxd = Double.parseDouble(xx);
        Double yyd = Double.parseDouble(yy);
        rpEntity.setXx(xxd);
        rpEntity.setYy(yyd);


    }

}
