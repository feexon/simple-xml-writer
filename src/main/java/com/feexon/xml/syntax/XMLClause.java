package com.feexon.xml.syntax;

import java.io.IOException;

/**
 * @author Administrator
 * @version 1.0 14-1-4,下午5:49
 */
public interface XMLClause {

    void include(XMLBuilder builder) throws IOException;

    void include(String xml) throws IOException;
}
