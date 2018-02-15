package org.mvpigs.pigCoin;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Wallet {
    private PublicKey address;
    private PrivateKey SK;
    private double totalInput = 0.0d;
    private double totalOutput = 0.0d;
    private double balance = 0.0d;


    private void setAddress(PublicKey address) {
        this.address = address;
    }

    public PublicKey getAddress() {
        return this.address;
    }

    private void setSK(PrivateKey SK) {
        this.SK = SK;
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

    @Override
    public String toString() {
        String output = "";
        output += " \n Wallet = " + getAddress().hashCode() + "\n";
        output += " total input: " + getTotalInput() + "\n";
        output += " total output: " + getTotalOutput() + "\n";
        output += " Balance: " + getBalance() + "\n";
        return output;
    }

}