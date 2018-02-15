package org.mvpigs.pigCoin;



import static org.junit.Assert.assertEquals;

import java.security.KeyPair;
import org.junit.Before;
import org.junit.Test;

public class WalletTest{
    Wallet wallet;
    @Before
    public void SetUp() {
        wallet = new Wallet();
    }

    @Test
    public void testComprobarWallet (){
        Wallet wallet = new Wallet();

        KeyPair pair = GenSig.generateKeyPair();

        wallet.setSK(pair.getPrivate());
        wallet.setAddress(pair.getPublic());

       assertEquals(pair.getPublic(), wallet.getAddress());

    }

    @Test
    public void testGenerateKeyPair() {
        wallet.generateKeyPair();
        System.out.println(wallet.getAddress().hashCode());
    }

    



}