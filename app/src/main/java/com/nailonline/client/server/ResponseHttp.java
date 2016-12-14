package com.nailonline.client.server;

import org.json.JSONObject;

/**
 * Created by Olga Riabkova on 10.06.2016.
 * Cheese
 */
public class ResponseHttp {
    public static final int SERVER_ERROR = 403;
    public static final int OK = 200;
    private Api.ResponseType type;
    private JSONObject content;
    private ErrorHttp error;
    private int code;
    private String raw;

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Api.ResponseType getType() {
        return type;
    }

    public void setType(Api.ResponseType type) {
        this.type = type;
    }

    public JSONObject getContent() {
        return content;
    }

    public void setContent(JSONObject content) {
        this.content = content;
    }

    public ErrorHttp getError() {
        return error;
    }

    public void setError(ErrorHttp error) {
        this.error = error;
    }
}
