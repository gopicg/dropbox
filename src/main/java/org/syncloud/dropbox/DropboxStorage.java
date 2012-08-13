package org.syncloud.dropbox;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.WebAuthSession;
import syncloud.storage.*;

import java.util.ArrayList;
import java.util.List;

public class DropboxStorage implements IStorage {

    public final static String ROOT = "/";
    private final NodeKey rootNodeKey;

    private StorageKey key;
    private DropboxAuthorization authorization;

    public DropboxStorage(StorageKey key) {
        this.key = key;
        rootNodeKey = NodeKey.create(key, ROOT);
        authorization = new DropboxAuthorization();
    }

    @Override
    public IAuthorization getAuthorization() {
        return authorization;
    }

    @Override
    public List<IFolder> getRoots() throws StorageException {
        List<IFolder> roots = new ArrayList<IFolder>();
        DropboxFolder root = new DropboxFolder(rootNodeKey, authorization.dropbox);
        roots.add(root);
        return roots;
    }

    @Override
    public StorageKey getKey() {
        return key;
    }
}
