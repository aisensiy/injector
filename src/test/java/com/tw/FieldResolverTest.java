package com.tw;

import com.tw.exception.CicularDependenciesException;
import com.tw.exception.ClassNotFoundInContainerException;
import com.tw.exception.InjectFinalFieldException;
import com.tw.fixture.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class FieldResolverTest {

    private Container container;

    @Before
    public void setUp() throws Exception {
        container = new Container();
        container.register(Inner.class);
    }

    @Test
    public void should_not_set_field_if_no_inject() throws Exception {
        container.register(ClassWithoutInjectField.class);
        ClassWithoutInjectField classWithoutInjectField = (ClassWithoutInjectField) container.getResource(ClassWithoutInjectField.class);
        assertThat(classWithoutInjectField.getInner(), is(nullValue()));
    }

    @Test
    public void should_set_field_with_inject() throws Exception {
        container.register(ClassWithInjectField.class);
        container.register(InnerClassWithInjectField.class);
        ClassWithInjectField classWithInjectField = (ClassWithInjectField) container.getResource(ClassWithInjectField.class);
        assertThat(classWithInjectField.getInner(), not(nullValue()));
    }

    @Test
    public void should_set_fields_if_have_inject() throws Exception {
        container.register(ClassWithMultiInjectFields.class);
        ClassWithMultiInjectFields classWithMultiInjectFields = (ClassWithMultiInjectFields) container.getResource(ClassWithMultiInjectFields.class);
        assertThat(classWithMultiInjectFields.getInnerOne(), not(nullValue()));
        assertThat(classWithMultiInjectFields.getInnerTwo(), not(nullValue()));
    }

    @Test
    public void should_set_fields_for_recursive_declare() throws Exception {
        container.register(ClassWithInjectField.class);
        container.register(InnerClassWithInjectField.class);

        ClassWithInjectField classWithInjectField = (ClassWithInjectField) container.getResource(ClassWithInjectField.class);
        assertThat(classWithInjectField.getInnerClassWithInjectField().getInner(), not(nullValue()));
    }

    @Test
    public void should_set_static_field_once() throws Exception {
        container.register(ClassWithStaticInjectField.class);
        container.getResource(ClassWithStaticInjectField.class);

        Inner staticInner = ClassWithStaticInjectField.inner;
        container.getResource(ClassWithStaticInjectField.class);
        assertThat(staticInner, sameInstance(ClassWithStaticInjectField.inner));
    }

    @Test(expected = ClassNotFoundInContainerException.class)
    public void should_raise_exception_when_bean_is_not_register() throws Exception {
        container.getResource(ClassNotRegistered.class);
    }

    @Test(expected = InjectFinalFieldException.class)
    public void should_raise_exception_when_set_annotation_for_final_field() throws Exception {
        container.register(ClassWithFinalField.class);
        container.getResource(ClassWithFinalField.class);
    }

    @Test
    public void should_first_set_for_super_class_fields() throws Exception {
        container.register(ParentClassWithInjectField.class);
        container.register(ChildClassWithInjectField.class);
        ChildClassWithInjectField childWrapper = (ChildClassWithInjectField) container.getResource(ChildClassWithInjectField.class);
        assertThat(childWrapper.getInnerOne(), not(nullValue()));
    }

    @Test(expected = CicularDependenciesException.class)
    public void should_set_throw_excepations_for_circular_dependencies() throws Exception {
        container.register(CircularA.class);
        container.register(CircularB.class);

        CircularA circularA = (CircularA) container.getResource(CircularA.class);
        CircularB circularB = (CircularB) container.getResource(CircularB.class);

        assertThat(circularA.getCircularB(), not(nullValue()));
        assertThat(circularB.getCircularA(), not(nullValue()));
    }



}