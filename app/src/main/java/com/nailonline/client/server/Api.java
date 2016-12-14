package com.nailonline.client.server;


import com.nailonline.client.BuildConfig;

/**
 * Created by Olga Riabkova on 17.10.2016.
 * Cheese
 */

public class Api {
    public static final String API = BuildConfig.SERVER;
    public static final String IMAGE_PRESENTS_DIR = BuildConfig.SERVER_IMAGE_PRESENTS;
    public static final String IMAGE_PROMO_DIR = BuildConfig.SERVER_IMAGE_PROMO;

    public static enum ResponseType {FAILURE, SUCCESS}

    public static enum ErrorType {INCORRECT, EMPTY, NOT_FOUND}

}
