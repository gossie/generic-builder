package de.gmcs.builder;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.gmcs.builder.model.DomainObject;
import de.gmcs.builder.model.PrivateObject;

public class GenericBuilderTest {

    @Test
    public void test() throws Exception {
        DomainObject result = GenericBuilder.getInstance(DomainObject.class)
                .with("setAttribute", "attributeValue")
                .with("setProperty", "property", Integer.valueOf(3))
                .build();

        assertThat(result.getAttribute(), is("attributeValue"));
        assertThat(result.getProperty("property"), is(3));
    }

    @Test
    public void testParametrizedConstructor() throws Exception {
        DomainObject result = GenericBuilder.getInstance(DomainObject.class, "attributeValue")
                .with("setProperty", "property", Integer.valueOf(3))
                .build();

        assertThat(result.getAttribute(), is("attributeValue"));
        assertThat(result.getProperty("property"), is(3));
    }

    @Test(expected = GenericBuilderException.class)
    public void testMissingMethod() throws Exception {
        GenericBuilder.getInstance(DomainObject.class)
                .with("setMissing", "attributeValue")
                .build();
    }

    @Test(expected = GenericBuilderException.class)
    public void testPrivateConstructor() throws Exception {
        GenericBuilder.getInstance(PrivateObject.class);
    }

}
