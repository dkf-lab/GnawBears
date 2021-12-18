package me.dkflab.gnawbears;

import org.bukkit.plugin.java.JavaPlugin;
import stellar.Account;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public final class GnawBears extends JavaPlugin {

    String wallet = "test";

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println(new Account("GC3FVQYZORHWJCU4M72KDTRUHYYBXR6YARVHVWPZF24R2ZN34LQ3BKEC").getBalances());
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
