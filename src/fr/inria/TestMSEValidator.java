package fr.inria;

import ch.akuhn.fame.parser.ParseError;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestMSEValidator {

    protected MSEValidator valid;

    @Before
    public void setup() {
        valid = new MSEValidator();
    }

    @Test
    public void testEmptyMSE() throws Exception {
        assertEquals(0, valid.validate("()"));
    }


    @Test(expected=ParseError.class)
    public void testIncorrectMSE() throws Exception {
        valid.validate("(");
    }

    @Test
    public void testOneEntity() throws Exception {
        assertEquals(1, valid.validate("((FAMIX.Class (id: 1) (name 'toto')))"));
    }

    @Test
    public void testClassWithMethod() throws Exception {
        assertEquals(2, valid.validate("((FAMIX.Class (id: 1)) (FAMIX.Method (id: 2) (parentType (ref: 1))))"));
    }

    @Test(expected=Error.class)
    public void testWrongMethodParent() throws Exception {
        valid.validate("((FAMIX.Class (id: 1)) (FAMIX.Method (id: 2) (parentType (ref: 2))))");
    }

}
