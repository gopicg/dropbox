package org.syncloud.dropbox;

public class Credentials {
    public static final String[] ACCOUNT = System.getProperty("dropbox.test.account").split(":");
    public static String LOGIN = ACCOUNT[0];
    public static String ACCESS_KEY = ACCOUNT[1];
    public static String ACCESS_SECRET = ACCOUNT[2];
    public static String SECRET = ACCOUNT[1]+":"+ACCOUNT[2];
}