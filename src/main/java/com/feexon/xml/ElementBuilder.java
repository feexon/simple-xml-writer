package com.feexon.xml;

import java.io.IOException;

/**
 * @author Administrator
 * @version 1.0 14-1-4,下午5:58
 */
public interface ElementBuilder {
    void writeTo(XMLClosure writer) throws IOException;
}
