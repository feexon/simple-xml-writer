package com.feexon.xml;

import com.feexon.xml.supports.XMLAssertion;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @author Administrator
 * @version 1.0 14-1-6,上午11:44
 */
public class IncludingTest {

    private XMLAssertion assertion;

    @Before
    public void setUp() throws Exception {
        assertion = new XMLAssertion();
    }

    @Test
    public void include() throws Exception {
        assertion.assertRendering(Including.content("<abc/>"), equalTo("<abc/>"));
    }

    @Test
    public void includeNull() throws Exception {
        assertion.assertNotRendering(Including.content(null));
    }
}
