package com.tw.fixture;

public class ClassWithoutInjectMethod {
    private Inner inner;

    public void setInner(Inner inner) {
        this.inner = inner;
    }

    public Inner getInner() {
        return inner;
    }
}
