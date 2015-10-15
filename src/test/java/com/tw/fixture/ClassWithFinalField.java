package com.tw.fixture;

import javax.inject.Inject;

public class ClassWithFinalField {
    @Inject
    private final Inner inner = new Inner();

    public Inner getInner() {
        return inner;
    }
}
