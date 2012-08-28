package org.syncloud.dropbox;

import syncloud.storage.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class DropboxStorage implements IStorage {

    public final static String ROOT = "/";
    private final NodeKey rootNodeKey;

    private StorageKey key;
    private DropboxAuthentication authentication;

    public DropboxStorage(StorageKey key) {
        this.key = key;
        rootNodeKey = NodeKey.create(key, ROOT);
        authentication = new DropboxAuthentication();
    }

    @Override
    public EnumSet<AuthenticationType> getAuthenticationTypes() throws StorageException {
        return new DropboxStorageMetadata().getAuthenticationTypes();
    }

    @Override
    public IAuthentication getAuthentication(AuthenticationType authenticationType) {
        switch (authenticationType) {
            case OAuth:
                return authentication;
            default:
                String message = "Unsupported authentication type";
                throw new StorageException(message);
        }
    }

    @Override
    public List<IFolder> getRoots() throws StorageException {
        List<IFolder> roots = new ArrayList<IFolder>();
        DropboxFolder root = new DropboxFolder(rootNodeKey, authentication.dropbox);
        roots.add(root);
        return roots;
    }

    @Override
    public StorageKey getKey() {
        return key;
    }
}
