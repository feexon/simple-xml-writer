package com.feexon.xml;

import com.feexon.xml.syntax.XMLBuilder;
import com.feexon.xml.syntax.XMLClause;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.feexon.xml.Including.content;

/**
 * @author Administrator
 * @version 1.0 14-1-4,下午5:25
 */
public class Embody implements XMLClause, XMLBuilder {
    private List<XMLBuilder> builders = new ArrayList<XMLBuilder>();

    public void include(final String content) throws IOException {
        add(content(content));
    }

    private void add(XMLBuilder builder) {
        builders.add(builder);
    }

    public void include(final XMLBuilder builder) throws IOException {
        checking(builder);
        add(builder);
    }

    private void checking(Object definition) {
        if (definition == this) {
            throw new IllegalArgumentException("Include self!");
        }
    }

    public void writeTo(XMLClause writer) throws IOException {
        checking(writer);
        for (XMLBuilder builder : builders) {
            builder.writeTo(writer);
        }
    }
}
