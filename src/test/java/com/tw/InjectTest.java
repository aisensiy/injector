package com.tw;

import com.tw.fixture.ClassWithNamedInnerField;
import com.tw.fixture.Inner;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class InjectTest {

    private Container container;

    @Before
    public void setUp() throws Exception {
        container = new Container();
        container.register(Inner.class);
    }

    @Test
    public void should_set_named_field() throws Exception {
        Inner namedInner = new Inner();
        container.registerByName("namedInner", namedInner);
        container.register(ClassWithNamedInnerField.class);
        ClassWithNamedInnerField classWithNamedInnerField = (ClassWithNamedInnerField) container.getResource(ClassWithNamedInnerField.class);
        assertThat(classWithNamedInnerField.getInner(), sameInstance(namedInner));
    }
}

