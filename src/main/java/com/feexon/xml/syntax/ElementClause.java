package com.feexon.xml.syntax;

import java.io.IOException;

/**
* @author Administrator
* @version 1.0 14-1-6,上午10:40
*/
public interface ElementClause {
    XMLBuilder withText(Object value) throws IOException;

    XMLBuilder withNoText() throws IOException;

    XMLBuilder surround(XMLBuilder body);
}
