package com.tw.fixture;

import javax.inject.Inject;

public class ClassWithMultiInjectFields {
    @Inject
    private Inner innerOne;
    @Inject
    private Inner innerTwo;

    public Inner getInnerOne() {
        return innerOne;
    }

    public Inner getInnerTwo() {
        return innerTwo;
    }
}
