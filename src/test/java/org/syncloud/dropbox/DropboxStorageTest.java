package org.syncloud.dropbox;

import syncloud.storage.IFolder;
import syncloud.storage.IStorage;
import syncloud.storage.StorageTest;

public class DropboxStorageTest extends StorageTest {
    @Override
    public IStorage createStorage() {
        return DropboxStorageHelper.createStorage();
    }
}
