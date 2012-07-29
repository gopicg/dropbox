package org.syncloud.dropbox;

import com.dropbox.client2.DropboxAPI;
import syncloud.storage.*;

import java.util.List;

public class DropboxFolder extends IFolder {

    private NodeKey key;
    private DropboxAPI<?> dropbox;

    public DropboxFolder(NodeKey key, DropboxAPI<?> dropbox) {
        this.key = key;
        this.dropbox = dropbox;
    }

    @Override
    public List<IFolder> getFolders() throws StorageException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<INode> getContents() throws StorageException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IFolder createFolder(String s) throws StorageException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IFile createFile(String s, InputStreamProvider inputStreamProvider, long l) throws StorageException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getName() {
        List<String> components = key.getPathKey().getPathComponents();
        return components.get(components.size() - 1);
    }

    @Override
    public void delete() throws StorageException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public NodeKey getKey() {
        return key;
    }
}
