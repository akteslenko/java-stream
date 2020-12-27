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
@JsonIgnoreProperties(value = {"httpStatus"})
public class ApiResponse {

    private String status;
    private Integer code;
    private String message;
    private Map<?, ?> data;
    private Map<?, ?> errors;

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static final String ERROR = "error";

    public ApiResponse success(Map<?, ?> data, Map<?, ?> errors) {
        HttpStatus httpStatus = HttpStatus.OK;

        return buildResponseObject(httpStatus, SUCCESS, data, errors);
    }

    public ApiResponse success(Map<?, ?> data) {
        HttpStatus httpStatus = HttpStatus.OK;

        return buildResponseObject(httpStatus, SUCCESS, data, null);
    }

    public ApiResponse success() {
        HttpStatus httpStatus = HttpStatus.OK;

        return buildResponseObject(httpStatus, SUCCESS, null, null);
    }

    public ApiResponse accepted() {
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, Integer> test = new HashMap<>();

        return buildResponseObject(httpStatus, SUCCESS, test, null);
    }

    public ApiResponse accepted(Map<?, ?> data) {
        HttpStatus httpStatus = HttpStatus.ACCEPTED;

        return buildResponseObject(httpStatus, SUCCESS, data, null);
    }

    private ApiResponse buildResponseObject(
            HttpStatus httpStatus,
            String message,
            Map<?, ?> data,
            Map<?, ?> errors
    ) {
        return new ApiResponse(httpStatus.getReasonPhrase(), httpStatus.value(), message, data, errors);
    }
}
