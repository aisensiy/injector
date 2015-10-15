package com.tw.fixture;

import javax.inject.Inject;

public class ChildClassWithInjectMethod extends ParentClassWithInjectMethod {

    private Inner childInner;
    private Inner innerTwo;

    public Inner getChildInner() {
        return childInner;
    }

    @Inject
    void setInnerTwo(Inner inner) {
        this.innerTwo = inner;
    }

    @Override
    @Inject
    public void willOverrideInChildClass(Inner inner) {
        callCount++;
        this.childInner = inner;
    }

    public Inner getInnerTwo() {
        return innerTwo;
    }
}
