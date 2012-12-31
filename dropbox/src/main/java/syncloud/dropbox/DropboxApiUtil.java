package syncloud.dropbox;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.WebAuthSession;
import syncloud.dropbox.oauth.Constants;

public class DropboxApiUtil {
    public static DropboxAPI<?> CreateDropboxApi(String accessKey, String accessSecret) {
        AppKeyPair appKeys = new AppKeyPair(Constants.APP_KEY, Constants.APP_SECRET);
        AccessTokenPair tokenPair = new AccessTokenPair(accessKey, accessSecret);
        WebAuthSession session = new WebAuthSession(appKeys, Constants.ACCESS_TYPE, tokenPair);
        return new DropboxAPI<WebAuthSession>(session);
    }
}
