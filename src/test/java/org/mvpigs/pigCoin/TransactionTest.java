package org.mvpigs.pigCoin;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class TransactionTest {
    Wallet wallet_1;
    Wallet wallet_2;
    Transaction trx;

    @Before
    public void setUp() {
        wallet_1 = new Wallet();
        wallet_1.generateKeyPair();

        wallet_2 = new Wallet();
        wallet_2.generateKeyPair();
    }

    @Test
    public void testCrearTransaccion() {

        Transaction trx = new Transaction();
        trx = new Transaction("hash_1", "0", wallet_1.getAddress(), wallet_2.getAddress(), 20, "a flying pig!");

        assertEquals("hash_1" , trx.getHash());
        assertEquals(wallet_1.getAddress(), trx.getPkeySender());
        assertEquals(wallet_2.getAddress(), trx.getPkeyRecipient());
    }

    @Test
    public void testTransaccionToString(){
        trx = new Transaction("hash_1", "0", wallet_1.getAddress(), wallet_2.getAddress(), 20, "a flying pig!");
        System.out.println(trx.toString());

    }
}