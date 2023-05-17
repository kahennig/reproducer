package uy.com.andi.reproducer.endpoint.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class GenericResponse {
    
    private int responseCode;
    private Object responseBody;

    public int getResponseCode() {
        return responseCode;
    }

    public GenericResponse setResponseCode(int respondeCode) {
        this.responseCode = respondeCode;
        return this;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public GenericResponse setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
        return this;
    }

}
