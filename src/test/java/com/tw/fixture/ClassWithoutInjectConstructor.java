package com.tw.fixture;

public class ClassWithoutInjectConstructor {
    private Inner inner;

    public Inner getInner() {
        return inner;
    }

    public ClassWithoutInjectConstructor(Inner inner) {
        this.inner = inner;
    }
}
