package com.vn.napas.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiXMConverterResponse {
    private String type;
    private String message;
    private Boolean duplicate;
}
