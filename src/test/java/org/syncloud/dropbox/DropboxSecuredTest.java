package org.syncloud.dropbox;

import syncloud.storage.ISecured;
import syncloud.storage.SecuredTest;
import syncloud.storage.StorageKey;

public class DropboxSecuredTest extends SecuredTest {
    @Override
    public ISecured createStorage(StorageKey storageKey) {
        return new DropboxStorage(storageKey);
    }

    @Override
    public String getGoodLogin() {
        return Credentials.LOGIN;
    }

    @Override
    public String getGoodPassword() {
        return Credentials.SECRET;
    }
}
