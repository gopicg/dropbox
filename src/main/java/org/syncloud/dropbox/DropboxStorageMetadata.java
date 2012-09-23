package org.syncloud.dropbox;

import syncloud.storage.*;

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

    @Override
    public IAuthentication getAuthentication(AuthenticationType type) throws StorageException {
        if (type == AuthenticationType.OAuth)
            return new DropboxAuthentication();
        else {
            String message = IStorageMetadata.MessageUnsupportedAuthenticationType(type);
            throw new StorageException(message);
        }
    }
}
