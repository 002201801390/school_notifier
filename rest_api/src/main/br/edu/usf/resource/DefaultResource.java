package br.edu.usf.resource;

import br.edu.usf.auth.Secured;
import br.edu.usf.enums.ROLE;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

public abstract class DefaultResource<T> {

    @POST
    @Secured({ROLE.ADMIN, ROLE.EMPLOYEE})
    @Path("/insert")
    @Produces(MediaType.APPLICATION_JSON)
    public final Response insert(String body) {
        return Response.status(insertImpl(convertInput(body)) ? Response.Status.OK : Response.Status.CONFLICT).build();
    }

    public abstract boolean insertImpl(T t);

    @GET
    @Secured
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public final Response findAll() {
        return Response.status(Response.Status.OK).entity(findAllImpl()).build();
    }

    public abstract Collection<T> findAllImpl();

    @POST
    @Secured
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public final Response update(String body) {
        return Response.status(updateImpl(convertInput(body)) ? Response.Status.OK : Response.Status.CONFLICT).build();
    }

    public abstract boolean updateImpl(T t);

    @POST
    @Secured({ROLE.ADMIN, ROLE.EMPLOYEE})
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public final Response delete(String body) {
        return Response.status(deleteImpl(convertInput(body)) ? Response.Status.OK : Response.Status.CONFLICT).build();
    }

    public abstract boolean deleteImpl(T t);

    private T convertInput(String input) {
        final T convertInput = convertInputImpl(input);
        if (convertInput != null) {
            return convertInput;
        }
        throw new IllegalArgumentException("Input value cannot be converted");
    }

    protected abstract T convertInputImpl(String input);
}
