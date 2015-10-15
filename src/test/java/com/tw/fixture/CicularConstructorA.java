package com.tw.fixture;

import javax.inject.Inject;

public class CicularConstructorA {
    private CicularConstructorB cicularConstructorB;

    @Inject
    public CicularConstructorA(CicularConstructorB cicularConstructorB) {
        this.cicularConstructorB = cicularConstructorB;
    }
}
