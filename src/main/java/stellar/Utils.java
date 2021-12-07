package stellar;

import org.stellar.sdk.Network;

public class Utils {

    String pubKey;
    Network network = new Network(Network.TESTNET.getNetworkPassphrase());

    public Utils(String pubkey) {
        pubkey = this.pubKey;
    }

    public void getWallet() {
        Account account = new Account(pubKey);
        account.getBalances();
    }
}
