package org.mvpigs.pigCoin;

import org.mvpigs.pigCoin.Transaction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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

        for (Transaction transaccion:bChain.getBlockChain()) {
            System.out.println(transaccion.toString());
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
}