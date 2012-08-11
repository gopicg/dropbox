package org.syncloud.dropbox;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import syncloud.core.log.Logger;
import syncloud.storage.IFile;
import syncloud.storage.InputStreamProvider;
import syncloud.storage.NodeKey;
import syncloud.storage.StorageException;

import java.io.InputStream;
import java.util.List;

public class DropboxFile implements IFile {
    private NodeKey key;
    private DropboxAPI<?> dropbox;
    private static Logger logger = Logger.getLogger(DropboxFile.class);

    public DropboxFile(NodeKey key, DropboxAPI<?> dropbox) {
        this.key = key;
        this.dropbox = dropbox;
    }

    @Override
    public boolean exists() {
        try {
            DropboxAPI.Entry entry = dropbox.metadata(key.getPathKey().getPath(Constants.SEPARATOR), 0, null, false, null);
            return (entry != null && !entry.isDeleted);
        } catch (DropboxException e) {
        }
        return false;
    }

    @Override
    public String getVersion() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(InputStreamProvider inputStreamProvider, long l) throws StorageException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long size() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
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

    @Override
    public InputStream getData() throws StorageException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
