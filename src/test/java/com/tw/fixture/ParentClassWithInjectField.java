package com.tw.fixture;

import javax.inject.Inject;

public class ParentClassWithInjectField {
    @Inject
    private Inner innerOne;

    public Inner getInnerOne() {
        return innerOne;
    }
}
