package syncloud.dropbox.oauth;

import syncloud.storage.Account;
import syncloud.storage.StorageKey;
import syncloud.storage.auth.AuthenticationType;

import java.util.HashMap;
import java.util.Map;

public class AccountUtil {

    private Account account;
    public AccountUtil(Account account) {
        this.account = account;
    }

    public StorageKey getStorageKey() {
        return account.getStorageKey();
    }

    public String getAccessKey() {
        return account.getCredentials().get(CredentialKeys.ACCESS_KEY);
    }
    public String getAccessSecret() {
        return account.getCredentials().get(CredentialKeys.ACCESS_SECRET);
    }

    public static Account create(StorageKey key, String accessKey, String accessSecret) {
        Map<String, String> credentials = new HashMap<String, String>();
        credentials.put(CredentialKeys.ACCESS_KEY, accessKey);
        credentials.put(CredentialKeys.ACCESS_SECRET, accessSecret);

        Account account = new Account(AuthenticationType.OAuth, key, credentials);
        return account;
    }

    public static Account create(String storage, String user, String accessKey, String accessSecret) {
        return create(new StorageKey(storage, user), accessKey, accessSecret);
    }
}
