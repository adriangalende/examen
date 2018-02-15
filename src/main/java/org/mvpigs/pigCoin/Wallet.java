package org.mvpigs.pigCoin;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.TreeMap;

public class Wallet {
    private PublicKey address;
    private PrivateKey SK;
    private double totalInput = 0.0d;
    private double totalOutput = 0.0d;
    private double balance = 0.0d;
    private TreeMap<Integer, Transaction> loadInputTransactions = new TreeMap();
    private TreeMap<Integer, Transaction> loadOutputTransactions = new TreeMap();


    private void setAddress(PublicKey address) {
        this.address = address;
    }

    public PublicKey getAddress() {
        return this.address;
    }

    private void setSK(PrivateKey SK) {
        this.SK = SK;
    }

    public void setTotalInput(double input) {
        this.totalInput += input;
    }

    public void setTotalOutput(double output) {
        this.totalOutput += output;
    }

    public void generateKeyPair(){
        KeyPair pair = GenSig.generateKeyPair();
        setAddress(pair.getPublic());
        setSK(pair.getPrivate());
    }

    public double getTotalInput() {
        return this.totalInput;
    }

    public double getTotalOutput() {
        return this.totalOutput;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance() {
        double balance =  getTotalInput() - getTotalOutput();
        if (balance >= 0) {
         this.balance = balance;
        }
    }


    @Override
    public String toString() {
        String output = "";
        output += " \n Wallet = " + getAddress().hashCode() + "\n";
        output += " total input: " + getTotalInput() + "\n";
        output += " total output: " + getTotalOutput() + "\n";
        output += " Balance: " + getBalance() + "\n";
        return output;
    }

    public void loadCoins(BlockChain blockChain) {
        for (Transaction transaccion:blockChain.getBlockChain()) {
            if (transaccion.getPkeySender().equals(getAddress())) {
                setTotalOutput(transaccion.getPigCoins());
            } else if (transaccion.getPkeyRecipient().equals(getAddress())) {
                setTotalInput(transaccion.getPigCoins());
            } else {
                //ignore
             }
        }
        setBalance();
    }

}