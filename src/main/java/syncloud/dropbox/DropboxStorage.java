package syncloud.dropbox;

import com.dropbox.client2.DropboxAPI;
import syncloud.storage.IFolder;
import syncloud.storage.IStorage;
import syncloud.storage.NodeKey;
import syncloud.storage.StorageException;

import java.util.ArrayList;
import java.util.List;

public class DropboxStorage implements IStorage {

    public final static String ROOT = "/";
    private final NodeKey rootNodeKey;

    private DropboxAPI<?> dropbox;

    public DropboxStorage(DropboxAuthentication authentication) {
        rootNodeKey = NodeKey.create(authentication.getStorageKey(), ROOT);
        dropbox = authentication.getDropbox();
    }

    @Override
    public List<IFolder> getRoots() throws StorageException {
        List<IFolder> roots = new ArrayList<IFolder>();
        DropboxFolder root = new DropboxFolder(rootNodeKey, dropbox);
        roots.add(root);
        return roots;
    }
}
