package br.edu.usf.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

public abstract class DefaultResource<T> {

    @POST
    @Path("/insert")
    @Produces(MediaType.APPLICATION_JSON)
    public final Response insert(T t) {
        return Response.status(insertImpl(t) ? Response.Status.OK : Response.Status.CONFLICT).build();
    }

    public abstract boolean insertImpl(T t);

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public final Response findAll() {
        return Response.status(Response.Status.OK).entity(findAllImpl()).build();
    }

    public abstract Collection<T> findAllImpl();

    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public final Response update(T t) {
        return Response.status(updateImpl(t) ? Response.Status.OK : Response.Status.CONFLICT).build();
    }

    public abstract boolean updateImpl(T t);

    @POST
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public final Response delete(T t) {
        return Response.status(deleteImpl(t) ? Response.Status.OK : Response.Status.CONFLICT).build();
    }

    public abstract boolean deleteImpl(T t);
}
