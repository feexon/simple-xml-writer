package com.feexon.xml;

import java.io.IOException;

/**
 * @author Administrator
 * @version 1.0 14-1-4,下午5:49
 */
public interface XMLClosure {

    void include(ElementBuilder builder) throws IOException;

    void surround(String name, ElementBuilder builder) throws IOException;

    void include(String xml) throws IOException;
}
