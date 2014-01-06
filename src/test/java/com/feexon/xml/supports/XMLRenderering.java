package com.feexon.xml.supports;

import com.feexon.xml.syntax.XMLBuilder;
import com.feexon.xml.syntax.XMLClause;
import org.hamcrest.Matcher;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Administrator
 * @version 1.0 14-1-6,上午11:45
 */
public class XMLRenderering implements XMLClause {
    private StringBuilder out = new StringBuilder();
    private boolean rendered = false;

    public static XMLRenderering render(final XMLBuilder builder) throws IOException {
        return new XMLRenderering() {{
            builder.writeTo(this);
        }};
    }

    public static Expectation xml(final Matcher<String> resultMatcher) {
        return new Expectation() {
            public void check(XMLRenderering source) {
                assertThat(source.rendered, is(true));
                assertThat(source.out.toString(), resultMatcher);
            }
        };
    }

    public void include(XMLBuilder builder) throws IOException {
        builder.writeTo(this);
    }

    public void include(String xml) throws IOException {
        rendered = true;
        out.append(xml);
    }


    public void expect(Expectation expectation) {
        expectation.check(this);
    }

    public static interface Expectation {
        void check(XMLRenderering source);
    }

    static public Expectation hasNotRenderered() {
        return new Expectation() {
            public void check(XMLRenderering source) {
                assertThat(source.rendered, is(false));
            }
        };
    }


}
