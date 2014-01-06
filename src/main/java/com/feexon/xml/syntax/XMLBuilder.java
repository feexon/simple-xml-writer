package com.feexon.xml.syntax;

import java.io.IOException;

/**
 * @author Administrator
 * @version 1.0 14-1-4,下午5:58
 */
public interface XMLBuilder {
    void writeTo(XMLClause writer) throws IOException;
}
