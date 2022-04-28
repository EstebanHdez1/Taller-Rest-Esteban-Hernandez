package co.edu.unbosque.taller_4web_service.resources;

import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.*;
import java.util.List;
import java.util.Map;


@Path("/users/{username}/collection")
public class CollectionResource {


    @Context
    ServletContext context;

    private final String UPLOAD_DIRECTORY = "/Collection/";

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response uploadFile(@PathParam("collection") String collection, MultipartFormDataInput input) {
        // Getting the file from form input
        Map<String, List<InputPart>> formParts = input.getFormDataMap();
        List<InputPart> inputParts = formParts.get("file");


        return Response.status(201)
                .entity("Collection created " + collection)
                .build();
    }

    // Parse Content-Disposition header to get the file name
    private String parseFileName(MultivaluedMap<String, String> headers) {
        String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");

        for (String name : contentDispositionHeader) {
            if ((name.trim().startsWith("Text"))) {
                String[] tmp = name.split("=");
                String fileName = tmp[1].trim().replaceAll("\"","");
                return fileName;
            }
        }

        return "unknown";
    }


}
