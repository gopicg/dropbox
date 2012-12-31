package syncloud.dropbox;

public class Credentials {
    public static String LOGIN = System.getProperty("dropbox.oauth.user");
    public static String ACCESS_KEY = System.getProperty("dropbox.oauth.access_key");
    public static String ACCESS_SECRET = System.getProperty("dropbox.oauth.access_secret");
}