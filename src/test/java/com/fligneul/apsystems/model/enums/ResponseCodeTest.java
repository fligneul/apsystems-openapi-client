package com.fligneul.apsystems.model.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResponseCodeTest {

    @Test
    void testFromCode() {
        assertEquals(ResponseCode.SUCCESS, ResponseCode.fromCode(0));
        assertEquals(ResponseCode.NO_DATA, ResponseCode.fromCode(1001));
        assertEquals(ResponseCode.TOO_MANY_REQUESTS, ResponseCode.fromCode(7002));
        assertEquals(ResponseCode.UNKNOWN, ResponseCode.fromCode(9999));
    }

    @Test
    void testGetDescription() {
        assertEquals("Succeed to request.", ResponseCode.SUCCESS.getDescription());
        assertEquals("No data.", ResponseCode.NO_DATA.getDescription());
    }
}
