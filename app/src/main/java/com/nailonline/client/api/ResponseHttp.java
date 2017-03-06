package com.nailonline.client.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Olga Riabkova on 10.06.2016.
 * Cheese
 */
public class ResponseHttp {

    public ResponseHttp(JSONObject raw) {
        try {
            if (raw.has("success")) {

                String res = raw.getString("success");
                if (!res.equals("true")) {
                    JSONObject suc = new JSONObject(raw.getString("success"));
                    this.setContent(suc);
                }
            }

            if (raw.has("error")) {
                ErrorHttp error = new ErrorHttp();

                String e = raw.getString("error");
                error.setMessage(e);
                if (e.equals("empty")) error.setType(ErrorHttp.ErrorType.EMPTY);
                if (e.equals("not_found")) error.setType(ErrorHttp.ErrorType.NOT_FOUND);
                if (e.equals("incorrect")) error.setType(ErrorHttp.ErrorType.INCORRECT);
                if (e.equals("403")) error.setType(ErrorHttp.ErrorType.INCORRECT);
                if (e.equals("401"))
                    error.setType(ErrorHttp.ErrorType.ENTITY_NOT_FOUND);
                if (e.equals("Entity not found"))
                    error.setType(ErrorHttp.ErrorType.ENTITY_NOT_FOUND);
                this.setError(error);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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
