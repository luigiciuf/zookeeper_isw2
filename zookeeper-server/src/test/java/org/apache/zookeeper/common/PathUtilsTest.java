package org.apache.zookeeper.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;
import org.testng.annotations.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class PathUtilsTest {

    private String path;
    private Boolean isSequential;
    private boolean expectedException;
    public PathUtilsTest(String path, boolean expectedException){
        this.path=path;
        this.expectedException=expectedException;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { null, true },
                { "/valid/path", false },
                { "/a/b/./c",  true },
                { "/a/b/../c",  true },
                { "", true },

                // valori non validi
                { "/path\u0000/", true },
                {"/path\u0001", true},
                {"/path\u007F", true},
                { "/path\ud800",  true },
                {"/path\uFFF0", true },
                {"/path\uFFFE", true },
                {"/path\uF000 ", true},


                //casi aggiunti per coprire condition
                {"invalid/path", true},
                {"/invalid//path", true},
                {"/invalid/path/", true},
                {"/.", true},
                {"/..", true},
                {"/../", true},
                {"/./", true},
                //riga 48
                {"/", false},
                // riga 45
                {"/a/b\u0000", true}, // contiene carattere nullo
                { "/abc", false },
                { "/abc/", true },
                { "/a/b/c", false },
                { "/a/b/c/", true },
                { "/a/b/cd", false },
                { "/a/b/cd/", true },
                { "/a/b/./c", true },
                { "/a/b/../c", true },
                { "/a/b/...", false },


                //badua
                {"/\uffff", true}, // Character in range \ufff0 - \uffff
                {"/path/with/spaces", false},
                {"/path_with.dots", false},


                //pit
                {"/path\u001f", true },
                { "/path\u009f", true },
                { "/path\uf8ff", true },
                { "/path\ufff0", true },
                { "/path\uffff", true },

        });
    }

    @Test
    public void testValidatePath() {
        try {
            PathUtils.validatePath(path);
            if (expectedException) {
                // Se ci aspettiamo un'eccezione ma non viene lanciata
                assert false : "Expected IllegalArgumentException for path: " + path;
            }
        } catch (IllegalArgumentException e) {
            if (!expectedException) {
                // Se non ci aspettiamo un'eccezione ma viene lanciata
                assert false : "Did not expect IllegalArgumentException for path: " + path;
            }
        }
    }


}



