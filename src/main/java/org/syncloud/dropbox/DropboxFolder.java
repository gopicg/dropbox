package org.syncloud.dropbox;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.exception.DropboxUnlinkedException;
import syncloud.core.log.Logger;
import syncloud.storage.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DropboxFolder extends IFolder {
    private NodeKey key;
    private DropboxAPI<?> dropbox;
    private static Logger logger = Logger.getLogger(DropboxFolder.class);

    public DropboxFolder(NodeKey key, DropboxAPI<?> dropbox) {
        this.key = key;
        this.dropbox = dropbox;
    }

    @Override
    public List<IFolder> getFolders() throws StorageException {
        List<IFolder> folders = new ArrayList<IFolder>();
        try {
            DropboxAPI.Entry entry = dropbox.metadata(key.getPath().getPath(Constants.SEPARATOR), 0, null, true, null);
            for(DropboxAPI.Entry childEntry: entry.contents) {
                String childFileName = childEntry.fileName();
                if (childEntry.isDir)
                    folders.add(new DropboxFolder(key.child(childFileName), dropbox));
            }
        } catch (DropboxException e) {
            String message = String.format(IFolder.UNABLE_TO_GET_CONTENTS, getName());
            logger.error(message);
            throw new StorageException(ErrorCode.Unknown, message);
        }
        return folders;
    }

    @Override
    public List<INode> getContents() throws StorageException {
        try {
            List<INode> nodes = new ArrayList<INode>();
            DropboxAPI.Entry entry = dropbox.metadata(key.getPath().getPath(Constants.SEPARATOR), 0, null, true, null);
            for(DropboxAPI.Entry childEntry: entry.contents) {
                String childFileName = childEntry.fileName();
                if (childEntry.isDir)
                    nodes.add(new DropboxFolder(key.child(childFileName), dropbox));
                else
                    nodes.add(new DropboxFile(key.child(childFileName), childEntry, dropbox));
            }
            return nodes;
        } catch (DropboxException e) {
            String message = String.format(IFolder.UNABLE_TO_GET_CONTENTS, getName());
            logger.error(message);
            throw new StorageException(ErrorCode.Unknown, message);
        }
    }

    @Override
    public IFolder createFolder(String name) throws StorageException {
        try {
            NodeKey newKey = key.child(name);
            DropboxAPI.Entry entry = dropbox.createFolder(newKey.getPath().getPath(Constants.SEPARATOR));
            return new DropboxFolder(newKey, dropbox);
        } catch (DropboxException e) {
            String message = String.format(UNABLE_TO_ADD_FOLDER(name, getName()));
            logger.error(message, e);
            throw new StorageException(message, e);
        }
    }

    @Override
    public IFile createFile(String name, InputStreamProvider inputStreamProvider, long length) throws StorageException {
        InputStream inputStream = inputStreamProvider.getData();
        try {
            NodeKey newKey = key.child(name);
            DropboxAPI.Entry newEntry = dropbox.putFile(newKey.getPath().getPath(Constants.SEPARATOR), inputStream, length, null, null);
            return new DropboxFile(newKey, newEntry, dropbox);
        } catch (DropboxUnlinkedException e) {
            // User has unlinked, ask them to link again here.
        } catch (DropboxException e) {
//            Log.e("DbExampleLog", "Something went wrong while uploading.");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {}
            }
        }
        return null;
    }

    @Override
    public String getName() {
        List<String> components = key.getPath().getPathComponents();
        return components.get(components.size() - 1);
    }

    @Override
    public void delete() throws StorageException {
        String path = key.getPath().getPath(Constants.SEPARATOR);
        try {
            dropbox.delete(path);
        } catch (DropboxException e) {
            String message = String.format(UNABLE_TO_DELETE_FOLDER(path));
            logger.error(message, e);
            throw new StorageException(message);
        }
    }

    @Override
    public NodeKey getKey() {
        return key;
    }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof DropboxFolder))
            return false;

        DropboxFolder other = (DropboxFolder) obj;
        return key.equals(other.key) && dropbox.equals(dropbox);
    }
}
