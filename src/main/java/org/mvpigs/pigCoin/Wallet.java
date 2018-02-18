package org.mvpigs.pigCoin;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
            if ((transaccion.getPkeySender().equals(getAddress()))){
                outputTransactions.add(transaccion);
            }
        }   
    }

    public ArrayList<Transaction> getOutputTransactions() {
        return this.outputTransactions;
    }

    public boolean esTransaccionConsumida(String hash) {
        return getOutputTransactions().contains(hash);
    }

    private Map<String, Double> getTransaccionesConsumidas() {
        Map<String, Double> transaccionesConsumidas = new HashMap();

        for (Transaction transaccion:getInputTransactions()) {
            // no encontramos el hash de la transaccion actual en la lista de
            // output transactions
            if (esTransaccionConsumida(transaccion.getHash())) {
                transaccionesConsumidas.put(transaccion.getHash(), transaccion.getPigCoins());
            }
        }
        return transaccionesConsumidas;
    }
    
    public  Map<String, Double> collectCoins(double pigCoins){

        Map<String, Double> transaccionesConsumidas = getTransaccionesConsumidas();
        Map<String, Double> transaccionesDisponibles = new TreeMap();


        if (getBalance() >= pigCoins) {
            int i = 0;
            while (pigCoins > 0) {
                Transaction transaccion = getInputTransactions().get(i);
                if (!(transaccionesConsumidas.containsKey(transaccion.getHash()))) {
                    if (pigCoins == transaccion.getPigCoins()) {
                        transaccionesDisponibles.put(transaccion.getHash(), transaccion.getPigCoins());    
                        pigCoins = 0;
                    } else if (pigCoins < transaccion.getPigCoins()) {
                        transaccionesDisponibles.put(transaccion.getHash(), pigCoins);
                        //CHANGE ADDRESS
                        double cambio = transaccion.getPigCoins() - pigCoins;
                        transaccionesDisponibles.put("CA_" + transaccion.getHash(), cambio);
                        pigCoins = 0;
                    } else {
                        transaccionesDisponibles.put(transaccion.getHash(), transaccion.getPigCoins());
                        pigCoins -= transaccion.getPigCoins();
                    }
                }
                i++;
            }
        }

        return transaccionesDisponibles;
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