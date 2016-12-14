package com.nailonline.client.server;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Olga Riabkova on 10.06.2016.
 * Cheese
 */
public class ErrorHttp {

    public static enum ErrorType {INCORRECT, EMPTY, NOT_FOUND, ENTITY_NOT_FOUND, CONNECT_EXCEPTION}

    private String message;
    private ErrorType type;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorType getType() {
        return type;
    }

    public void setType(ErrorType type) {
        this.type = type;
    }

    public static void showErrorMessage(Context context, String message) {
        if (message == null) {
            Toast.makeText(context, "Ошибка на сервере", Toast.LENGTH_LONG).show();
        } else {
            if (message.contains("ConnectException")) {
                Toast.makeText(context, "Отсутствует подключение к интернету", Toast.LENGTH_LONG).show();
            } else if (message.contains("Entity")) {
                Toast.makeText(context, "Ошибка выполнения запроса. Была произведена авторизация на другом устройстве", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Ошибка выполнения запроса", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static void showChancheAccountMessage(Context context) {
        Toast.makeText(context, "Была произведена авторизация на другом устройстве", Toast.LENGTH_SHORT).show();
    }
}
