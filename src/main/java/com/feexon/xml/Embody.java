package com.feexon.xml;

import com.feexon.xml.syntax.ElementBuilder;
import com.feexon.xml.syntax.XMLClause;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0 14-1-4,下午5:25
 */
public class Embody implements XMLClause, ElementBuilder {
    private List<ElementBuilder> builders = new ArrayList<ElementBuilder>();

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

    public void writeTo(XMLClause writer) throws IOException {
        checking(writer);
        for (ElementBuilder builder : builders) {
            builder.writeTo(writer);
        }
    }
}
