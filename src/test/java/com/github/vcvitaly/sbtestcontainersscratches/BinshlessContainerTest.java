package com.github.vcvitaly.sbtestcontainersscratches;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;

/**
 * SimpleTest.
 *
 * @author Vitalii Chura
 */
public class BinshlessContainerTest {

    private static final GenericContainer<?> container = new GenericContainer<>("docker.io/vcvitaly/bin_shless_container:0.1")
            .withExposedPorts(8080);

    @BeforeAll
    static void beforeAll() {
        container.start();
    }

    @Test
    void test() {
        Assertions.assertEquals(1, 1);
    }

}
