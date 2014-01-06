package com.feexon.xml;

import java.io.IOException;

/**
* @author Administrator
* @version 1.0 14-1-4,下午6:53
*/
public class Including implements ElementBuilder {
    private final String body;

    private Including(String body) {
        this.body = body;
    }

    public static Including content(String body) {
        return new Including(body);
    }

    public void writeTo(XMLClosure writer) throws IOException {
        if (body != null) {
            writer.include(body);
        }
    }
}
