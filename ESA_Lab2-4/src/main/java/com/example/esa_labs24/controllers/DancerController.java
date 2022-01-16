package com.example.esa_labs24.controllers;

import com.example.esa_labs24.models.Dancer;
import com.example.esa_labs24.models.DanceClass;
import com.example.esa_labs24.models.DanceStudio;
import com.example.esa_labs24.services.DanceClassService;
import com.example.esa_labs24.services.DancerService;
import com.example.esa_labs24.services.DanceStudioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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
@RequestMapping(value = "/dancers")

public class DancerController {
    @Autowired
    private DancerService dancerService;

    @Autowired
    private DanceClassService danceClassService;

    @Autowired
    private DanceStudioService danceStudioService;

    @RequestMapping(value = "/", method = RequestMethod.GET, headers="accept=application/json") // localhost:8080/dancer
    public ResponseEntity getDancers(){
        List<Dancer> dancers = dancerService.findAll();
        return ResponseEntity.ok().body(dancers);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, headers="accept=application/xml") // localhost:8080/danceStudio
    public ModelAndView getDancerXSLT() throws JsonProcessingException {
        List<Dancer> dancers = dancerService.findAll();
        ModelAndView modelAndView = new ModelAndView("dancers");
        Source source = new StreamSource(new ByteArrayInputStream(new XmlMapper().writeValueAsBytes(dancers)));
        modelAndView.addObject(source);
        return modelAndView;
    }

    @RequestMapping(value = "/{dancerId}", method = RequestMethod.GET) // localhost:8080/danceClass
    public ResponseEntity getDancerById(@PathVariable("dancerId") Integer dancerId){
        Optional<Dancer> dancer = dancerService.findById(dancerId);

        if (!dancer.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Dancer with id %s not found", dancerId), HttpStatus.NOT_FOUND);

        return ResponseEntity.ok().body(dancer.get());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity addNewDancer(
            @RequestBody Dancer dancer,
            @RequestParam Integer classId,
            @RequestParam Integer studioId
    ) {
        Optional<DanceClass> danceClass = danceClassService.findById(classId);
        if (!danceClass.isPresent())
            return new ResponseEntity<Object>(
                    String.format("DanceClass with id %s not found", classId), HttpStatus.NOT_FOUND);

        dancer.setDanceClass(danceClass.get());

        Optional<DanceStudio> danceStudio = danceStudioService.findById(studioId);
        if (!danceStudio.isPresent())
            return new ResponseEntity<Object>(
                    String.format("DanceStudio with id %s not found", studioId), HttpStatus.NOT_FOUND);

        dancer.setDanceStudio(danceStudio.get());

        dancerService.save(dancer);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{dancerId}", method = RequestMethod.PUT)
    public ResponseEntity updateDancer(
            @RequestBody Dancer dancerUpdate,
            @PathVariable("dancerId") Integer dancerId,
            @RequestParam(defaultValue = "-1") Integer classId,
            @RequestParam(defaultValue = "-1") Integer studioId
    ) {
        Optional<Dancer> dancerWrapper = dancerService.findById(dancerId);

        if (!dancerWrapper.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Dancer with id %s not found", dancerId), HttpStatus.NOT_FOUND);

        Dancer dancer = dancerWrapper.get();
        if (!dancerUpdate.getName().isEmpty())
            dancer.setName(dancerUpdate.getName());

        Optional<DanceClass> danceClassWrapper = danceClassService.findById(classId);
        if (!danceClassWrapper.isPresent() && classId > 0)
            return new ResponseEntity<Object>(
                    String.format("DanceClass with id %s not found", classId), HttpStatus.NOT_FOUND);
        dancer.setDanceClass(danceClassWrapper.get());

        Optional<DanceStudio> danceStudioWrapper = danceStudioService.findById(studioId);
        if (!danceStudioWrapper.isPresent() && studioId > 0)
            return new ResponseEntity<Object>(
                    String.format("DanceStudio with id %s not found", studioId), HttpStatus.NOT_FOUND);
        dancer.setDanceStudio(danceStudioWrapper.get());

        dancerService.save(dancer);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{dancerId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteDancer(@PathVariable("dancerId") Integer dancerId) {
        Optional<Dancer> dancerWrapper = dancerService.findById(dancerId);
        if (!dancerWrapper.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Dancer with id %s not found", dancerId), HttpStatus.NOT_FOUND);

        dancerService.delete(dancerWrapper.get());
        return ResponseEntity.ok().build();
    }

}