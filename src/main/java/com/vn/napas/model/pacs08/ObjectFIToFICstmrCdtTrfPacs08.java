package com.vn.napas.model.pacs08;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.Data;

@Data
public class ObjectFIToFICstmrCdtTrfPacs08 {
	@JsonProperty("GrpHdr")
	private ObjectGrpHdr objectGrpHdr;
	
	@JsonProperty("CdtTrfTxInf")
	@JacksonXmlElementWrapper(useWrapping = false)
	private ObjectCdtTrfTxInf[] objectCdtTrfTxInf;
}
