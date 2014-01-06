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
    public void writeAnEmptyElement() throws Exception {
        writer.include(element("content").withNoText());
        assertResult(equalTo("<content/>"));
    }

    @Test
    public void writeElements() throws Exception {
        writer.include(element("first").withNoText());
        writer.include(element("last").withText("foo"));
        assertResult(equalTo("<first/><last><![CDATA[foo]]></last>"));
    }

    @Test
    public void writeAnElementWithEmptyString() throws Exception {
        writer.include(element("content").withText(""));
        assertResult(equalTo("<content><![CDATA[]]></content>"));
    }

    @Test
    public void writeAnElementWithTextIncludingCDATA() throws Exception {
        writer.include(element("content").withText("abc"));
        assertResult(equalTo("<content><![CDATA[abc]]></content>"));
    }

    @Test
    public void surrounding() throws Exception {
        writer.surround("xml", new Embody() {{
            include(element("content").withNoText());
        }});
        assertResult(equalTo("<xml><content/></xml>"));
    }

    @Test
    public void surroundWithMultiElements() throws Exception {
        writer.surround("xml", new Embody() {{
            include(element("first").withNoText());
            include(element("last").withNoText());
        }});
        assertResult(equalTo("<xml><first/><last/></xml>"));
    }

    @Test
    public void surroundWithSameElementMoreTimes() throws Exception {
        writer.surround("xml", new Embody() {{
            include(element("same").withNoText());
            include(element("same").withNoText());
        }});
        assertResult(equalTo("<xml><same/><same/></xml>"));
    }

    @Test
    public void surroundWithElement() throws Exception {
        writer.surround("xml", new Embody() {{
            surround("articles", new Embody() {{
                include(element("article").withText("java"));
            }});
        }});
        assertResult(equalTo("<xml><articles><article><![CDATA[java]]></article></articles></xml>"));
    }

    @Test
    public void should_includeSelf_raiseException() throws Exception {
        try {
            writer.surround("xml", new Embody() {{
                surround("articles", this);
            }});
            fail("should raise exception");
        } catch (IllegalArgumentException expected) {

        }
    }

    @Test
    public void include() throws Exception {
        writer.include(new ElementBuilder(){
            public void writeTo(XMLClosure writer) throws IOException {
                writer.include("<nested/>");
            }
        });
        assertResult(equalTo("<nested/>"));
    }

    @Test
    public void includeWithSurrounding() throws Exception {
        writer.surround("xml",new Embody(){{
           include(new ElementBuilder() {
               public void writeTo(XMLClosure writer) throws IOException {
                   writer.include("<nested/>");
               }
           });
        }});
        assertResult(equalTo("<xml><nested/></xml>"));
    }

    @Test
    public void includeTextWithSurrounding() throws Exception {
        writer.surround("xml",new Embody(){{
           include("<text/>");
        }});
        assertResult(equalTo("<xml><text/></xml>"));
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
