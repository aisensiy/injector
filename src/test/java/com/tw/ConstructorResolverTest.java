package com.tw;

import com.tw.exception.CicularDependenciesException;
import com.tw.fixture.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConstructorResolverTest {

    private Container container;

    @Before
    public void setUp() throws Exception {
        container = new Container();
        container.register(Inner.class);
    }

    @Test
    public void should_not_set_fields_without_annotation_for_constructor() throws Exception {
        container.register(ClassWithoutInjectConstructor.class);
        ClassWithoutInjectConstructor classWithoutInjectConstructor = (ClassWithoutInjectConstructor) container.getResource(ClassWithoutInjectConstructor.class);
        assertThat(classWithoutInjectConstructor.getInner(), is(nullValue()));
    }

    @Test
    public void should_set_fields_with_inject_for_constructor() throws Exception {
        container.register(ClassWithInjectConstructor.class);
        ClassWithInjectConstructor classWithInjectConstructor = (ClassWithInjectConstructor) container.getResource(ClassWithInjectConstructor.class);
        assertThat(classWithInjectConstructor.getInner(), not(nullValue()));
    }

    @Test
    public void should_set_recusive_for_constructor() throws Exception {
        container.register(ClassWithInjectConstrutor.class);
        container.register(InnerClassWithConstructor.class);
        ClassWithInjectConstrutor wrapperWithConstructorTwo = (ClassWithInjectConstrutor) container.getResource(ClassWithInjectConstrutor.class);
        assertThat(wrapperWithConstructorTwo.getInnerClassWithConstructor().getInner(), not(nullValue()));
    }

    @Test(expected = CicularDependenciesException.class)
    public void should_raise_exception_for_circular_dependencies_for_constructor() throws Exception {
        container.register(CicularConstructorA.class);
        container.register(CicularConstructorB.class);
        container.getResource(CicularConstructorA.class);
    }

}