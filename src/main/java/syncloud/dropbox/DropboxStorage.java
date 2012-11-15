package syncloud.dropbox;

import syncloud.storage.IFolder;
import syncloud.storage.IStorage;
import syncloud.storage.NodeKey;
import syncloud.storage.StorageException;

import java.util.ArrayList;
import java.util.List;

public class DropboxStorage implements IStorage {

    public final static String ROOT = "/";
    private final NodeKey rootNodeKey;

//    private StorageKey key;
    private DropboxAuthentication authentication;

    public DropboxStorage(DropboxAuthentication authentication) {
//        this.key = key;
        rootNodeKey = NodeKey.create(authentication.getStorageKey(), ROOT);
        this.authentication = authentication;
    }

    /*@Override
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
    }*/

    @Override
    public List<IFolder> getRoots() throws StorageException {
        List<IFolder> roots = new ArrayList<IFolder>();
        DropboxFolder root = new DropboxFolder(rootNodeKey, authentication.dropbox);
        roots.add(root);
        return roots;
    }

   /* @Override
    public StorageKey getKey() {
        return key;
    }*/
}
