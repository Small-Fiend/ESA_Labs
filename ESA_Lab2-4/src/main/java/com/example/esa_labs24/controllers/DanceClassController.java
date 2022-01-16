package com.example.esa_labs24.controllers;
import com.example.esa_labs24.models.DanceClass;
import com.example.esa_labs24.services.DanceClassService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/danceClasses")
public class DanceClassController {
    @Autowired
    private DanceClassService danceClassService;

    @RequestMapping(value = "/", method = RequestMethod.GET, headers="accept=application/json") // localhost:8080/danceClass
    public ResponseEntity getDanceClasses() {
        List<DanceClass> danceClasses = danceClassService.findAll();
        return ResponseEntity.ok().body(danceClasses);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, headers="accept=application/xml") // localhost:8080/danceStudio
    public ModelAndView getDanceClassXSLT() throws JsonProcessingException {
        List<DanceClass> danceClasses = danceClassService.findAll();
        ModelAndView modelAndView = new ModelAndView("danceClasses");
        Source source = new StreamSource(new ByteArrayInputStream(new XmlMapper().writeValueAsBytes(danceClasses)));
        modelAndView.addObject(source);
        return modelAndView;
    }

    @RequestMapping(value = "/{classId}", method = RequestMethod.GET) // localhost:8080/danceClass
    public ResponseEntity getDanceClassById(@PathVariable("classId") Integer classId) {
        Optional<DanceClass> danceClass = danceClassService.findById(classId);

        if (!danceClass.isPresent())
            return new ResponseEntity<Object>(String.format("DanceClass with id %s not found", classId), HttpStatus.NOT_FOUND);

        return ResponseEntity.ok().body(danceClass.get());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity addNewDanceClass(@RequestBody DanceClass danceClass) {
        danceClassService.save(danceClass);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{classId}", method = RequestMethod.PUT)
    public ResponseEntity updateDanceClass(
            @RequestBody DanceClass danceClassUpdate,
            @PathVariable("classId") Integer classId
    ) {
        Optional<DanceClass> danceClassWrapper = danceClassService.findById(classId);
        if (!danceClassWrapper.isPresent())
            return new ResponseEntity<Object>(
                    String.format("DanceClass with id %s not found", classId), HttpStatus.NOT_FOUND);

        DanceClass danceClass = danceClassWrapper.get();
        if (!danceClassUpdate.getStyle().isEmpty())
            danceClass.setStyle(danceClassUpdate.getStyle());

        if (!danceClassUpdate.getTrainer().isEmpty())
            danceClass.setTrainer(danceClassUpdate.getTrainer());

        danceClassService.save(danceClass);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{classId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteDanceClass(@PathVariable("classId") Integer classId) {
        Optional<DanceClass> danceClassWrapper = danceClassService.findById(classId);
        if (!danceClassWrapper.isPresent())
            return new ResponseEntity<Object>(
                    String.format("DanceClass with id %s not found", classId), HttpStatus.NOT_FOUND);

        danceClassService.delete(danceClassWrapper.get());
        return ResponseEntity.ok().build();
    }
}