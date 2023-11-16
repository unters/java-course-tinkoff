package edu.hw6;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class Task6Test {
    @Test
    void scanPorts_MethodCalled_NoExceptionIsThrown() {
        assertDoesNotThrow(Task6::scanPorts);
    }
}
