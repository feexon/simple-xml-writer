package com.feexon.xml;


import org.hamcrest.Matcher;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static com.feexon.xml.XMLWriter.element;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


/**
 * @author Administrator
 * @version 1.0 14-1-4,下午4:53
 */
public class XMLWriterTest {
    private StringWriter result = new StringWriter();

    private XMLWriter writer = new XMLWriter(result);

    private void assertResult(Matcher<String> matcher) {
        assertThat(result.toString(), matcher);
    }


    @Test
    public void includeAnEmptyElement() throws Exception {
        writer.include(element("content").withNoText());
        assertResult(equalTo("<content/>"));
    }

    @Test
    public void includeAnElementWithNullText() throws Exception {
        writer.include(element("content").withText(null));
        assertResult(equalTo("<content></content>"));
    }

    @Test
    public void includeAnElementWithEmptyString() throws Exception {
        writer.include(element("content").withText(""));
        assertResult(equalTo("<content></content>"));
    }

    @Test
    public void includeAnElementWithTextIncludingCDATA() throws Exception {
        writer.include(element("content").withText("abc"));
        assertResult(equalTo("<content><![CDATA[abc]]></content>"));
    }

    @Test
    public void includeElements() throws Exception {
        writer.include(element("first").withNoText());
        writer.include(element("last").withText("foo"));
        assertResult(equalTo("<first/><last><![CDATA[foo]]></last>"));
    }

    @Test
    public void surrounding() throws Exception {
        writer.include(element("xml").surround(new Embody() {{
            include(element("content").withNoText());
        }}));
        assertResult(equalTo("<xml><content/></xml>"));
    }

    @Test
    public void surroundWithMultiElements() throws Exception {
        writer.include(element("xml").surround(new Embody() {{
            include(element("first").withNoText());
            include(element("last").withNoText());
        }}));
        assertResult(equalTo("<xml><first/><last/></xml>"));
    }

    @Test
    public void surroundWithSameElementMultiTimes() throws Exception {
        writer.include(element("xml").surround(new Embody() {{
            include(element("same").withNoText());
            include(element("same").withNoText());
        }}));
        assertResult(equalTo("<xml><same/><same/></xml>"));
    }

    @Test
    public void surroundings() throws Exception {
        writer.include(element("xml").surround(new Embody() {{
            include(element("articles").surround(new Embody() {{
                include(element("article").withText("java"));
            }}));
        }}));
        assertResult(equalTo("<xml><articles><article><![CDATA[java]]></article></articles></xml>"));
    }

    @Test
    public void should_includeSelf_raiseException() throws Exception {
        try {
            writer.include(element("xml").surround(new Embody() {{
                include(this);
            }}));
            fail("should raise exception");
        } catch (IllegalArgumentException expected) {

        }
    }

    @Test
    public void nestedSurrounding() throws Exception {
        writer.include(element("xml").surround(new Embody() {{
            include(element("nested").withNoText());
        }}));
        assertResult(equalTo("<xml><nested/></xml>"));
    }



    @Test
    public void close() throws Exception {
        new WriterSpy() {{
            new XMLWriter(this).close();
        }}.assertClosed();
    }

    private static class WriterSpy extends Writer {
        private boolean closed = false;

        public void write(char[] buff, int off, int len) throws IOException {

        }

        public void flush() throws IOException {

        }

        public void close() throws IOException {
            closed = true;
        }

        public void assertClosed() {
            assertThat(closed, equalTo(true));
        }
    }
}
