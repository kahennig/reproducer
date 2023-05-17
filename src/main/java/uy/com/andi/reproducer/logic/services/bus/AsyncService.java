package uy.com.andi.reproducer.logic.services.bus;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uy.com.andi.reproducer.endpoint.interfaces.ApiService;
import uy.com.andi.reproducer.logic.services.bus.model.BasicBusMessage;
import uy.com.andi.reproducer.logic.services.bus.model.Event;
import uy.com.andi.reproducer.logic.services.bus.utils.BusMessageCodec;

@ApplicationScoped
public class AsyncService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncService.class);

    public static final String CREATE_GROUPS_OP = "create.groups";

    @Inject
    ApiService apiService;

    @Blocking
    @ConsumeEvent(value = "event", codec = BusMessageCodec.class)
    public void asyncEventsConsumer(BasicBusMessage message) {
        if (message.getOperation().equals(CREATE_GROUPS_OP)) {
            Event event = (Event) message;
            apiService.test(event.getMessage());
        }
    }

}
