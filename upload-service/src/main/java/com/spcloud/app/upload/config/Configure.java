package com.spcloud.app.upload.config;

public class Configure {

    public static final String ROOT_DIR = System.getProperty("user.dir");
    public static final String UPLOAD_DIR = System.getProperty("user.dir") + "/upload/";
    public static final String FILE_PREFIX = "/upload/";

    public static final String WEB_PREFIX = "/static/**";
    public static final String WEB_DIR = ROOT_DIR + "/static/";
}
