package com.feexon.xml;

import java.io.IOException;

/**
* @author Administrator
* @version 1.0 14-1-6,上午10:40
*/
public interface ElementClosure {
    ElementBuilder withText(Object value) throws IOException;

    ElementBuilder withNoText() throws IOException;

    ElementBuilder surround(ElementBuilder body);
}
