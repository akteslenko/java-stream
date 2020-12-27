package com.zvuk.stream;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    private Object data;
    private Object errors;

    public static ApiResponse buildResponseObject(
            HttpStatus httpStatus,
            Object data,
            Object errors
    ) {
        return new ApiResponse(httpStatus.getReasonPhrase(), httpStatus.value(), data, errors);
    }
}
