package stellar;

import org.stellar.sdk.*;
import org.stellar.sdk.requests.ErrorResponse;
import org.stellar.sdk.requests.RequestBuilder;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.Page;
import org.stellar.sdk.responses.SubmitTransactionResponse;
import org.stellar.sdk.responses.TransactionResponse;
import org.stellar.sdk.xdr.PaymentOp;

import java.io.IOException;
import java.util.Random;


public class WalletVerification {
    public String wallet = "GBLQSYTWBDP57XEW67AETKXLOCAF7E6XWYAXEF3N3JDYH3E5A2GOANWD"; // SDPEHA5AQVJNTQENQVI6J5WXQT7G7HXTGQEKQE7OXBBACI5JPE5QAOJF
    Server server = Account.server;

    public String verify(String account) {

        String verifingmemo = String.valueOf(Math.abs(new Random().nextInt()));
        System.out.println("Public key : "+ wallet);
        System.out.println("memo : " + verifingmemo);
        return verifingmemo;
    }

    public boolean checkVerify(String account, String memo) {
        try {
            return checkVerification(account,memo) && returnamt(account);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean returnamt(String account) throws IOException {

        KeyPair signer = KeyPair.fromSecretSeed("SD36A47KXKBC42APSGZLDCF5LDCX4VMK4QXUSSVG5LDYZBYLEDXBLWRY");
        AccountResponse accountResponse = server.accounts().account(wallet);
        Transaction transaction = new Transaction.Builder(accountResponse,Network.PUBLIC)
                .addOperation(new PaymentOperation.Builder(account,new AssetTypeNative(),"0.9999").build())
                .addMemo(new MemoText("GnawVerse Verified"))
                .setBaseFee(1000)
                .setTimeout(180)
                .build();
        transaction.sign(signer);

        try {
            SubmitTransactionResponse response = server.submitTransaction(transaction);
            System.out.println("transaction hash: "+ response.getHash());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }
    public boolean checkVerification(String account,String verifyKeysMemo) {

        try {
            System.out.println("checkVerification");
            Page<TransactionResponse> responsePage = server.transactions().forAccount(wallet).order(RequestBuilder.Order.DESC).execute();
            for (TransactionResponse response : responsePage.getRecords()) {
                //System.out.println("Hash "+response.getHash());
                //System.out.println("memo "+response.getMemo()+" is "+response.getMemo().toString().equals(verifyKeysMemo));
                //System.out.println(response.getSourceAccount());
                //System.out.println("account val = "+account);
                //System.out.println("acc "+response.getSourceAccount().toString()+" is "+response.getSourceAccount().toString().equals(account));

                if (account.equals(response.getSourceAccount())
                        && verifyKeysMemo.equals(response.getMemo().toString())) {

                    Transaction transactionev = (Transaction) Transaction.fromEnvelopeXdr(response.getEnvelopeXdr(),
                            new Network(Network.PUBLIC.getNetworkPassphrase()));

                    for(Operation op : transactionev.getOperations()) {

                        PaymentOp paymentOp = op.toXdr().getBody().getPaymentOp();

                        //System.out.println("amt : "+paymentOp.getAmount().getInt64());

                        //System.out.println("Assert : "+paymentOp.getAsset().getDiscriminant().name());
                        if (paymentOp.getAmount().getInt64().toString().equals("9999000")
                                && paymentOp.getAsset().getDiscriminant().name().equals("ASSET_TYPE_NATIVE")) {

                            return true;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (ErrorResponse errorResponse) {
            System.out.println(errorResponse.getCause());
        }

        return false;
    }

    public String getQRString(String verifyKeysMemo) {
        return  "web+stellar:pay?destination="+wallet+"&amount=0.9999&memo="+verifyKeysMemo+"&memo_type=MEMO_TEXT";
    }
}
