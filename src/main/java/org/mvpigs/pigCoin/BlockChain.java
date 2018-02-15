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
        for (Transaction transaccion:consumedCoins){
            if(blockChain.contains(transaccion.getHash())){
                return true;
            } 
        }
        return false;
    }

    public void processTransactions(PublicKey sender, PublicKey recipient, Map <String,Double> consumedCoins, String message, byte[] messageSigned) {
        if(isSignatureValid(sender, message, messageSigned) && isConsumedCoinValid(consumedCoins)){
            createTransaction(sender, recipient, consumedCoins,message, messageSigned);
        }
        
    }

}