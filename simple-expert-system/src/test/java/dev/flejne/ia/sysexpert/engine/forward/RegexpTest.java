package dev.flejne.ia.sysexpert.engine.forward;

import static org.junit.Assert.*;

import org.junit.Test;

public class RegexpTest {

    private static final String TRUE_REGEXP =  "[YyOo]|[Oo]ui|[Yy]es|[Tt]rue";
 
    @Test
    public void regexpShoulReturnsTrueTest()
    {
        assertTrue("Y".matches(TRUE_REGEXP));
        assertTrue("y".matches(TRUE_REGEXP));
        assertTrue("O".matches(TRUE_REGEXP));
        assertTrue("o".matches(TRUE_REGEXP));
        assertTrue("Oui".matches(TRUE_REGEXP));
        assertTrue("oui".matches(TRUE_REGEXP));
        assertTrue("Yes".matches(TRUE_REGEXP));
        assertTrue("yes".matches(TRUE_REGEXP));
        assertTrue("True".matches(TRUE_REGEXP));
        assertTrue("true".matches(TRUE_REGEXP));
    }

}
