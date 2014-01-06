package com.feexon.xml;

import java.io.*;

import static com.feexon.xml.Including.content;

/**
 * @author Administrator
 * @version 1.0 14-1-4,下午4:56
 */
public class XMLWriter implements XMLClosure, Closeable, Flushable {
    private Writer out;

    public XMLWriter(OutputStream out, String charset) throws UnsupportedEncodingException {
        this(new OutputStreamWriter(out, charset));
    }

    public XMLWriter(Writer out) {
        this.out = out;
    }

    static private String startTag(String name) {
        return "<" + name + ">";
    }

    static private String endTag(String name) {
        return "</" + name + ">";
    }

    static private String data(Object value) {
        if(value==null){
            return "";
        }
        return "<![CDATA[" + value + "]]>";
    }


    public void include(String xml) throws IOException {
        out.write(xml);
    }

    public void include(ElementBuilder builder) throws IOException {
        builder.writeTo(this);
    }

    static public ElementWriter element(final String name) {
        return new ElementWriter() {
            public ElementBuilder body;

            public ElementBuilder withText(Object value) throws IOException {
               return surround(content(data(value)));
            }

            public ElementBuilder withNoText() throws IOException {
                return this;
            }

            public ElementBuilder surround(ElementBuilder body) {
                this.body = body;
                return this;
            }

            public void writeTo(XMLClosure writer) throws IOException {
                if (body != null) {
                    writer.include(startTag(name));
                    body.writeTo(writer);
                    writer.include(endTag(name));
                } else {
                    writer.include("<" + name + "/>");
                }
            }


        };
    }

    public static interface ElementWriter extends ElementBuilder {

        ElementBuilder withText(Object value) throws IOException;

        ElementBuilder withNoText() throws IOException;

        ElementBuilder surround(ElementBuilder body);
    }

    public void close() {
        if (out != null) {
            try {
                out.close();
            } catch (IOException ignored) {
            }
            out = null;
        }
    }

    public void flush() {
        if (out != null) {
            try {
                out.flush();
            } catch (IOException ignored) {

            }
        }
    }
}
