package com.feexon.xml.supports;

import com.feexon.xml.syntax.XMLBuilder;
import com.feexon.xml.syntax.XMLClause;
import org.hamcrest.Matcher;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Administrator
 * @version 1.0 14-1-6,上午11:45
 */
public class XMLAssertion implements XMLClause {
    private StringBuilder out = new StringBuilder();
    private boolean rendered = false;

    public void include(XMLBuilder builder) throws IOException {
        builder.writeTo(this);
    }

    public void include(String xml) throws IOException {
        rendered=true;
        out.append(xml);
    }

    public void assertRendering(XMLBuilder builder, Matcher<String> resultMatcher) throws IOException {
        builder.writeTo(this);
        assertThat(rendered,is(true));
        assertThat(out.toString(), resultMatcher);
    }

    public void assertNotRendering(XMLBuilder builder) throws IOException {
        builder.writeTo(this);
        assertThat(rendered, is(false));
    }
}
