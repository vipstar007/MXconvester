package com.vn.napas.model.response;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateXMConverterResponse {
    private String type;
    private String message;
    private Integer code;
    private Boolean duplicate;
    private String messageIdentifier;
}
