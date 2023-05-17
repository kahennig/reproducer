package uy.com.andi.reproducer.logic.services.bus.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Event extends BasicBusMessage {

    String message;

    public Event(String operation, String tenantId, String xChannel, String xCorrelationID, String message) {
        super(operation, tenantId, xChannel, xCorrelationID);
        this.message = message;
    }

    public Event() {
        super();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
