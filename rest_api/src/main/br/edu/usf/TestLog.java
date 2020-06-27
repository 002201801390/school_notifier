package br.edu.usf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/test_log")
public class TestLog {
    private static final Logger logger = LoggerFactory.getLogger(TestLog.class);

    @GET
    public Response test() {
        logger.debug("DEBUG");
        logger.info("INFO");
        logger.warn("WARN");
        logger.error("ERROR");

        return Response.ok("OK_OK_OK_OK_OK_OK_OK_OK_OK_OK_OK").build();
    }
}
