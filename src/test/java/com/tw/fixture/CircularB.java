package com.tw.fixture;

import javax.inject.Inject;

public class CircularB {
    @Inject
    private CircularA circularA;

    public CircularA getCircularA() {
        return circularA;
    }
}
