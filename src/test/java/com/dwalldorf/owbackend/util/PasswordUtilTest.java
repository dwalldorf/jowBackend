package com.dwalldorf.owbackend.util;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PasswordUtilTest {

    private final static int ITERATIONS = 100;

    @InjectMocks
    private PasswordUtil passwordUtil;

    @Test
    public void testCreateSaltConstantLength() throws Exception {
        int saltLength = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            byte[] salt = passwordUtil.createSalt();

            if (i == 0) {
                saltLength = salt.length;
            } else {
                assertEquals(saltLength, salt.length);
            }
        }
    }

    @Test
    public void testHashReturnsDistinctHashes() {
        final String PASSWORD = "test123";
        final byte[] SALT = passwordUtil.createSalt();

        Set<byte[]> hashes = new HashSet<>();
        for (int i = 0; i < ITERATIONS; i++) {
            byte[] hash = passwordUtil.hash(PASSWORD.toCharArray(), SALT);

            assertFalse(hashes.contains(hash));
            hashes.add(hash);
        }
    }

    @Test
    public void testIsExpectedPassword() {
        final String PASSWORD = "fsaHJSSAusdj12dDS*d7/dsa-DSpqsutvDIl";
        final byte[] SALT = passwordUtil.createSalt();


        Set<byte[]> hashes = new HashSet<>();
        for (int i = 0; i < ITERATIONS; i++) {
            byte[] hash = passwordUtil.hash(PASSWORD.toCharArray(), SALT);

            assertFalse(hashes.contains(hash));
            hashes.add(hash);
        }

        hashes.stream()
              .parallel()
              .forEach(hash -> assertTrue(passwordUtil.isExpectedPassword(PASSWORD.toCharArray(), SALT, hash)));
    }
}