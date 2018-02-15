package org.mvpigs.pigCoin;

import org.mvpigs.pigCoin.Transaction;

import static org.junit.Assert.*;

import org.junit.Test;

public class BlockChainTest {
    @Test
    public void testCrearBlockChain() {
        BlockChain bChain = new BlockChain();
        assertNotEquals(null, bChain);
    }

    @Test
    public void testAddNewOrigin() {

        BlockChain bChain = new BlockChain();

        Wallet origin = new Wallet();
        origin.generateKeyPair();

        Wallet wallet_1 = new Wallet();
        wallet_1.generateKeyPair();

        Transaction trx = new Transaction("hash_1", "0", origin.getAddress(), wallet_1.getAddress(), 20, "bacon eggs");
        bChain.addOrigin(trx);

        for (Transaction transaccion:bChain.getBlockChain()) {
            System.out.println(transaccion.toString());
        }

    }
}