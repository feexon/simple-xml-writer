package com.feexon.xml;

import org.junit.Test;

import static com.feexon.xml.Including.content;
import static com.feexon.xml.supports.XMLRenderering.*;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @author Administrator
 * @version 1.0 14-1-6,上午11:44
 */
public class IncludingTest {


    @Test
    public void include() throws Exception {
        render(content("<abc/>")).expect(xml(equalTo("<abc/>")));
    }

    @Test
    public void includeNull() throws Exception {
        render(content(null)).expect(hasNotRenderered());
    }
}
