package com.tw.fixture;

import javax.inject.Inject;

public class ClassWithInjectMethod {
    private Inner inner;

    @Inject
    public void setInner(Inner inner) {
        this.inner = inner;
    }

    public Inner getInner() {
        return inner;
    }
}
