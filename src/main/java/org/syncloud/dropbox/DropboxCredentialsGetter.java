package org.syncloud.dropbox;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.*;

public class DropboxCredentialsGetter {

    public class Account {
        public String user;
        public String key;
        public String secret;

        public Account(String user, String key, String secret) {
            this.user = user;
            this.key =  key;
            this.secret = secret;
        }
    }

    private String appkey;
    private String appsecret;
    private Session.AccessType accessType;

    public DropboxCredentialsGetter(String appkey, String appsecret, Session.AccessType accessType) {
        this.appkey = appkey;
        this.appsecret = appsecret;
        this.accessType = accessType;
    }

    public Account getAccount() throws DropboxException {
        AppKeyPair appKeys = new AppKeyPair(appkey, appsecret);
        WebAuthSession session = new WebAuthSession(appKeys, accessType);

        WebAuthSession.WebAuthInfo info = session.getAuthInfo();

        String url = info.url;

        String uid = session.retrieveWebAccessToken(info.requestTokenPair);
        AccessTokenPair accessTokenPair = session.getAccessTokenPair();

        return new Account(uid, accessTokenPair.key, accessTokenPair.secret);
    }
}
