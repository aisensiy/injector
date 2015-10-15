package com.tw.fixture;

import javax.inject.Inject;

public class CircularA {
    @Inject
    private CircularB circularB;

    public CircularB getCircularB() {
        return circularB;
    }

    public CircularA() {
    }
}
