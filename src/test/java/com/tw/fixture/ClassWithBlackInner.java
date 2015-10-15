package com.tw.fixture;

import javax.inject.Inject;

public class ClassWithBlackInner {
    @QualifierInnerWithMetaData(color="black")
    @Inject
    private ColorInner colorInner;

    public ColorInner getColorInner() {
        return colorInner;
    }
}
