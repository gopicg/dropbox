package org.syncloud.dropbox;

import syncloud.storage.*;
import java.util.List;

import static junit.framework.Assert.fail;

public class DropboxStorageHelper {

    public static final String TEST_FOLDER = ".syncloud.test";

    public static DropboxStorage createStorage() {
        DropboxStorage storage = new DropboxStorage(new StorageKey("Dropbox", new User(Credentials.LOGIN)));
        try {
            IOAuthAuthorization auth = (IOAuthAuthorization)storage.getAuthorization();
            auth.authenticate(Credentials.ACCESS_KEY, Credentials.ACCESS_SECRET);
        } catch (Exception e) {
            fail("Authentication failed");
        }
        return storage;
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
