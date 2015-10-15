package com.tw.fixture;

import javax.inject.Inject;

public class InnerClassWithConstructor {
    private Inner inner;

    @Inject
    public InnerClassWithConstructor(Inner inner) {
        this.inner = inner;
    }

    public Inner getInner() {
        return inner;
    }
}
