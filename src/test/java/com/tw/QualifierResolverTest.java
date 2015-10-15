package com.tw;

import com.tw.fixture.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class QualifierResolverTest {
    private Container container;

    @Before
    public void setUp() throws Exception {
        container = new Container();
        container.register(Inner.class);
    }

    @Test
    public void should_get_qualifier_field() throws Exception {
        container.register(ClassWithQualifiedInner.class);
        container.registerByQualifier(new QualifierMeta(QualifierInner.class), InnerCreatedByQualifier.class);
        ClassWithQualifiedInner classWithQualifiedInner = (ClassWithQualifiedInner) container.getResource(ClassWithQualifiedInner.class);
        assertThat(classWithQualifiedInner.getInner().getClass().getName(), is(InnerCreatedByQualifier.class.getName()));
    }


    @Test
    public void should_set_qualifier_field_for_parameter() throws Exception {
        container.register(ClassWithQualifierForConstructor.class);
        container.registerByQualifier(new QualifierMeta(QualifierInner.class), InnerCreatedByQualifier.class);
        ClassWithQualifierForConstructor classWithQualifierForConstructor = (ClassWithQualifierForConstructor) container.getResource(ClassWithQualifierForConstructor.class);
        assertThat(classWithQualifierForConstructor.getInner().getClass().getName(), is(InnerCreatedByQualifier.class.getName()));
    }


    @Test
    public void should_support_meta_data_for_qualifier() throws Exception {
        container.register(ClassWithBlackInner.class);
        container.registerByQualifier(new QualifierMeta(QualifierInnerWithMetaData.class).meta("color", "black"), new ColorInner("black"));
        container.registerByQualifier(new QualifierMeta(QualifierInnerWithMetaData.class).meta("color", "red"), new ColorInner("red"));

        ClassWithBlackInner classWithBlackInner = (ClassWithBlackInner) container.getResource(ClassWithBlackInner.class);
        assertThat(classWithBlackInner.getColorInner().getColor(), is("black"));
    }
}