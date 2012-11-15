package syncloud.dropbox;

import syncloud.storage.FileTest;
import syncloud.storage.IFolder;
import syncloud.storage.IStorage;

public class DropboxFileTest extends FileTest {

    @Override
    public IStorage createStorage() {
        return DropboxStorageHelper.createStorage();
    }

    @Override
    public IFolder getTestsHome() {
        return DropboxStorageHelper.getTestHome(storage);
    }
}
