package org.mvpigs.pigCoin;

import java.security.PublicKey;
import org.mvpigs.pigCoin.Transaction;
import java.util.ArrayList;
import java.util.Map;

public class BlockChain {
    private ArrayList<Transaction> blockChain = new ArrayList();

    public ArrayList<Transaction> getBlockChain() {
        return this.blockChain;
    }

    public void addOrigin(Transaction trx) {
        blockChain.add(trx);
    }

    public void summarize() {
        for (Transaction transaccion:getBlockChain()) {
            System.out.println(transaccion.toString());
            System.out.println("");
            System.out.println("");
        }
    }

    public void summarize(int posicion) {
        System.out.println(getBlockChain().get(posicion).toString());
    }

    public boolean isSignatureValid(PublicKey sender, String message, byte[] messageSigned){
        return GenSig.verify(sender, message, messageSigned);
    }

    public boolean isConsumedCoinValid(Map<String,Double> consumedCoins) {
        for (Map.Entry<String, Double> consumedCoin : consumedCoins.entrySet()) {
            if (getBlockChain().contains(consumedCoin.getKey())) {
                return false;
            }
        }
        return true;
    }

    public void processTransactions(PublicKey sender, PublicKey recipient, Map <String,Double> consumedCoins, String message, byte[] messageSigned) {
        if(isSignatureValid(sender, message, messageSigned) && isConsumedCoinValid(consumedCoins)){
            createTransaction(sender, recipient, consumedCoins,message, messageSigned);
        }
        
    }

    private void createTransaction(PublicKey sender, PublicKey recipient, Map <String,Double> consumedCoins, String message, byte[] messageSigned){
        for (Map.Entry<String, Double> consumedCoin : consumedCoins.entrySet()) {
            String hash = "hash_" + (getBlockChain().size() + 1);
            PublicKey receptor = (consumedCoin.getKey().split("_")[0].equals("CA")) ? sender : recipient;
            Transaction transaccion = new Transaction( hash, consumedCoin.getKey(), sender, receptor, consumedCoin.getValue(),
                message);
            addOrigin(transaccion);
        }
        
    }

}