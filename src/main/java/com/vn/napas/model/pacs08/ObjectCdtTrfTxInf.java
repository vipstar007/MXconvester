package com.vn.napas.model.pacs08;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.Data;

@Data
public class ObjectCdtTrfTxInf {
	@JsonProperty("PmtId")
    private ObjectPmtId objectPmtId;
	
	@JsonProperty("PmtTpInf")
    private ObjectPmtTpInf objectPmtTpInf;
	
	@JsonProperty("IntrBkSttlmAmt")
    private ObjectIntrBkSttlmAmt objectIntrBkSttlmAmt;
	
	@JsonProperty("ChrgBr")
    private String chrgBr;
	
	@JsonProperty("InstgAgt")
    private ObjectInstgAgt objectInstgAgt;
	
	@JsonProperty("InstdAgt")
    private ObjectInstdAgt objectInstdAgt;
	
	@JsonProperty("Dbtr")
    private ObjectDbtr objectDbtr;
	
	@JsonProperty("DbtrAcct")
    private ObjectDbtrAcct objectDbtrAcct;
	
	@JsonProperty("DbtrAgt")
    private ObjectDbtrAgt objectDbtrAgt;
	
	@JsonProperty("CdtrAgt")
    private ObjectCdtrAgt objectCdtrAgt;
	
	@JsonProperty("Cdtr")
    private ObjectCdtr objectCdtr;
	
	@JsonProperty("CdtrAcct")
    private ObjectCdtrAcct objectCdtrAcct;
	
	@JsonProperty("InstrForNxtAgt")
	@JacksonXmlElementWrapper(useWrapping = false)
    private ObjectInstrForNxtAgt [] objectInstrForNxtAgt;
}
