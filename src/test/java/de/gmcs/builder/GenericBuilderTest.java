package de.gmcs.builder;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.gmcs.builder.model.DomainObject;
import de.gmcs.builder.model.PrivateObject;

public class GenericBuilderTest {

    @Test
    public void testAll() throws Exception {
        DomainObject result = GenericBuilder.getInstance(DomainObject.class)
                .set("attribute", "attributeValue")
                .set("unsettableAttribute", "unsettableAttributeValue")
                .invoke("setProperty", "property", Integer.valueOf(3))
                .invoke("perform")
                .build();

        assertThat(result.getAttribute(), is("attributeValue"));
        assertThat(result.getUnsettableAttribute(), is("unsettableAttributeValue"));
        assertThat(result.getProperty("property"), is(3));
        assertThat(result.getPerformCounter(), is(1));
    }

    @Test
    public void testParametrizedConstructor() throws Exception {
        DomainObject result = GenericBuilder.getInstance(DomainObject.class, "attributeValue")
                .invoke("setProperty", "property", Integer.valueOf(3))
                .build();

        assertThat(result.getAttribute(), is("attributeValue"));
        assertThat(result.getProperty("property"), is(3));
    }

    @Test
    public void testFactoryMethod() throws Exception {
        PrivateObject result = GenericBuilder.getInstanceFromFactoryMethod(PrivateObject.class, "getInstance")
                .invoke("setAttribute", "attribute")
                .build();

        assertThat(result.getAttribute(), is("attribute"));
    }

    @Test
    public void testFactoryMethodWithArgs() throws Exception {
        PrivateObject result = GenericBuilder.getInstanceFromFactoryMethod(PrivateObject.class, "getInstance", "attribute")
                .build();

        assertThat(result.getAttribute(), is("attribute"));
    }

    @Test(expected = GenericBuilderException.class)
    public void testMissingSetterMethod() throws Exception {
        GenericBuilder.getInstance(DomainObject.class)
                .set("missing", "attributeValue");
    }

    @Test(expected = GenericBuilderException.class)
    public void testMissingFactoryMethod() throws Exception {
        GenericBuilder.getInstanceFromFactoryMethod(PrivateObject.class, "getInstanceMissing");
    }

    @Test(expected = GenericBuilderException.class)
    public void testMissingMethod() throws Exception {
        GenericBuilder.getInstance(DomainObject.class)
                .invoke("setMissing", "attributeValue")
                .build();
    }

    @Test(expected = GenericBuilderException.class)
    public void testPrivateConstructor() throws Exception {
        GenericBuilder.getInstance(PrivateObject.class);
    }
}
