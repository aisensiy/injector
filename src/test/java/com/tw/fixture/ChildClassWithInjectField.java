package com.tw.fixture;

import javax.inject.Inject;

public class ChildClassWithInjectField extends ParentClassWithInjectField {
    @Inject
    private Inner innerTwo;

    public Inner getInnerTwo() {
        return innerTwo;
    }
}
