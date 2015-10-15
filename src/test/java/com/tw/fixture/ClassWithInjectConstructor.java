package com.tw.fixture;

import javax.inject.Inject;

public class ClassWithInjectConstructor {
    private Inner inner;

    @Inject
    public ClassWithInjectConstructor(Inner inner) {
        this.inner = inner;
    }

    public Inner getInner() {
        return inner;
    }
}
