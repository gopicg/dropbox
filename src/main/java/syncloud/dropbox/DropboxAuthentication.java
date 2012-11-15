package syncloud.dropbox;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.WebAuthSession;
import syncloud.storage.StorageKey;
import syncloud.storage.auth.IOAuthAuthentication;
import syncloud.storage.auth.OAuthAccess;

public class DropboxAuthentication implements IOAuthAuthentication {

    public WebAuthSession session;
    public WebAuthSession.WebAuthInfo authInfo;

    public String user;
    public DropboxAPI<?> dropbox;

    public DropboxAuthentication() {
    }

    @Override
    public String startAuthentication(String callbackUrl) {
        AppKeyPair appKeys = new AppKeyPair(Constants.APP_KEY, Constants.APP_SECRET);
        session = new WebAuthSession(appKeys, Constants.ACCESS_TYPE);

        try {
            authInfo = null;
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
            if (accessTokenPair == null) return null;
            DropboxAPI<?> dropbox = CreateDropboxApi(accessTokenPair.key, accessTokenPair.secret);
            DropboxAPI.Account account = dropbox.accountInfo();
            return new OAuthAccess(account.displayName, accessTokenPair.key, accessTokenPair.secret);
        } catch (DropboxException e) {
            return null;
        }
    }

    @Override
    public boolean authenticate(String user, String accessKey, String accessSecret) {
        dropbox = CreateDropboxApi(accessKey, accessSecret);
        try {
            DropboxAPI.Account account = dropbox.accountInfo();
            boolean authenticated = account != null;
            if (authenticated) this.user = user;
            return authenticated;
        } catch (DropboxException e) {
            return false;
        }
    }

    private static DropboxAPI<?> CreateDropboxApi(String accessKey, String accessSecret) {
        AppKeyPair appKeys = new AppKeyPair(Constants.APP_KEY, Constants.APP_SECRET);
        AccessTokenPair tokenPair = new AccessTokenPair(accessKey, accessSecret);
        WebAuthSession session = new WebAuthSession(appKeys, Constants.ACCESS_TYPE, tokenPair);
        return new DropboxAPI<WebAuthSession>(session);
    }

    @Override
    public boolean authenticated() {
        return dropbox != null;
    }

    @Override
    public StorageKey getStorageKey() {
        return new StorageKey(DropboxStorageMetadata.NAME, user);
    }
}
