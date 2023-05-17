package uy.com.andi.reproducer.endpoint;

import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import uy.com.andi.reproducer.logic.services.bus.AsyncService;
import uy.com.andi.reproducer.logic.services.bus.model.Event;
import uy.com.andi.reproducer.logic.services.bus.utils.BusMessageCodec;

@ApplicationScoped
public class TestApiHelper {

    private static final String EVENT = "event";

    @Inject
    EventBus bus;

    public Response test(String message) {

        // Return a 202 Accepted response indicating that processing is in progress
        Response response = Response.accepted().build();

        try {
            // Create Event for Async Call.
            Event event = new Event(AsyncService.CREATE_GROUPS_OP, null, null, null, message);
            // Send Event for Async Call.
            bus.send(EVENT, event, new DeliveryOptions().setCodecName(BusMessageCodec.class.getName()));
        } catch (Exception e) {
            // Err
        }
        return response;
    }


}
