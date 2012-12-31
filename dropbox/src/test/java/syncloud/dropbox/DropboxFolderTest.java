package syncloud.dropbox;

import syncloud.storage.FolderTest;
import syncloud.storage.IFolder;
import syncloud.storage.IStorage;

public class DropboxFolderTest extends FolderTest {
    @Override
    public IStorage createStorage() {
        return DropboxStorageHelper.createStorage();
    }

    @Override
    public IFolder getTestsHome() {
        return DropboxStorageHelper.getTestHome(storage);
    }
}
