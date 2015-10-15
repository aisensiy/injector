package com.tw.fixture;

import javax.inject.Inject;

public class ClassWithInjectField {
    @Inject
    private Inner inner;

    @Inject
    private InnerClassWithInjectField innerClassWithInjectField;

    public Inner getInner() {
        return inner;
    }

    public InnerClassWithInjectField getInnerClassWithInjectField() {
        return innerClassWithInjectField;
    }
}
