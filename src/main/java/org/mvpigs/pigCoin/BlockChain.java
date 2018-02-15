package org.mvpigs.pigCoin;

import org.mvpigs.pigCoin.Transaction;
import java.util.ArrayList;

public class BlockChain {
    private ArrayList<Transaction> blockChain = new ArrayList();

    public ArrayList<Transaction> getBlockChain() {
        return this.blockChain;
    }

    public void addOrigin(Transaction trx) {
        blockChain.add(trx);
    }

    public void summarize() {
        for (Transaction transaccion:getBlockChain()) {
            System.out.println(transaccion.toString());
            System.out.println("");
            System.out.println("");
        }
    }

}