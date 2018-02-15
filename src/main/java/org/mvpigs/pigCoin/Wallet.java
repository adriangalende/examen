package org.mvpigs.pigCoin;

import java.security.PrivateKey;
import java.security.PublicKey;

public class Wallet {
    private PublicKey address;
    private PrivateKey SK;

    public Wallet() {

    }

    public void setAddress(PublicKey address) {
        this.address = address;
    }

    public PublicKey getAddress() {
        return this.address;
    }

    public void setSK(PrivateKey SK) {
        this.SK = SK;
    }

}