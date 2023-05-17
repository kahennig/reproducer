package uy.com.andi.reproducer.logic.services.bus.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.HashMap;
import java.util.Map;

@RegisterForReflection
public abstract class BasicBusMessage {
    
    String operation;
    String tenantId;
    String xChannel;
    String xCorrelationID;
    Map<String, String> spanCtx = new HashMap<>();

    protected BasicBusMessage(String operation, String tenantId, String xChannel, String xCorrelationID) {
        this.operation = operation;
        this.tenantId = tenantId;
        this.xChannel = xChannel;
        this.xCorrelationID = xCorrelationID;
    }

    protected BasicBusMessage() {}

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getxChannel() {
        return xChannel;
    }

    public void setxChannel(String xChannel) {
        this.xChannel = xChannel;
    }

    public String getxCorrelationID() {
        return xCorrelationID;
    }

    public void setxCorrelationID(String xCorrelationID) {
        this.xCorrelationID = xCorrelationID;
    }

    public Map<String, String> getSpanCtx() {
        return spanCtx;
    }

    public void setSpanCtx(Map<String, String> spanCtx) {
        this.spanCtx = spanCtx;
    }
}
