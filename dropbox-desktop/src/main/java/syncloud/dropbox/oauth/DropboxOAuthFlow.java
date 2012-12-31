package syncloud.dropbox.oauth;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.WebAuthSession;
import syncloud.dropbox.DropboxApiUtil;
import syncloud.dropbox.DropboxStorageMetadata;
import syncloud.storage.Account;
import syncloud.storage.desktop.OAuthFlow;

public class DropboxOAuthFlow implements OAuthFlow {
    public WebAuthSession session;
    public WebAuthSession.WebAuthInfo authInfo;

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
    public Account finishAuthentication() {
        try {
            session.retrieveWebAccessToken(authInfo.requestTokenPair);
            AccessTokenPair accessTokenPair = session.getAccessTokenPair();
            if (accessTokenPair == null) return null;
            DropboxAPI<?> dropbox = DropboxApiUtil.CreateDropboxApi(accessTokenPair.key, accessTokenPair.secret);
            DropboxAPI.Account dbAccount = dropbox.accountInfo();
            Account account = AccountUtil.create(DropboxStorageMetadata.NAME, dbAccount.displayName, accessTokenPair.key, accessTokenPair.secret);
            return account;
        } catch (DropboxException e) {
            return null;
        }
    }
}
