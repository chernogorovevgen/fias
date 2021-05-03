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
                            //Fias fias = new Fias();
                            Map<String, String> fieldMap = new HashMap<>();
                            int attributesCount = xmlStreamReader.getAttributeCount();
                            for (int i = 0; i < attributesCount; i++) {
//                                fias.setAoid(xmlStreamReader.getAttributeLocalName(i),
//                                        xmlStreamReader.getAttributeValue(i));
//                                System.out.println(xmlStreamReader.getAttributeLocalName(i));
//                                System.out.println(xmlStreamReader.getAttributeValue(i));
                                fieldMap.put(xmlStreamReader.getAttributeLocalName(i).toLowerCase(),
                                        xmlStreamReader.getAttributeValue(i));
                            }
                            // all other user record-related logic
                            //System.out.println(fieldMap);
                            fiasDao.createFias(getFias(fieldMap));
                            //System.out.println(fieldMap);

                        }
                    }
                }
            }
        }

    }


    private Fias getFias(Map<String, String> fieldMap) {
        Fias fias = new Fias();
        fias.setAoid(fieldMap.get("aoid"));
        fias.setAoguid(fieldMap.get("aoguid"));
        fias.setParentguid(fieldMap.get("parentguid"));
        fias.setFormalname(fieldMap.get("formalname"));
        fias.setOffname(fieldMap.get("offname"));
        fias.setShortname(fieldMap.get("shortname"));
        fias.setAolevel(fieldMap.get("aolevel"));
        fias.setRegioncode(fieldMap.get("regioncode"));
        fias.setAreacode(fieldMap.get("areacode"));
        fias.setAutocode(fieldMap.get("autocode"));
        fias.setCitycode(fieldMap.get("citycode"));
        fias.setPlaincode(fieldMap.get("plaincode"));
        fias.setCode(fieldMap.get("code"));
        fias.setOkato(fieldMap.get("okato"));
        fias.setOktmo(fieldMap.get("oktmo"));
        fias.setStartdate(fieldMap.get("startdate"));
        fias.setEnddate(fieldMap.get("enddate"));
        fias.setNextid(fieldMap.get("nextid"));
        fias.setPostalcode(fieldMap.get("postalcode"));
        fias.setPrevid(fieldMap.get("previd"));
        return fias;
    }


}
