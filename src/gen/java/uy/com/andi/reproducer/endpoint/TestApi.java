package uy.com.andi.reproducer.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.annotations.SseElementType;

@Path("/tests")
@RequestScoped
@Api(description = "test API")

public class TestApi {

    @Inject
    TestApiHelper delegate;

    @POST
    @Path("/endpoints")
    @SseElementType(MediaType.APPLICATION_JSON)
    @Produces({"application/json"})
    public Response test(@ApiParam(value = "message") String message) {
        return delegate.test(message);
    }
}
