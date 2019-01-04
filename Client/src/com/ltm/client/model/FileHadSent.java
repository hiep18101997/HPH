package com.ltm.client.model;

public class FileHadSent {
    private int numberFileHasSent = 0;

    public synchronized void add() {
        this.numberFileHasSent += 1;
    }

    public synchronized int getValue() {
        return this.numberFileHasSent;
    }
}
