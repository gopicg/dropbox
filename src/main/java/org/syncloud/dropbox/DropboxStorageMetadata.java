package org.syncloud.dropbox;

import syncloud.storage.AuthenticationType;
import syncloud.storage.IStorage;
import syncloud.storage.IStorageMetadata;

import java.util.EnumSet;

public class DropboxStorageMetadata extends IStorageMetadata {
    @Override
    public String getName() {
        return "Dropbox";
    }

    @Override
    public Class<? extends IStorage> getStorageClass() {
        return DropboxStorage.class;
    }

    @Override
    public EnumSet<AuthenticationType> getAuthenticationTypes() {
        return EnumSet.of(AuthenticationType.OAuth);
    }
}
