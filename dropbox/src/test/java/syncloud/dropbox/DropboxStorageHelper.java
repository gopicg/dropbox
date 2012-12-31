package syncloud.dropbox;

import syncloud.dropbox.oauth.AccountUtil;
import syncloud.dropbox.oauth.DropboxOAuthAuthentication;
import syncloud.storage.Account;
import syncloud.storage.IFolder;
import syncloud.storage.IStorage;
import syncloud.storage.auth.AuthenticationType;
import syncloud.storage.util.FolderUtils;

import java.util.List;

import static junit.framework.Assert.fail;

public class DropboxStorageHelper {

    public static final String TEST_FOLDER = ".syncloud.test";

    public static DropboxStorage createStorage() {
        try {
            DropboxOAuthAuthentication auth = (DropboxOAuthAuthentication)new DropboxStorageMetadata().getAuthentication(AuthenticationType.OAuth);
            Account account = AccountUtil.create(DropboxStorageMetadata.NAME, Credentials.LOGIN, Credentials.ACCESS_KEY, Credentials.ACCESS_SECRET);
            auth.authenticate(account);
            return new DropboxStorage(auth);
        } catch (Exception e) {
            fail("Authentication failed");
            return null;
        }
    }

    public static IFolder getTestHome(IStorage storage) {
        IFolder homeFolder = getFirstRoot(storage);
        return (new FolderUtils(homeFolder)).getOrCreate(TEST_FOLDER);
    }

    public static IFolder getFirstRoot(IStorage storage) {
        List<IFolder> roots = storage.getRoots();
        if (roots.size() > 0)
            return roots.get(0);
        return null;
    }
}
