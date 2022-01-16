package com.example.esa_labs24.controllers;

import com.example.esa_labs24.models.DanceStudio;
import com.example.esa_labs24.services.DanceStudioService;
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
@RequestMapping("/danceStudios")
public class DanceStudioController {
    @Autowired
    private DanceStudioService danceStudioService;


    @RequestMapping(value = "/", method = RequestMethod.GET, headers="accept=application/json") // localhost:8080/danceStudio
    public ResponseEntity getDanceStudios() {
        List<DanceStudio> danceStudios = danceStudioService.findAll();
        return ResponseEntity.ok().body(danceStudios);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, headers="accept=application/xml") // localhost:8080/danceStudio
    public ModelAndView getDanceStudioXSLT() throws JsonProcessingException {
        List<DanceStudio> danceStudios = danceStudioService.findAll();
        ModelAndView modelAndView = new ModelAndView("danceStudio");
        Source source = new StreamSource(new ByteArrayInputStream(new XmlMapper().writeValueAsBytes(danceStudios)));
        modelAndView.addObject(source);
        return modelAndView;
    }

    @RequestMapping(value = "/{studioId}", method = RequestMethod.GET) // localhost:8080/danceClass
    public ResponseEntity getDanceStudioById(@PathVariable("studioId") Integer studioId) {
        Optional<DanceStudio> danceStudio = danceStudioService.findById(studioId);

        if (!danceStudio.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Dancer with id %s not found", studioId), HttpStatus.NOT_FOUND);

        return ResponseEntity.ok().body(danceStudio.get());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity addNewDanceStudio(
            @RequestBody DanceStudio danceStudio
    ) {
        danceStudioService.save(danceStudio);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{studioId}", method = RequestMethod.PUT)
    public ResponseEntity updateDanceStudio(
            @RequestBody DanceStudio danceStudioUpdate,
            @PathVariable("studioId") Integer studioId
    ) {
        Optional<DanceStudio> danceStudioWrapper = danceStudioService.findById(studioId);
        if (!danceStudioWrapper.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Dancer with id %s not found", studioId), HttpStatus.NOT_FOUND);

        DanceStudio danceStudio = danceStudioWrapper.get();
        if (!danceStudioUpdate.getAddress().isEmpty())
            danceStudio.setAddress(danceStudioUpdate.getAddress());

        danceStudioService.save(danceStudio);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{studioId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteDanceStudio(@PathVariable("studioId") Integer studioId) {
        Optional<DanceStudio> danceStudioWrapper = danceStudioService.findById(studioId);
        if (!danceStudioWrapper.isPresent())
            return new ResponseEntity<Object>(
                    String.format("DanceStudio with id %s not found", studioId), HttpStatus.NOT_FOUND);

        danceStudioService.delete(danceStudioWrapper.get());
        return ResponseEntity.ok().build();
    }
}
