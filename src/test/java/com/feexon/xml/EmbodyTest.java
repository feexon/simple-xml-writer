package com.feexon.xml;

import com.feexon.xml.supports.XMLAssertion;
import org.junit.Before;
import org.junit.Test;

import static com.feexon.xml.XMLWriter.content;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Administrator
 * @version 1.0 14-1-6,上午11:34
 */
public class EmbodyTest {

    private XMLAssertion assertion;
    private Embody embody;

    @Before
    public void setUp() throws Exception {
        assertion = new XMLAssertion();
        embody = new Embody();
    }

    @Test
    public void includeText() throws Exception {
        embody.include("text");
        assertion.assertRendering(embody, equalTo("text"));
    }

    @Test
    public void includeText_withNullValue() throws Exception {
        embody.include((String) null);
        assertion.assertNotRendering(embody);
    }

    @Test
    public void include() throws Exception {
        embody.include(content("<element/>"));
        assertion.assertRendering(embody, equalTo("<element/>"));
    }

}
