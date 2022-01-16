package com.example.esa_lab1.controller;

import com.example.esa_lab1.dao.DancerStudioDao;
import com.example.esa_lab1.models.DancerStudio;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@WebServlet
@Path("/dancerStudios")
public class DancerStudioController {
    @EJB
    private DancerStudioDao dancerStudioDao;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Path("/") // localhost:8081/dancerStudio
    public Response getClients() throws JsonProcessingException {
        List<DancerStudio> dancerStudios = dancerStudioDao.getAll();
        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(dancerStudios))
                .build();
    }

    @GET
    @Path("/{studioId}") // localhost:8081/dancerClass
    public Response getDancerStudioById(@PathParam("studioId") String studioId) throws JsonProcessingException {
        DancerStudio dancerStudio = dancerStudioDao.get(Integer.valueOf(studioId));

        if (dancerStudio == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Dancer with id %s not found", studioId)).build();

        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(dancerStudio))
                .build();
    }

    @POST
    @Path("/")
    public Response addNewDancerStudio(
            @FormParam("address") String address) {
        DancerStudio dancerStudio = new DancerStudio();
        dancerStudio.setAddress(address);

        dancerStudioDao.save(dancerStudio);
        return Response.ok().build();
    }

    @PUT
    @Path("/{studioId}")
    public Response updateDancerStudio(
            @PathParam("studioId") String studioId,
            @DefaultValue("") @FormParam("address") String address
    ) {
        DancerStudio dancerStudio = dancerStudioDao.get(Integer.valueOf(studioId));
        if (dancerStudio == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("DancerStudio with id %s not found", studioId)).build();

        if (!address.isEmpty())
            dancerStudio.setAddress(address);

        dancerStudioDao.update(dancerStudio);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{studioId}")
    public Response deleteDancerStudio(@PathParam("studioId") String studioId) {
        DancerStudio dancerStudio = dancerStudioDao.get(Integer.valueOf(studioId));
        if (dancerStudio == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("DancerStudio with id %s not found", studioId)).build();

        dancerStudioDao.delete(dancerStudio);
        return Response.ok().build();
    }
}
