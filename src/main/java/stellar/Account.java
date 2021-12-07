package stellar;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Server;
import org.stellar.sdk.responses.AccountResponse;

import java.io.IOException;

class Account{

    String accountId;
    Server server = new Server(variables.network);

    Account(String accountId) {
        accountId = this.accountId;
    }

    public void getBalances() {
        KeyPair keyPair = KeyPair.fromAccountId(accountId);
        AccountResponse account = getAccount(keyPair);

        for (AccountResponse.Balance balance : account.getBalances()) {
            System.out.printf(
                    "Type: %s, Code: %s, Balance: %s%n",
                    balance.getAssetType(),
                    balance.getAssetCode(),
                    balance.getBalance()
            );
        }
    }
    public AccountResponse getAccount(KeyPair keyPair) {
        try {

            AccountResponse account = server.accounts().account(keyPair.getAccountId());
            return account;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
