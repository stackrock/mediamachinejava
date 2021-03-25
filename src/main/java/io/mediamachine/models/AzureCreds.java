package io.mediamachine.models;

/**
 * Azure Credentials
 */
public class AzureCreds implements Credentials{
    private String accountName;
    private String accountKey;

    /**
     * Creates a new Azure credentials instance with the provided account name and key.
     *
     * @param accountName A String representing the account name.
     * @param accountKey A String representing the account key.
     */
    public AzureCreds(String accountName, String accountKey) {
        this.accountName = accountName;
        this.accountKey = accountKey;
    }
}
