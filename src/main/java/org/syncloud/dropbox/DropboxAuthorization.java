package org.syncloud.dropbox;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.WebAuthSession;
import syncloud.storage.IOAuthAuthorization;
import syncloud.storage.OAuthAccess;

public class DropboxAuthorization implements IOAuthAuthorization {

    public DropboxAPI<?> dropbox;

    @Override
    public String startAuthentication() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public OAuthAccess finishAuthentication() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
