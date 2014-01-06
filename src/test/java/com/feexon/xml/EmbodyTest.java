package com.feexon.xml;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.feexon.xml.XMLWriter.content;
import static com.feexon.xml.supports.XMLRenderering.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.fail;

/**
 * @author Administrator
 * @version 1.0 14-1-6,上午11:34
 */
public class EmbodyTest {

    private Embody embody;

    @Before
    public void setUp() throws Exception {
        embody = new Embody();
    }

    @Test
    public void includeText() throws Exception {
        embody.include("text");
        render(embody).expect(result(equalTo("text")));
    }

    @Test
    public void includeText_withNullValue() throws Exception {
        embody.include((String) null);
        render(embody).expect(hasNotRenderered());
    }

    @Test
    public void include() throws Exception {
        embody.include(content("<element/>"));
        render(embody).expect(result(equalTo("<element/>")));
    }

    @Test
    public void should_includeSelf_raiseException() throws Exception {
        try {
            embody.include(embody);
            fail("should raise exception");
        } catch (IllegalArgumentException expected) {
        }
    }
}
