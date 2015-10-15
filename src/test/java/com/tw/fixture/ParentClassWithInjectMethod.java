package com.tw.fixture;

import javax.inject.Inject;

public class ParentClassWithInjectMethod {
    protected int callCount = 0;
    private Inner innerOne;
    protected Inner parentInner;

    public Inner getInnerOne() {
        return innerOne;
    }

    @Inject
    public void setInnerOne(Inner innerOne) {
        this.innerOne = innerOne;
    }

    @Inject
    public void willOverrideInChildClass(Inner inner) {
        callCount++;
        this.parentInner = inner;
    }

    public Inner getParentInner() {
        return parentInner;
    }

    public int getCallCount() {
        return callCount;
    }
}
