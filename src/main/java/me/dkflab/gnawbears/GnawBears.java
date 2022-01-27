package me.dkflab.gnawbears;

import org.bukkit.plugin.java.JavaPlugin;
import stellar.Account;
import stellar.WalletVerification;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public final class GnawBears extends JavaPlugin {

    String wallet = "test";

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println(new Account(wallet).getBalances());
        //Wallet verification code
        WalletVerification walletVerification = new WalletVerification();
        String memo = walletVerification.verify(wallet); // to be stored with playerdata until verified

        String qrcode = walletVerification.getQRString(memo); // QR CODE to be shown to player (STRING to img)

        if(walletVerification.checkVerify(wallet,memo)) {
            System.out.println("verified"); // remove memo from playerdata if verified
        }
        else {
            System.out.println("Not Verified if amt sent contact admins for manual verification");
            // add a feature for admins to manually verify user
            // call returnamt(wallet) to refund the amt sent by user on manual verification returns 0.9999 XLM
        }

        //re nft verification   treat balance 0000001 as one nft
        System.out.println(new Account(wallet).getBalances());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public String getPlayerWallet(UUID uuid) {
        // UUID is a unique identifier for each player
        
        return wallet;
    }

    public List<String> getPlayerNFTs(UUID uuid) {
        // Would probably make an NFT() object
        return Collections.singletonList("GNAWBEARS001");
    }

}
