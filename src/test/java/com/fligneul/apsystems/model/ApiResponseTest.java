package com.fligneul.apsystems.model;

import com.fligneul.apsystems.model.enums.ResponseCode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ApiResponseTest {

    @Test
    void testSuccessLogic() {
        ApiResponse<String> response = new ApiResponse<>();
        response.setCode(0);
        assertTrue(response.isSuccess());
        assertEquals(ResponseCode.SUCCESS, response.getResponseCodeEnum());
    }

    @Test
    void testErrorLogic() {
        ApiResponse<String> response = new ApiResponse<>();
        response.setCode(1001);
        assertFalse(response.isSuccess());
        assertEquals(ResponseCode.NO_DATA, response.getResponseCodeEnum());
        assertEquals("No data.", response.getErrorMessage());
    }

    @Test
    void testDataAccess() {
        ApiResponse<String> response = new ApiResponse<>();
        response.setData("testData");
        assertEquals("testData", response.getData());
    }
}
