package com.tw.fixture;

import javax.inject.Inject;

public class ClassWithQualifierForConstructor {
    private Inner inner;

    public Inner getInner() {
        return inner;
    }

    @Inject
    public ClassWithQualifierForConstructor(@QualifierInner Inner inner) {
        this.inner = inner;
    }
}
