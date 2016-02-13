package jrocksly.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jrocksly.project.model.SubCategory;

@JsonInclude(Include.NON_NULL)
public class SubCategoryDTO {
	
	private String id;
	private String causalId;
	private String categoryId;
	private String label;
	
	public SubCategoryDTO() {}
	
	public SubCategoryDTO(SubCategory c) {
		this.id = c.getId().toString();
		this.categoryId = c.getCategoryId().toString();
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

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
