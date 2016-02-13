package jrocksly.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jrocksly.project.model.Causal;

@JsonInclude(Include.NON_NULL)
public class CausalDTO {
	
	private String id;
	private String label;
	
	public CausalDTO() {}

	public CausalDTO(Causal c) {
		this.id = c.getId().toString();
		this.label = c.getLabel();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
