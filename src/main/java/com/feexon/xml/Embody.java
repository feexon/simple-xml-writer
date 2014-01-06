package com.feexon.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0 14-1-4,下午5:25
 */
public class Embody implements XMLClosure, ElementBuilder {
    private List<ElementBuilder> builders = new ArrayList<ElementBuilder>();

    private static abstract class AbstractElementBuilder implements ElementBuilder {

        protected String name;
        protected Object value;

        public AbstractElementBuilder(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        abstract public void writeTo(XMLClosure writer) throws IOException;

    }

    private static class EmbodyElement extends AbstractElementBuilder {
        public EmbodyElement(String name, ElementBuilder definition) {
            super(name, definition);
        }

        public void writeTo(XMLClosure writer) throws IOException {
            writer.surround(name, (ElementBuilder) value);
        }
    }

    public void surround(String name, ElementBuilder builder) {
        checking(builder);
        builders.add(new EmbodyElement(name, builder));
    }

    private void checking(Object definition) {
        if (definition == this) {
            throw new IllegalArgumentException("Include self!");
        }
    }

    public void include(final String content) throws IOException {
        include(Including.content(content));
    }

    public void include(final ElementBuilder builder) throws IOException {
        checking(builder);
        builders.add(builder);
    }

    public void writeTo(XMLClosure writer) throws IOException {
        checking(writer);
        for (ElementBuilder builder : builders) {
            builder.writeTo(writer);
        }
    }
}
