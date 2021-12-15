package stellar;

import org.stellar.sdk.Server;
import shadow.com.moandjiezana.toml.Toml;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

public class NftVerification {

    public boolean verifyAccount(String accountId, Server server) {

        try {
            String res = server.accounts().account(accountId).getHomeDomain();

            if (res == null) {

                return false;
            }
            return (res.equals(variables.orgDomain) && tomlParsing(accountId));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean tomlParsing(String accountID) {

        try {
            Toml toml = new Toml().read(new BufferedInputStream(new URL(variables.tomlUrl).openStream()));

            return toml.toMap().get("CURRENCIES").toString().contains(accountID);

        } catch (IOException e) {
            // handle exception
            e.printStackTrace();
        }
        return false;
    }
}
