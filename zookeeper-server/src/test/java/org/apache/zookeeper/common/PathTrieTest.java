package org.apache.zookeeper.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class PathTrieTest {

    private final String path;
    private final Class<? extends Exception> expectedException;

    public PathTrieTest(String path, Class<? extends Exception> expectedException) {
        this.path = path;
        this.expectedException = expectedException;
    }
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { null, NullPointerException.class },
                { "/path", null },
               // {"?????????", IllegalArgumentException.class},
                { "", IllegalArgumentException.class },

        });
    }


    // Metodo di test che utilizza i parametri forniti
    @Test
    public void testAddPath() {
        PathTrie trie = new PathTrie();

        if (expectedException != null) {
            Exception exception = assertThrows(expectedException, () -> {
                trie.addPath(path);
            });
            assertNotNull(exception);
        } else {
            assertDoesNotThrow(() -> {
                trie.addPath(path);
            });

        }
    }

    @Test
    public void testChildNodeExists(){
        PathTrie trie= new PathTrie();
        assertDoesNotThrow(()->{
            trie.addPath("/path");
            trie.addPath("/path/valido");
            //pit
            assertTrue(trie.existsNode("/path/valido"));


        });
    }


    }






