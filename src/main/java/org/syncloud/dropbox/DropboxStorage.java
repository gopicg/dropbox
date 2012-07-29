package org.syncloud.dropbox;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.WebAuthSession;
import syncloud.storage.*;

import java.util.ArrayList;
import java.util.List;

public class DropboxStorage implements IStorage, ISecured {

    private StorageKey key;
    private DropboxAPI<?> dropbox;

    public final static String ROOT = "/";
    private final NodeKey rootNodeKey;

    public DropboxStorage(StorageKey key) {
        this.key = key;
        rootNodeKey = NodeKey.create(key, ROOT);
    }

    @Override
    public boolean authenticate(String secret) {
        String[] secretParts = secret.split(":");
        String accessKey = secretParts[0];
        String accessSecret  = secretParts[1];

        AppKeyPair appKeys = new AppKeyPair(Keys.APP_KEY, Keys.APP_SECRET);
        AccessTokenPair tokenPair = new AccessTokenPair(accessKey, accessSecret);
        WebAuthSession session = new WebAuthSession(appKeys, Keys.ACCESS_TYPE, tokenPair);

        dropbox = new DropboxAPI<WebAuthSession>(session);

        return true;
    }

    @Override
    public boolean authenticated() {
        return dropbox != null;
    }

    @Override
    public List<IFolder> getRoots() throws StorageException {
        List<IFolder> roots = new ArrayList<IFolder>();
        DropboxFolder root = new DropboxFolder(rootNodeKey, dropbox);
        roots.add(root);
        return roots;
    }

    @Override
    public StorageKey getKey() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
