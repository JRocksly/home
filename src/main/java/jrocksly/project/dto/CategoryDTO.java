package jrocksly.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jrocksly.project.model.Category;

@JsonInclude(Include.NON_NULL)
public class CategoryDTO {
	
	private String id;
	private String causalId;
	private String label;
	
	public CategoryDTO() {}
	
	public CategoryDTO(Category c) {
		this.id = c.getId().toString();
		this.causalId = c.getCausalId().toString();
		this.label = c.getLabel();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getCausalId() {
		return causalId;
	}

	public void setCausalId(String causalId) {
		this.causalId = causalId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
