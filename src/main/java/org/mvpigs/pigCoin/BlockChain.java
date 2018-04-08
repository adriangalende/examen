package org.mvpigs.pigCoin;

import java.security.PublicKey;
import java.util.*;
import org.mvpigs.pigCoin.Transaction;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

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
            for(Transaction transaccion:getBlockChain()) {
                if (transaccion.getPrevHash() == consumedCoin.getKey()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void processTransactions(PublicKey sender, PublicKey recipient, Map <String,Double> consumedCoins, String message, byte[] messageSigned) {
        if(isSignatureValid(sender, message, messageSigned) && isConsumedCoinValid(consumedCoins)){
            createTransaction(sender, recipient, consumedCoins,message, messageSigned);
        }
        
    }

    public void createTransaction(PublicKey sender, PublicKey recipient, Map <String,Double> consumedCoins, String message, byte[] messageSigned){
        for (Map.Entry<String, Double> consumedCoin : consumedCoins.entrySet()) {
            String hash = "hash_" + (getBlockChain().size() + 1);
            PublicKey receptor = (consumedCoin.getKey().split("_")[0].equals("CA")) ? sender : recipient;
            Transaction transaccion = new Transaction( hash, consumedCoin.getKey(), sender, receptor, consumedCoin.getValue(),
                message);
            addOrigin(transaccion);
        }
        
    }

    public double[] loadWallet(PublicKey address) {
        double totalIn = 0.0d;
        double totalOut = 0.0d;
        double[] walletBalance = {totalIn, totalOut};
        for (Transaction transaccion : getBlockChain()) {
            //Si el emisor = receptor => CHANGE ADDRESS
            if (transaccion.getPkeySender().equals(transaccion.getPkeyRecipient())
                    && transaccion.getPkeySender().equals(address)) {
                totalIn += transaccion.getPigCoins();
                totalOut += transaccion.getPigCoins();
            } else if (transaccion.getPkeyRecipient().equals(address)) {
                totalIn += transaccion.getPigCoins();
            } else if (transaccion.getPkeySender().equals(address)) {
                totalOut += transaccion.getPigCoins();
            } else {
                /*pass*/}
        }
        walletBalance[0] = totalIn;
        walletBalance[1] = totalOut;
        return walletBalance;
        
    }

    public ArrayList<Transaction> loadInputTransactions(PublicKey address) {
        ArrayList<Transaction> inputTransactions = getBlockChain().stream().
        filter(transaccion -> transaccion.getPkeyRecipient().equals(address)).
        collect(Collectors.toCollection(ArrayList<Transaction>::new));

        return inputTransactions;
    }

    public ArrayList<Transaction> loadOutputTransactions(PublicKey address) {
        ArrayList<Transaction> outputTransactions = getBlockChain().stream().
        filter(transaccion -> transaccion.getPkeySender().equals(address)).
        collect(Collectors.toCollection(ArrayList<Transaction>::new));
        return outputTransactions;
    }

}