package syncloud.dropbox;

import syncloud.dropbox.oauth.DropboxOAuthAuthentication;
import syncloud.storage.*;
import syncloud.storage.auth.AuthenticationType;
import syncloud.storage.auth.IAuthentication;

import java.util.EnumSet;

public class DropboxStorageMetadata extends IStorageMetadata {
    public static String NAME = "Dropbox";

    @Override
    public String getName() {
        return NAME;
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
    public IAuthentication getAuthentication(AuthenticationType authenticationType) throws StorageException {
        if (authenticationType == AuthenticationType.OAuth)
            return new DropboxOAuthAuthentication();
        else {
            String message = IStorageMetadata.MessageUnsupportedAuthenticationType(authenticationType);
            throw new StorageException(message);
        }
    }
}
