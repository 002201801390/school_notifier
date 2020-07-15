package br.edu.usf.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MongoConnection {
    private static final Logger log = LoggerFactory.getLogger(MongoConnection.class);

    private static final MongoConnection instance = new MongoConnection();

    private static final MongoClient mongoClient = new MongoClient();
    private static final MongoDatabase database = mongoClient.getDatabase("school_notifier_request_log");
    private static final MongoCollection<Document> collection = database.getCollection("Collection");

    public static MongoConnection gi() {
        return instance;
    }

    private MongoConnection() {
        super();
    }

    public Response logResponse(Response response) {
        final Date date = response.getDate();
        final Response.StatusType statusInfo = response.getStatusInfo();

        try {
            final Document document = new Document("type", "response")
                    .append("date", date)
                    .append("status_code", statusInfo.getStatusCode());

            collection.insertOne(document);

        } catch (Exception e) {
            log.error("Error to log request in MongoDB", e);
        }

        return response;
    }

    public boolean logRequest(ContainerRequestContext requestContext) {
        final Date date = requestContext.getDate();
        final String method = requestContext.getMethod();
        final UriInfo uriInfo = requestContext.getUriInfo();

        try {
            final Document document = new Document("type", "request")
                    .append("date", date)
                    .append("method", method)
                    .append("uri", uriInfo.getAbsolutePath().toString());

            collection.insertOne(document);

            return true;

        } catch (Exception e) {
            log.error("Error to log request in MongoDB", e);
        }

        return false;
    }

    public List<Document> getLogRequests() {
        final List<Document> items = new ArrayList<>();

        try (final MongoCursor<Document> cursor = collection.find().iterator()) {

            while (cursor.hasNext()) {
                final Document document = cursor.next();
                items.add(document);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }
}
