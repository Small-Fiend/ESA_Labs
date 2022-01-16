package com.example.esa_lab1.controller;

import com.example.esa_lab1.dao.DancerClassDao;
import com.example.esa_lab1.models.DancerClass;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@WebServlet
@Path("/dancerClasses")
public class DancerClassController {
    @EJB
    private DancerClassDao dancerClassDao;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Path("/") // localhost:8081/danceClass
    public Response getDanceClass() throws JsonProcessingException {
        List<DancerClass> dancerClasses = dancerClassDao.getAll();
        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(dancerClasses))
                .build();
    }

    @GET
    @Path("/{classId}") // localhost:8081/danceClass
    public Response getDanceClassById(@PathParam("classId") String classId) throws JsonProcessingException {
        DancerClass dancerClass = dancerClassDao.get(Integer.valueOf(classId));

        if (dancerClass == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("DanceClass with id %s not found", classId)).build();

        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(dancerClass))
                .build();
    }

    @POST
    @Path("/")
    public Response addNewDancerClass(
            @FormParam("style") String style,
            @FormParam("trainer") String trainer) {
        DancerClass dancerClass = new DancerClass();
        dancerClass.setStyle(style);
        dancerClass.setTrainer(trainer);

        dancerClassDao.save(dancerClass);
        return Response.ok().build();
    }

    @PUT
    @Path("/{classId}")
    public Response updateDanceClass(
            @PathParam("classId") String classId,
            @DefaultValue("") @FormParam("style") String style,
            @DefaultValue("") @FormParam("trainer") String trainer
    ) {
        DancerClass dancerClass = dancerClassDao.get(Integer.valueOf(classId));
        if (dancerClass == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("DanceClass with id %s not found", classId)).build();

        if (!style.isEmpty())
            dancerClass.setStyle(style);

        if (!trainer.isEmpty())
            dancerClass.setTrainer(trainer);

        dancerClassDao.update(dancerClass);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{classId}")
    public Response deleteDancerClass(@PathParam("classId") String classId) {
        DancerClass dancerClass = dancerClassDao.get(Integer.valueOf(classId));
        if (dancerClass == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("DancerClass with id %s not found", classId)).build();

        dancerClassDao.delete(dancerClass);
        return Response.ok().build();
    }
}