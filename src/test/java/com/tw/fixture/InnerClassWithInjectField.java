package com.tw.fixture;

import javax.inject.Inject;

public class InnerClassWithInjectField {
    @Inject
    private Inner inner;

    public Inner getInner() {
        return inner;
    }
}
