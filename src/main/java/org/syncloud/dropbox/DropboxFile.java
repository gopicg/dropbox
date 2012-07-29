package org.syncloud.dropbox;

import syncloud.storage.IFile;
import syncloud.storage.InputStreamProvider;
import syncloud.storage.NodeKey;
import syncloud.storage.StorageException;

import java.io.InputStream;

public class DropboxFile implements IFile {
    @Override
    public boolean exists() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
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
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete() throws StorageException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public NodeKey getKey() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public InputStream getData() throws StorageException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
