package com.tw.fixture;

import javax.inject.Inject;

public class ClassWithQualifiedInner {
    @Inject
    @QualifierInner
    Inner inner;

    public Inner getInner() {
        return inner;
    }
}
