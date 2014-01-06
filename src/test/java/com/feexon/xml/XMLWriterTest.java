package com.feexon.xml;


import com.feexon.xml.supports.XMLRenderering;
import com.feexon.xml.syntax.ElementClause;
import com.feexon.xml.syntax.XMLBuilder;
import com.feexon.xml.syntax.XMLClause;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static com.feexon.xml.XMLWriter.element;
import static com.feexon.xml.supports.XMLRenderering.render;
import static com.feexon.xml.supports.XMLRenderering.result;
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

    private XMLRenderering rendering() throws IOException {
        return render(new XMLBuilder() {
            public void writeTo(XMLClause writer) throws IOException {
                writer.include(result.toString());
            }
        });
    }


    @Test
    public void includeAnEmptyElement() throws Exception {
        writer.include(element("content").withNoText());
        rendering().expect(result(equalTo("<content/>")));
    }

    @Test
    public void includeAnElementWithNullText() throws Exception {
        writer.include(element("content").withText(null));
        rendering().expect(result(equalTo("<content></content>")));
    }

    @Test
    public void includeAnElementWithEmptyString() throws Exception {
        writer.include(element("content").withText(""));
        rendering().expect(result(equalTo("<content></content>")));
    }

    @Test
    public void includeAnElementWithTextIncludingCDATA() throws Exception {
        writer.include(element("content").withText("abc"));
        rendering().expect(result(equalTo("<content><![CDATA[abc]]></content>")));
    }

    @Test
    public void includeElements() throws Exception {
        writer.include(element("first").withNoText());
        writer.include(element("last").withText("foo"));
        rendering().expect(result(equalTo("<first/><last><![CDATA[foo]]></last>")));
    }

    @Test
    public void surrounding() throws Exception {
        writer.include(element("xml").surround(new Embody() {{
            include(element("content").withNoText());
        }}));
        rendering().expect(result(equalTo("<xml><content/></xml>")));
    }

    @Test
    public void surroundWithMultiElements() throws Exception {
        writer.include(element("xml").surround(new Embody() {{
            include(element("first").withNoText());
            include(element("last").withNoText());
        }}));
        rendering().expect(result(equalTo("<xml><first/><last/></xml>")));
    }

    @Test
    public void surroundWithSameElementMultiTimes() throws Exception {
        writer.include(element("xml").surround(new Embody() {{
            include(element("same").withNoText());
            include(element("same").withNoText());
        }}));
        rendering().expect(result(equalTo("<xml><same/><same/></xml>")));
    }

    @Test
    public void surroundings() throws Exception {
        writer.include(element("xml").surround(new Embody() {{
            include(element("articles").surround(new Embody() {{
                include(element("article").withText("java"));
            }}));
        }}));
        rendering().expect(result(equalTo("<xml><articles><article><![CDATA[java]]></article></articles></xml>")));
    }

    @Test
    public void should_includeSelf_raiseException() throws Exception {
        ElementClause element = element("xml");
        try {
            element.surround(element.withNoText());
            fail("should raise exception");
        } catch (IllegalArgumentException expected) {

        }
    }

    @Test
    public void nestedSurrounding() throws Exception {
        writer.include(element("xml").surround(new Embody() {{
            include(element("nested").withNoText());
        }}));
        rendering().expect(result(equalTo("<xml><nested/></xml>")));
    }


    @Test
    public void close() throws Exception {
        new WriterSpy() {{
            new XMLWriter(this).close();
        }}.assertClosed();
    }

    @Test
    public void flush() throws Exception {
        new WriterSpy() {{
            new XMLWriter(this).flush();
        }}.assertFlushed();
    }

    private static class WriterSpy extends Writer {
        private boolean closed = false;
        private Boolean flashed = false;

        public void write(char[] buff, int off, int len) throws IOException {

        }

        public void flush() throws IOException {
            flashed = true;
        }

        public void close() throws IOException {
            closed = true;
        }

        public void assertClosed() {
            assertThat(closed, equalTo(true));
        }

        public void assertFlushed() {
            assertThat(flashed, equalTo(true));
        }
    }
}
