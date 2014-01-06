package com.feexon.xml;

import com.feexon.xml.syntax.XMLBuilder;
import com.feexon.xml.syntax.XMLClause;

import java.io.IOException;

/**
* @author Administrator
* @version 1.0 14-1-4,下午6:53
*/
public class Including implements XMLBuilder {
    private final String body;

    private Including(String body) {
        this.body = body;
    }

    public static Including content(String body) {
        return new Including(body);
    }

    public void writeTo(XMLClause writer) throws IOException {
        if (body != null) {
            writer.include(body);
        }
    }
}
