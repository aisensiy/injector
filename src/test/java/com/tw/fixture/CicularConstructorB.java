package com.tw.fixture;

import javax.inject.Inject;

public class CicularConstructorB {
    private CicularConstructorA cicularConstructorA;

    @Inject
    public CicularConstructorB(CicularConstructorA cicularConstructorA) {
        this.cicularConstructorA = cicularConstructorA;
    }
}
