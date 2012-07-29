package org.syncloud.dropbox;

import syncloud.storage.IStorage;
import syncloud.storage.IStorageMetadata;

public class DropboxStorageMetadata extends IStorageMetadata {
    @Override
    public String getName() {
        return "Dropbox";
    }

    @Override
    public Class<? extends IStorage> getStorageClass() {
        return DropboxStorage.class;
    }
}
