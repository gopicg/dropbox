package org.syncloud.dropbox;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import syncloud.core.log.Logger;
import syncloud.storage.IFile;
import syncloud.storage.InputStreamProvider;
import syncloud.storage.NodeKey;
import syncloud.storage.StorageException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DropboxFile implements IFile {
    private NodeKey key;
    private DropboxAPI.Entry entry;
    private DropboxAPI<?> dropbox;

    private boolean deleted;

    private static Logger logger = Logger.getLogger(DropboxFile.class);

    public DropboxFile(NodeKey key, DropboxAPI.Entry entry, DropboxAPI<?> dropbox) {
        this.key = key;
        this.entry = entry;
        this.dropbox = dropbox;
    }

    @Override
    public boolean exists() {
        return (!deleted && entry != null && !entry.isDeleted);
    }

    @Override
    public String getVersion() {
        if (deleted || entry == null || entry.isDeleted) return null;
        return entry.rev;
    }

    @Override
    public void save(InputStreamProvider inputStreamProvider, long length) throws StorageException {
        String path = key.getPath().getPath(Constants.SEPARATOR);
        InputStream inputStream = inputStreamProvider.getData();
        try {
            DropboxAPI.Entry newEntry = dropbox.putFileOverwrite(path, inputStream, length, null);
            entry = newEntry;
        } catch (DropboxException e) {
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {}
            }
        }
    }

    @Override
    public long size() {
        return entry.bytes;
    }

    @Override
    public String getName() {
        List<String> components = key.getPath().getPathComponents();
        return components.get(components.size() - 1);
    }

    @Override
    public void delete() throws StorageException {
        try {
            dropbox.delete(key.getPath().getPath(Constants.SEPARATOR));
            deleted = true;
        } catch (DropboxException e) {
            String message = String.format(UNABLE_TO_DELETE_FILE, key.getPath().getPath(Constants.SEPARATOR));
            logger.error(message, e);
            throw new StorageException(message, e);
        }
    }

    @Override
    public NodeKey getKey() {
        return key;
    }

    @Override
    public InputStream getData() throws StorageException {
        try {
            return dropbox.getFileStream(key.getPath().getPath(Constants.SEPARATOR), entry.rev);
        } catch (DropboxException e) {
            throw new StorageException(e.getMessage());
        }
    }
}
