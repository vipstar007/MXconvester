package com.vn.napas.model.pacs08;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ObjectDocumentPacs08XML {
	@JsonProperty("FIToFICstmrCdtTrf")
    private ObjectFIToFICstmrCdtTrfPacs08XML objectFIToFICstmrCdtTrfPacs08;
}
