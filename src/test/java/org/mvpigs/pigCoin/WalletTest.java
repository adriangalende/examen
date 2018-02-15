package org.mvpigs.pigCoin;



import static org.junit.Assert.assertEquals;

import java.security.KeyPair;
import org.junit.Before;
import org.junit.Test;

public class WalletTest{
    Wallet wallet_1;
    Wallet wallet_2;
    @Before
    public void SetUp() {
        wallet_1 = new Wallet();
        wallet_1.generateKeyPair();

        wallet_2 = new Wallet();
        wallet_2.generateKeyPair();
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
        Wallet wallet = new Wallet();
        wallet.generateKeyPair();
        System.out.println(wallet.getAddress().hashCode());
    }

    @Test
    public void testWalletToString() {
        System.out.println("wallet_1 :" + wallet_1.toString());
        System.out.println("wallet_2 :" + wallet_2.toString());

    }

    @Test
    public void testCargarPigCoins() {


        Wallet origin = new Wallet();
        origin.generateKeyPair();

        BlockChain bChain = new BlockChain();

        Transaction trx = new Transaction("hash_1", "0", origin.getAddress(), wallet_1.getAddress(), 20, "bacon eggs");
        bChain.addOrigin(trx);
        trx = new Transaction("hash_2", "1", origin.getAddress(), wallet_2.getAddress(), 10, "spam spam spam");
        bChain.addOrigin(trx);
        trx = new Transaction("hash_3", "hash_1", wallet_1.getAddress(), wallet_2.getAddress(), 20, "a flying pig!");
        bChain.addOrigin(trx); 
        
        wallet_1.loadCoins(bChain);
        System.out.println(wallet_1.toString());

        wallet_2.loadCoins(bChain);
        System.out.println(wallet_2.toString());
   
    }





}