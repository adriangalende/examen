package org.mvpigs.pigCoin;



import java.util.Map;
import static org.junit.Assert.assertEquals;

import java.security.KeyPair;
import org.junit.Before;
import org.junit.Test;

public class WalletTest{
    Wallet wallet_1;
    Wallet wallet_2;
    BlockChain bChain;

    @Before
    public void SetUp() {
        wallet_1 = new Wallet();
        wallet_1.generateKeyPair();

        wallet_2 = new Wallet();
        wallet_2.generateKeyPair();

        bChain = new BlockChain();
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

    @Test
    public void testLoadInputTransactions() {
 
        Wallet origin = new Wallet();
        origin.generateKeyPair();

        Transaction trx = new Transaction("hash_1", "0", origin.getAddress(), wallet_1.getAddress(), 20, "bacon eggs");
        bChain.addOrigin(trx);
        trx = new Transaction("hash_2", "1", origin.getAddress(), wallet_2.getAddress(), 10, "spam spam spam");
        bChain.addOrigin(trx);
        trx = new Transaction("hash_3", "hash_1", wallet_1.getAddress(), wallet_2.getAddress(), 20, "a flying pig!");
        bChain.addOrigin(trx); 
        
        wallet_1.loadInputTransactions(bChain);
        
        System.out.println("Wallet = " + wallet_1.getAddress().hashCode());
        System.out.println("Transacciones = " + wallet_1.getInputTransactions().toString());
    }

    @Test
    public void testLoadOutputTransactions() {
 
        Wallet origin = new Wallet();
        origin.generateKeyPair();

        Transaction trx = new Transaction("hash_1", "0", origin.getAddress(), wallet_1.getAddress(), 20, "bacon eggs");
        bChain.addOrigin(trx);
        trx = new Transaction("hash_2", "1", origin.getAddress(), wallet_2.getAddress(), 10, "spam spam spam");
        bChain.addOrigin(trx);
        trx = new Transaction("hash_3", "hash_1", wallet_1.getAddress(), wallet_2.getAddress(), 20, "a flying pig!");
        bChain.addOrigin(trx); 
        
        wallet_1.loadOutputTransactions(bChain);
        wallet_2.loadOutputTransactions(bChain);
        
        System.out.println("Wallet = " + wallet_1.getAddress().hashCode());
        System.out.println("Transacciones = " + wallet_1.getOutputTransactions().toString());

        System.out.println("Wallet = " + wallet_2.getAddress().hashCode());
        System.out.println("Transacciones = " + wallet_2.getOutputTransactions().toString());
    }

    @Test
    public void testCollectCoins(){  
        Wallet origin = new Wallet();
        origin.generateKeyPair();

        Transaction trx = new Transaction("hash_4", "2", origin.getAddress(), wallet_1.getAddress(), 20, "sausages puagh!");
        bChain.addOrigin(trx);
        trx = new Transaction("hash_5", "3", origin.getAddress(), wallet_1.getAddress(), 10, "baked beans are off!");
        bChain.addOrigin(trx);

        wallet_1.loadInputTransactions(bChain);
        wallet_1.loadOutputTransactions(bChain);

        wallet_1.loadCoins(bChain);

        Double pigcoins = 25d;
        Map<String, Double> consumedCoins = wallet_1.collectCoins(pigcoins);
        System.out.println("Pigcoins enviados a la wallet_2 y transacciones consumidas: " + consumedCoins);
    }





}