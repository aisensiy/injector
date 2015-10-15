package com.tw.fixture;

import javax.inject.Inject;

public class ClassWithStaticInjectMethod {
    public static Inner inner;

    @Inject
    public static void setInner(Inner inner) {
        ClassWithStaticInjectMethod.inner = inner;
    }
}
