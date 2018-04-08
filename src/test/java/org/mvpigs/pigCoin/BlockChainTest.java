package org.mvpigs.pigCoin;

import org.mvpigs.pigCoin.Transaction;

import static org.junit.Assert.*;

import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.ByteHolder;

public class BlockChainTest {
    BlockChain bChain;
    Wallet origin;
    Transaction trx;

    @Before
    public void setUp() {
        bChain = new BlockChain();

        origin = new Wallet();
        origin.generateKeyPair();

    }


    @Test
    public void testCrearBlockChain() {
        BlockChain bChain = new BlockChain();
        assertNotEquals(null, bChain);
    }

    @Test
    public void testAddNewOrigin() {

        Wallet wallet_1 = new Wallet();
        wallet_1.generateKeyPair();

        Transaction trx = new Transaction("hash_1", "0", origin.getAddress(), wallet_1.getAddress(), 20, "bacon eggs");
        bChain.addOrigin(trx);
        trx = new Transaction("hash_2", "0", origin.getAddress(), wallet_1.getAddress(), 20, "bacon eggs");
        bChain.addOrigin(trx);
        
        int i=1;
        for (Transaction transaccion:bChain.getBlockChain()) {
            assertNotEquals(null, transaccion);
            assertEquals("hash_"+i, transaccion.getHash());
            i++;
        }

    }

    @Test
    public void testSummarize() {
        Wallet wallet_1 = new Wallet();
        wallet_1.generateKeyPair();

        Wallet wallet_2 = new Wallet();
        wallet_2.generateKeyPair();

        Transaction trx = new Transaction("hash_1", "0", origin.getAddress(), wallet_1.getAddress(), 20, "bacon eggs");
        bChain.addOrigin(trx);
        trx = new Transaction("hash_2", "1", origin.getAddress(), wallet_2.getAddress(), 10, "spam spam spam");
        bChain.addOrigin(trx);
        trx = new Transaction("hash_3", "hash_1", wallet_1.getAddress(), wallet_2.getAddress(), 20, "a flying pig!");
        bChain.addOrigin(trx);    
        
        bChain.summarize();

    }

    @Test
    public void testSummarizeConPosicion() {
        Wallet wallet_1 = new Wallet();
        wallet_1.generateKeyPair();

        Wallet wallet_2 = new Wallet();
        wallet_2.generateKeyPair();

        Transaction trx = new Transaction("hash_1", "0", origin.getAddress(), wallet_1.getAddress(), 20, "bacon eggs");
        bChain.addOrigin(trx);
        trx = new Transaction("hash_2", "1", origin.getAddress(), wallet_2.getAddress(), 10, "spam spam spam");
        bChain.addOrigin(trx);
        trx = new Transaction("hash_3", "hash_1", wallet_1.getAddress(), wallet_2.getAddress(), 20, "a flying pig!");
        bChain.addOrigin(trx);    
        
        bChain.summarize(1);
    }

    @Test
    public void testIsConsumedCoinValid() {
        Wallet wallet_1 = new Wallet();
        wallet_1.generateKeyPair();

        Wallet wallet_2 = new Wallet();
        wallet_2.generateKeyPair();

        Transaction trx = new Transaction("hash_1", "0", origin.getAddress(), wallet_1.getAddress(), 20, "bacon eggs");
        bChain.addOrigin(trx);
        trx = new Transaction("hash_2", "1", origin.getAddress(), wallet_2.getAddress(), 10, "spam spam spam");
        bChain.addOrigin(trx);
        trx = new Transaction("hash_3", "hash_1", wallet_1.getAddress(), wallet_2.getAddress(), 20, "a flying pig!");
        bChain.addOrigin(trx); 


        wallet_1.loadCoins(bChain);
        wallet_1.loadInputTransactions(bChain);
        wallet_1.loadOutputTransactions(bChain);

        Double pigcoins = 25d;
        Map<String, Double> consumedCoins = wallet_1.collectCoins(pigcoins);
        assertEquals(true, bChain.isConsumedCoinValid(consumedCoins));

    }

    @Test
    public void testLoadWallet() {
        Wallet wallet_1 = new Wallet();
        wallet_1.generateKeyPair();

        Wallet wallet_2 = new Wallet();
        wallet_2.generateKeyPair();

        Transaction trx = new Transaction("hash_1", "0", origin.getAddress(), wallet_1.getAddress(), 20, "bacon eggs");
        bChain.addOrigin(trx);
        trx = new Transaction("hash_2", "1", origin.getAddress(), wallet_2.getAddress(), 10, "spam spam spam");
        bChain.addOrigin(trx);
        trx = new Transaction("hash_3", "hash_1", wallet_1.getAddress(), wallet_2.getAddress(), 20, "a flying pig!");
        bChain.addOrigin(trx); 
        wallet_1.loadCoins(bChain);
        assertEquals(20.0, bChain.loadWallet(wallet_1.getAddress())[0], 0.01);
        assertEquals( wallet_1.getTotalInput(), bChain.loadWallet(wallet_1.getAddress())[1], 0.01);

    }
}