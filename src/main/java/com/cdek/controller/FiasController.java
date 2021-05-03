package com.cdek.controller;

import com.cdek.service.XMLSample;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/fias/")
public class FiasController {

    private final XMLSample xmlSample;

    public FiasController(XMLSample xmlSample) {
        this.xmlSample = xmlSample;
    }

    @PostMapping("upload")
    public void uploadFias(@NotNull @RequestBody String request) throws XMLStreamException, FileNotFoundException {
        xmlSample.readDataXML(request);
    }
}
