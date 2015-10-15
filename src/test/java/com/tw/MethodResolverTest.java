package com.tw;

import com.tw.fixture.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class MethodResolverTest {

    private Container container;

    @Before
    public void setUp() throws Exception {
        container = new Container();
        container.register(Inner.class);
    }

    @Test
    public void should_not_run_for_methods_without_annotation() throws Exception{
        container.register(ClassWithoutInjectMethod.class);
        ClassWithoutInjectMethod classWithoutInjectMethod = (ClassWithoutInjectMethod) container.getResource(ClassWithoutInjectMethod.class);
        assertThat(classWithoutInjectMethod.getInner(), is(nullValue()));
    }

    @Test
    public void should_run_for_methods_with_annotation() throws Exception{
        container.register(ClassWithInjectMethod.class);
        ClassWithInjectMethod classWithInjectMethod = (ClassWithInjectMethod) container.getResource(ClassWithInjectMethod.class);
        assertThat(classWithInjectMethod.getInner(), not(nullValue()));
    }

    @Test
    public void should_run_super_class_method() throws Exception {
        container.register(ParentClassWithInjectMethod.class);
        container.register(ChildClassWithInjectMethod.class);
        ChildClassWithInjectMethod childClassWithInjectMethod = (ChildClassWithInjectMethod) container.getResource(ChildClassWithInjectMethod.class);
        assertThat(childClassWithInjectMethod.getInnerOne(), not(nullValue()));
        assertThat(childClassWithInjectMethod.getInnerTwo(), not(nullValue()));
    }

    @Test
    public void should_run_static_method_once() throws Exception {
        container.register(ClassWithStaticInjectMethod.class);
        container.getResource(ClassWithStaticInjectMethod.class);
        Inner inner = ClassWithStaticInjectMethod.inner;
        container.getResource(ClassWithStaticInjectMethod.class);
        assertThat(inner, sameInstance(ClassWithStaticInjectMethod.inner));
    }

    @Test
    public void should_only_run_override_method() throws Exception {
        container.register(ParentClassWithInjectMethod.class);
        container.register(ChildClassWithInjectMethod.class);

        ChildClassWithInjectMethod childClassWithInjectMethod = (ChildClassWithInjectMethod) container.getResource(ChildClassWithInjectMethod.class);
        assertThat(childClassWithInjectMethod.getParentInner(), is(nullValue()));
        assertThat(childClassWithInjectMethod.getChildInner(), not(nullValue()));
        assertThat(childClassWithInjectMethod.getCallCount(), is(1));
    }

}