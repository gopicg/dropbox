package org.syncloud.dropbox;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.WebAuthSession;
import syncloud.storage.IOAuthAuthentication;
import syncloud.storage.OAuthAccess;

public class DropboxAuthentication implements IOAuthAuthentication {

    public WebAuthSession session;
    public WebAuthSession.WebAuthInfo authInfo;

    public DropboxAPI<?> dropbox;

    @Override
    public String startAuthentication(String callbackUrl) {
        AppKeyPair appKeys = new AppKeyPair(Constants.APP_KEY, Constants.APP_SECRET);
        session = new WebAuthSession(appKeys, Constants.ACCESS_TYPE);

        try {
            WebAuthSession.WebAuthInfo authInfo = null;
            if (callbackUrl != null)
                authInfo = session.getAuthInfo(callbackUrl);
            else
                authInfo = session.getAuthInfo();
            return authInfo.url;
        } catch (DropboxException e) {
            return null;
        }
    }

    @Override
    public OAuthAccess finishAuthentication() {
        String uid = null;
        try {
            uid = session.retrieveWebAccessToken(authInfo.requestTokenPair);
            AccessTokenPair accessTokenPair = session.getAccessTokenPair();
            return new OAuthAccess(uid, accessTokenPair.key, accessTokenPair.secret);
        } catch (DropboxException e) {
            return null;
        }
    }

    @Override
    public boolean authenticate(String accessKey, String accessSecret) {
        AppKeyPair appKeys = new AppKeyPair(Constants.APP_KEY, Constants.APP_SECRET);
        AccessTokenPair tokenPair = new AccessTokenPair(accessKey, accessSecret);
        WebAuthSession session = new WebAuthSession(appKeys, Constants.ACCESS_TYPE, tokenPair);
        dropbox = new DropboxAPI<WebAuthSession>(session);
        try {
            DropboxAPI.Account account = dropbox.accountInfo();
            return account != null;
        } catch (DropboxException e) {
            return false;
        }
    }

    @Override
    public boolean authenticated() {
        return dropbox != null;
    }
}
