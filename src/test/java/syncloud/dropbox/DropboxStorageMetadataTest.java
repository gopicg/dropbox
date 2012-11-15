package syncloud.dropbox;

import syncloud.dropbox.DropboxStorageMetadata;
import syncloud.storage.IStorageMetadata;
import syncloud.storage.StorageMetadataTest;

public class DropboxStorageMetadataTest extends StorageMetadataTest {
    @Override
    public IStorageMetadata createMetadata() {
        return new DropboxStorageMetadata();
    }
}
