package ar.edu.unicen.ringo.console.ui.support;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXB;

import org.junit.Assert;
import org.junit.Test;

public class MenuTest {

    @Test
    public void serialization() {
        Menu m = new Menu();
        m.setId("main-menu");
        m.setStyleClass("main-menu");

        Item i = new Item();
        i.setHref("/");
        i.setLabel("General");
        i.setName("general");
        i.setStyleClass("something");

        Item sub = new Item();
        sub.setHref("/");
        sub.setLabel("Dashboard");
        sub.setName("dashboard");
        sub.setStyleClass("component");

        i.getItem().add(sub);
        m.getItems().add(i);

        StringWriter writer = new StringWriter();
        JAXB.marshal(m, writer);
        System.out.println(writer.toString());
    }

    @Test
    public void deserialization() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/menu.xml")) {
            Menu m = JAXB.unmarshal(is, Menu.class);
            Assert.assertNotNull(m);
            Assert.assertEquals("main-menu", m.getId());
            Assert.assertEquals("main-menu", m.getStyleClass());

            Assert.assertEquals(2, m.getItems().size());
            Item general = m.getItems().get(0);
            Assert.assertEquals("general", general.getName());
            Assert.assertEquals("General", general.getLabel());
            Assert.assertEquals(1, general.getItem().size());
        }
    }
}
