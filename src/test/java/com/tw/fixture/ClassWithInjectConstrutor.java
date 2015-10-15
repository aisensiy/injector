package com.tw.fixture;

import javax.inject.Inject;

public class ClassWithInjectConstrutor {

    private InnerClassWithConstructor innerClassWithConstructor;

    @Inject
    public ClassWithInjectConstrutor(InnerClassWithConstructor innerClassWithConstructor) {
        this.innerClassWithConstructor = innerClassWithConstructor;
    }

    public InnerClassWithConstructor getInnerClassWithConstructor() {
        return innerClassWithConstructor;
    }
}
