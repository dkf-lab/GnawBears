package stellar;

public class AssetClass {
    String assetcode;
    double balance;
    AssetClass(String assetcode, double balance) {
        this.assetcode = assetcode;
        this.balance = balance;
    }

    public String getAssetcode() {
        return assetcode;
    }

    public void setAssetcode(String assetcode) {
        this.assetcode = assetcode;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
