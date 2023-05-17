package uy.com.andi.reproducer.logic.services.bus.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uy.com.andi.reproducer.logic.services.bus.model.BasicBusMessage;

@RegisterForReflection
public class BusMessageCodec implements MessageCodec<BasicBusMessage, BasicBusMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusMessageCodec.class);

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void encodeToWire(Buffer buffer, BasicBusMessage event) {
        
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
        .allowIfSubType("uy.com.andi.reproducer.logic.services.bus.model")
        .allowIfSubType("java.util.ArrayList")
        .build();
        
        mapper.getFactory().configure(JsonWriteFeature.ESCAPE_NON_ASCII.mappedFeature(), true);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);


        var jsonDataString = "";
        try {
            jsonDataString = mapper.writeValueAsString(event);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Encoded Event: {}", jsonDataString);
            }
        } catch (JsonProcessingException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Message: {}", e.getMessage());
            }
        }

        // Length of JSON: is NOT characters count
        int length = jsonDataString.getBytes().length;

        // Write data into given buffer
        buffer.appendInt(length);
        buffer.appendString(jsonDataString);
    }

    @Override
    public BasicBusMessage decodeFromWire(int position, Buffer buffer) {
        // My custom message starting from this *position* of buffer
        int pos = position;

        // Length of JSON
        var length = buffer.getInt(pos);

        // Get JSON string by it`s length
        // Jump 4 because getInt() == 4 bytes
        int start = pos += 4;
        int end = pos += length;
        var jsonDataString = buffer.getString(start, end);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Event to Decode: {}", jsonDataString);
        }
        try {
            return mapper.readValue(jsonDataString, BasicBusMessage.class);
        } catch (JsonProcessingException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Message: {}", e.getMessage());
            }
        }
        return null;
    }

    @Override
    public BasicBusMessage transform(BasicBusMessage event) {
        // If a message is sent *locally* across the event bus.
        // This example sends message just as is
        return event;
    }

    @Override
    public String name() {
        // Each codec must have a unique name.
        // This is used to identify a codec when sending a message and for unregistering
        // codecs.
        return this.getClass().getName();
    }

    @Override
    public byte systemCodecID() {
        // Always -1
        return -1;
    }
}