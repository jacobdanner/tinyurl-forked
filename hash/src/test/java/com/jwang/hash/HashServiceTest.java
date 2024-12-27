/* (C)2024 */
package com.jwang.hash;

import static org.junit.jupiter.api.Assertions.*;

import com.jwang.hash.repository.HashRepository;
import com.jwang.hash.repository.UsedHashRepository;
import com.jwang.hash.service.RetrieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HashServiceTest {

    @Autowired RetrieveService retrieveService;

    @Autowired UsedHashRepository usedHashRepository;

    @Autowired HashRepository hashRepository;

    // @Test
    public void testRetrieve() {
        String hash = retrieveService.retrieveOne();
        assertFalse(hashRepository.existsById(hash));
        assertTrue(usedHashRepository.existsById(hash));
        System.out.println(hash);
    }
}
