package com.tw.fixture;

import javax.inject.Inject;
import javax.inject.Named;

public class ClassWithNamedInnerField {
    @Inject
    @Named("namedInner")
    private Inner inner;

    public Inner getInner() {
        return inner;
    }
}
