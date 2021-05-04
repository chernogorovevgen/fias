package com.cdek.service;

import com.cdek.dao.FiasDao;
import com.cdek.entity.Fias;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Service
public class XMLSample {

    private final FiasDao fiasDao;

    public XMLSample(FiasDao fiasDao) {
        this.fiasDao = fiasDao;
    }

    public void readDataXML(String fileLocation) throws FileNotFoundException, XMLStreamException {
        FileInputStream fileInputStream = new FileInputStream(fileLocation);
        XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(fileInputStream);

        // reading the data
        while (xmlStreamReader.hasNext()) {

            int eventCode = xmlStreamReader.next();

            // this triggers _users records_ logic
            if ((XMLStreamConstants.START_ELEMENT == eventCode)
                    && xmlStreamReader.getLocalName().equalsIgnoreCase("AddressObjects")) {

                // read and parse the user data rows
                while (xmlStreamReader.hasNext()) {

                    eventCode = xmlStreamReader.next();

                    // this breaks _users record_ reading logic
                    if ((XMLStreamConstants.END_ELEMENT == eventCode)
                            && xmlStreamReader.getLocalName().equalsIgnoreCase("AddressObjects")) {
                        break;
                    } else {

                        if ((XMLStreamConstants.START_ELEMENT == eventCode)
                                && xmlStreamReader.getLocalName().equalsIgnoreCase("Object")) {

                            // extract the user data
                            Map<String, String> fieldMap = new HashMap<>();
                            int attributesCount = xmlStreamReader.getAttributeCount();
                            for (int i = 0; i < attributesCount; i++) {
                                fieldMap.put(xmlStreamReader.getAttributeLocalName(i).toLowerCase(),
                                        xmlStreamReader.getAttributeValue(i));
                            }
                            fiasDao.createFias(getFias(fieldMap));
                        }
                    }
                }
            }
        }

    }


    private Fias getFias(Map<String, String> fieldMap) {
        Fias fias = new Fias();
        fias.setId(fieldMap.get("id"));
        fias.setObjectId(fieldMap.get("objectid"));
        fias.setObjectGuid(fieldMap.get("objectguid"));
        fias.setChangeId(fieldMap.get("changeid"));
        fias.setName(fieldMap.get("name"));
        fias.setTypeName(fieldMap.get("typename"));
        fias.setLevel(fieldMap.get("level"));
        fias.setPrevId(fieldMap.get("previd"));
        fias.setNextId(fieldMap.get("nextid"));
        fias.setUpdateDate(fieldMap.get("updatedate"));
        fias.setStartDate(fieldMap.get("startdate("));
        fias.setEndDate(fieldMap.get("enddate"));
        fias.setIsActual(fieldMap.get("isactual"));
        fias.setIsActive(fieldMap.get("isactive"));
        return fias;
    }


}
