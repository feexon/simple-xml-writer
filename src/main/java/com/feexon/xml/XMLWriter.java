package com.feexon.xml;

import com.feexon.xml.syntax.XMLBuilder;
import com.feexon.xml.syntax.ElementClause;
import com.feexon.xml.syntax.XMLClause;

import java.io.*;

/**
 * @author Administrator
 * @version 1.0 14-1-4,下午4:56
 */
public class XMLWriter implements XMLClause, Closeable, Flushable {
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
        if(value==null||value.equals("")){
            return "";
        }
        return "<![CDATA[" + value + "]]>";
    }


    public void include(String xml) throws IOException {
        out.write(xml);
    }

    public void include(XMLBuilder builder) throws IOException {
        builder.writeTo(this);
    }

    static public XMLBuilder content(String content){
        return Including.content(content);
    }

    static public ElementClause element(final String name) {
        return new ElementBuilder(name);
    }

    private static class ElementBuilder implements ElementClause, XMLBuilder {
        private final String name;
        public XMLBuilder body;

        public ElementBuilder(String name) {
            this.name = name;
        }

        public XMLBuilder withText(Object value) throws IOException {
            return surround(content(data(value)));
        }

        public XMLBuilder withNoText() throws IOException {
            return this;
        }

        public XMLBuilder surround(XMLBuilder body) {
            this.body = body;
            return this;
        }

        public void writeTo(XMLClause writer) throws IOException {
            if (body != null) {
                writer.include(startTag(name));
                body.writeTo(writer);
                writer.include(endTag(name));
            } else {
                writer.include("<" + name + "/>");
            }
        }


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
