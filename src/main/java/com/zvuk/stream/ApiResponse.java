package com.zvuk.stream;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = { "httpStatus" })
public class ApiResponse {

    private enum ResponseMessages {
        SUCCESS("success"),
        FAIL("fail"),
        ERROR("error");

        private final String message;

        ResponseMessages(String message) {
            this.message = message;
        }
    }

    String status;
    Integer code;
    String message;
    Map data;
    Map errors;

    public ApiResponse success(Map data, Map errors) {
        HttpStatus httpStatus = HttpStatus.OK;
        String message = ResponseMessages.SUCCESS.message;

        return buildResponseObject(httpStatus, message, data, errors);
    }

    public ApiResponse success(Map data) {
        HttpStatus httpStatus = HttpStatus.OK;
        String message = ResponseMessages.SUCCESS.message;

        return buildResponseObject(httpStatus, message, data, null);
    }

    public ApiResponse success() {
        HttpStatus httpStatus = HttpStatus.OK;
        String message = ResponseMessages.SUCCESS.message;

        return buildResponseObject(httpStatus, message, null, null);
    }

    public ApiResponse accepted() {
        HttpStatus httpStatus = HttpStatus.OK;
        String message = ResponseMessages.SUCCESS.message;

        Map<String, Integer> test = new HashMap<>();

        return buildResponseObject(httpStatus, message, test, null);
    }

    public ApiResponse accepted(Map data) {
        HttpStatus httpStatus = HttpStatus.ACCEPTED;
        String message = ResponseMessages.SUCCESS.message;

        return buildResponseObject(httpStatus, message, data, null);
    }

    private ApiResponse buildResponseObject(
            HttpStatus httpStatus,
            String message,
            Map data,
            Map errors
    ) {
        return new ApiResponse(httpStatus.getReasonPhrase(), httpStatus.value(), message, data, errors);
    }
}
