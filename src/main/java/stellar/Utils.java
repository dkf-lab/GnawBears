package stellar;

public class Utils {

    String pubKey;

    public Utils(String pubkey) {

        pubkey = this.pubKey;
    }

    public void getWallet() {
        Account account = new Account(pubKey);

        account.getBalances();
    }
}
