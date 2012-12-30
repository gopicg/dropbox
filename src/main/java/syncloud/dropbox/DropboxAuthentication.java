package syncloud.dropbox;

import com.dropbox.client2.DropboxAPI;
import syncloud.storage.auth.IAuthentication;

public interface DropboxAuthentication extends IAuthentication {
    DropboxAPI<?> getDropbox();
}
