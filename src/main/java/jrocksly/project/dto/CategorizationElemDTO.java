package jrocksly.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jrocksly.project.model.Category;
import jrocksly.project.model.Causal;
import jrocksly.project.model.SubCategory;

@JsonInclude(Include.NON_NULL)
public class CategorizationElemDTO {
	
	private String id;
	private String parentId;
	private String label;
	
	public CategorizationElemDTO() {}
	
	public CategorizationElemDTO(Causal c) {
		this.id = c.getId().toString();
		this.label = c.getLabel();
	}
	
	public CategorizationElemDTO(Category c) {
		this.id = c.getId().toString();
		this.parentId = c.getParentId().toString();
		this.label = c.getLabel();
	}

	public CategorizationElemDTO(SubCategory c) {
		this.id = c.getId().toString();
		this.parentId = c.getParentId().toString();
		this.label = c.getLabel();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
