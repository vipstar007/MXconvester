package com.vn.napas.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AchSendPacs008Response {
    @JsonProperty("Header")
    private JsonNode Header;
    @JsonProperty("Payload")
    private JsonNode Payload;
}
