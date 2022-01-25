package stellar;

import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Server;
import org.stellar.sdk.responses.AccountResponse;

import java.io.IOException;
import java.util.ArrayList;

public class Account{

    String accountId;

    static Server server = new Server(variables.network);

    public Account(String accountId) {

        this.accountId = accountId;
    }

    public ArrayList getBalances() {

        KeyPair keyPair = KeyPair.fromAccountId(accountId);

        AccountResponse account = getAccount(keyPair);

        ArrayList<String> nftArray = new ArrayList<>();

        if (account == null) {
            return nftArray;
        }
        try {
            for (AccountResponse.Balance balance : account.getBalances()) {

                if (balance.getAssetIssuer().isPresent()) {

                    if (new NftVerification().verifyAccount(balance.getAssetIssuer().get(), server)) {

                        nftArray.add(balance.getAssetCode().get());
                        System.out.println(balance.getBalance());
                    }
                }
            }
            System.out.println(nftArray); // NFT array containing all verified nft codenames such as GNAWBEARS001, GNAWBEAR0007, GNAWBEARTYT2, etc

            return nftArray;

        } catch (Exception e) {

            e.printStackTrace();
        }
        return nftArray;
    }
    public AccountResponse getAccount(KeyPair keyPair) {

        try {
            return  server.accounts().account(keyPair.getAccountId());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
