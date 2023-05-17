package uy.com.andi.reproducer.endpoint.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uy.com.andi.reproducer.endpoint.interfaces.ApiService;

@ApplicationScoped
public class ApiManager implements ApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiManager.class);

    @Override
    public Response test(String message) {
        LOGGER.info("Test message: {}", message);
        return Response.status(Response.Status.OK).entity(message).build();
    }
}
