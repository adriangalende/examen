package org.mvpigs.pigCoin;

import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class TransactionTest {
    @Test
    public void testCrearTransaccion() {

        Wallet wallet_1 = new Wallet();
        wallet_1.generateKeyPair();

        Wallet wallet_2 = new Wallet();
        wallet_2.generateKeyPair();

        Transaction trx = new Transaction();
        trx = new Transaction("hash_1", "0", wallet_1.getAddress(), wallet_2.getAddress(), 20, "a flying pig!");

        assertEquals("hash_1" , trx.getHash());
        assertEquals(wallet_1.getAddress(), trx.getPkeySender());
        assertEquals(wallet_2.getAddress(), trx.getPkeyRecipient());

    }
}