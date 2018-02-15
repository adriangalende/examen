package org.mvpigs.pigCoin;

import java.security.PublicKey;

public class Transaction {
    private String hash = "";
    private String prevHash = "";
    private PublicKey pKeySender = null;
    private PublicKey pKeyRecipient = null;
    private int pigCoins = 0;
    private String message = "";
    private byte[] signature;

    public Transaction() {

    }

    public Transaction(String hash, String prevHash, PublicKey pKeySender, PublicKey pKeyRecipient, int pigCoins,
            String message) {

                this.hash = hash;
                this.prevHash = prevHash;
                this.pKeySender = pKeySender;
                this.pKeyRecipient = pKeyRecipient;
                this.pigCoins = pigCoins;
                this.message = message;
    }

    public String getHash() {
        return this.hash;
    }

    public String getPrevHash() {
        return this.prevHash;
    }

    public PublicKey getPkeySender() {
        return this.pKeySender;
    }

    public PublicKey getPkeyRecipient() {
        return this.pKeyRecipient;
    }

    public double getPigCoins() {
        return this.pigCoins;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        String output = "";
        output += "\n hash = " + getHash() + "\n";
        output += "\n prev_hash = " + getPrevHash() + "\n";
        output += "\n pKey_Sender = " + getPkeySender().hashCode() + "\n";
        output += "\n pKey_recipient = " + getPkeyRecipient().hashCode() + "\n";
        output += "\n pigcoins = " + getPigCoins() + "\n";
        output += "\n message = " + getMessage() + "\n";
        return output;
    }


}