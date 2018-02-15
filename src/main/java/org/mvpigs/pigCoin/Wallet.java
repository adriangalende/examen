package org.mvpigs.pigCoin;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
    private PublicKey address;
    private PrivateKey SK;
    private double totalInput = 0.0d;
    private double totalOutput = 0.0d;
    private double balance = 0.0d;
    private ArrayList<Transaction> inputTransactions = new ArrayList();
    private ArrayList<Transaction> outputTransactions = new ArrayList();


    public void setAddress(PublicKey address) {
        this.address = address;
    }

    public PublicKey getAddress() {
        return this.address;
    }

    public void setSK(PrivateKey SK) {
        this.SK = SK;
    }

    public PrivateKey getSK(){
        return this.SK;
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

    public void loadInputTransactions(BlockChain blockChain) {
        for (Transaction transaccion:blockChain.getBlockChain()) {
            if (transaccion.getPkeyRecipient().equals(getAddress())) {
                inputTransactions.add(transaccion);
            }
        }   
    }

    public ArrayList<Transaction> getInputTransactions(){
        return this.inputTransactions;
    }

    public void loadOutputTransactions(BlockChain blockChain){
        for (Transaction transaccion:blockChain.getBlockChain()) {
            if ((transaccion.getPkeySender().equals(getAddress()))
                    && !(outputTransactions.contains(transaccion.getHash()))) {
                outputTransactions.add(transaccion);
            } else {
                System.out.println("Ya existe una transacción de salida con la id " 
                + transaccion.getHash());
            }
        }   
    }

    public ArrayList<Transaction> getOutputTransactions() {
        return this.outputTransactions;
    }

    public boolean esTransaccionConsumida(String hash) {
        return getOutputTransactions().contains(hash);
    }

    public  Map<String, Double> collectCoins(double pigCoins){
        Map<String, Double> consumidas = new HashMap();

        for (Transaction transaccion:getInputTransactions()) {
            // no encontramos el hash de la transaccion actual en la lista de
            // output transactions
            if (!(esTransaccionConsumida(transaccion.getHash()))) {
                if (getInputTransactions().contains(transaccion.getPigCoins())) {
                   consumidas.put(transaccion.getHash(), transaccion.getPigCoins());
                   return consumidas;
                } else {
                    

                }
            }
        }
        return consumidas;
    }

    public byte[] signTransaction(String message) {
        return (byte[]) message.getBytes();
    }

    public void sendCoins(PublicKey address, double pigcoins, String message, BlockChain bChain){
        Map<String, Double> consumedCoins = collectCoins(pigcoins);
        byte[] messageSignature = signTransaction(message);
        bChain.processTransactions(getAddress(), address, consumedCoins, message, messageSignature);
    }

}