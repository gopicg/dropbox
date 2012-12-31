package syncloud.dropbox.oauth;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import syncloud.dropbox.DropboxApiUtil;
import syncloud.dropbox.DropboxAuthentication;
import syncloud.dropbox.DropboxStorageMetadata;
import syncloud.storage.Account;
import syncloud.storage.StorageKey;

public class DropboxOAuthAuthentication implements DropboxAuthentication {
    public DropboxOAuthAuthentication() {
    }

    public DropboxAPI<?> dropbox;
    public String user;

    @Override
    public DropboxAPI<?> getDropbox() {
        return dropbox;
    }

    @Override
    public boolean authenticate(Account account) {
        AccountUtil accountUtil = new AccountUtil(account);
        dropbox = DropboxApiUtil.CreateDropboxApi(accountUtil.getAccessKey(), accountUtil.getAccessSecret());
        try {
            DropboxAPI.Account dbAccount = dropbox.accountInfo();
            boolean authenticated = dbAccount != null;
            if (authenticated) this.user = account.getStorageKey().getUser();
            return authenticated;
        } catch (DropboxException e) {
            return false;
        }
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
