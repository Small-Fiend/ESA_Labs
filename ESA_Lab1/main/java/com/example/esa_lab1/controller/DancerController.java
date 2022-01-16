package com.example.esa_lab1.controller;


import com.example.esa_lab1.dao.DancerClassDao;
import com.example.esa_lab1.dao.DancerDao;
import com.example.esa_lab1.dao.DancerStudioDao;
import com.example.esa_lab1.models.Dancer;
import com.example.esa_lab1.models.DancerClass;
import com.example.esa_lab1.models.DancerStudio;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;


@WebServlet
@Path("/dancers")
public class DancerController {
    @EJB
    private DancerDao dancerDao;

    @EJB
    private DancerClassDao dancerClassDao;

    @EJB
    private DancerStudioDao dancerStudioDao;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Path("/") // localhost:8081/dancer
    public Response getDancers() throws JsonProcessingException {
        List<Dancer> dancers = dancerDao.getAll();

        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(dancers))
                .build();
    }

    @GET
    @Path("/{dancerId}") // localhost:8081/dancerClass
    public Response getDancerById(@PathParam("dancerId") String dancerId) throws JsonProcessingException {
        Dancer dancer = dancerDao.get(Integer.valueOf(dancerId));

        if (dancer == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Dancer with id %s not found", dancerId)).build();

        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(dancer))
                .build();
    }

    @POST
    @Path("/")
    public Response addNewDancer(
            @FormParam("name") String name,
            @FormParam("classId") String classId,
            @FormParam("studioId") String studioId) {
        Dancer dancer = new Dancer();
        dancer.setName(name);

        DancerClass dancerClass = dancerClassDao.get(Integer.valueOf(classId));
        if (dancerClass == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("DancerClass with id %s not found", classId)).build();
        dancer.setDancerClass(dancerClass);

        DancerStudio dancerStudio = dancerStudioDao.get(Integer.valueOf(studioId));
        if (dancerStudio == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("DancerStudio with id %s not found", studioId)).build();
        dancer.setDancerStudio(dancerStudio);

        dancerDao.save(dancer);
        return Response.ok().build();
    }

    @PUT
    @Path("/{dancerId}")
    public Response updateDancer(
            @PathParam("dancerId") String dancerId,
            @DefaultValue("") @FormParam("name") String name,
            @DefaultValue("-1") @FormParam("classId") String classId,
            @DefaultValue("-1") @FormParam("studioId") String studioId
    ) {
        Dancer dancer = dancerDao.get(Integer.valueOf(dancerId));
        if (dancer == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Dancer with id %s not found", dancerId)).build();

        if (!name.isEmpty())
            dancer.setName(name);

        if (!classId.equals("-1")) {
            DancerClass dancerClass = dancerClassDao.get(Integer.valueOf(classId));
            if (dancerClass == null)
                return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                        .entity(String.format("DancerClass with id %s not found", classId)).build();
            dancer.setDancerClass(dancerClass);
        }

        if (!studioId.equals("-1")) {
            DancerStudio dancerStudio = dancerStudioDao.get(Integer.valueOf(studioId));
            if (dancerStudio == null)
                return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                        .entity(String.format("DancerStudio with id %s not found", studioId)).build();
            dancer.setDancerStudio(dancerStudio);
        }

        dancerDao.update(dancer);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{dancerId}")
    public Response deleteDancer(@PathParam("dancerId") String dancerId) {
        Dancer dancer = dancerDao.get(Integer.valueOf(dancerId));
        if (dancer == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Dancer with id %s not found", dancerId)).build();

        dancerDao.delete(dancer);
        return Response.ok().build();
    }
}