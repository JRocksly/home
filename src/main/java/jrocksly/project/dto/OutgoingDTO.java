package jrocksly.project.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jrocksly.project.model.Category;
import jrocksly.project.model.Causal;
import jrocksly.project.model.Outgoing;
import jrocksly.project.model.SubCategory;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties("format")
public class OutgoingDTO {
	
	DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
	
	public String id;
	public String causalId;
	public String causal;
	public String categoryId;
	public String category;
	public String subcategoryId;
	public String subcategory;
	public String description;
	public String date;
	public String expense;
	
	public OutgoingDTO() {}

	public OutgoingDTO(Outgoing outgoing) {
		this.id = outgoing.getId().toString();
		this.causalId = outgoing.getCausalId().toString();
		this.categoryId = outgoing.getCategoryId() != null ? outgoing.getCategoryId().toString() : null;
		this.subcategoryId = outgoing.getSubcategoryId() != null ? outgoing.getSubcategoryId().toString() : null;
		this.description = outgoing.getDescription();
		this.date = format.format(outgoing.getDate().toString());
		this.expense = outgoing.getExpense().toString();
	}
	
	public OutgoingDTO(Outgoing o, Causal c, Category ca, SubCategory cs) {
		this.id = o.getId().toString();
		this.causalId = o.getCausalId().toString();
		this.causal = c.getLabel();
		if(o.getCategoryId() != null) {
			this.categoryId = o.getCategoryId().toString();
			this.category = ca.getLabel();
			if(o.getSubcategoryId() != null) {
				this.subcategoryId = o.getCategoryId().toString();
				this.subcategory = cs.getLabel();
			}
		}
		this.description = o.getDescription();
		this.date = format.format(o.getDate().toString());
		this.expense = o.getExpense().toString();
	}

	public String getId() {
		return id;
	}

	public String getCausalId() {
		return causalId;
	}

	public void setCausalId(String causalId) {
		this.causalId = causalId;
	}

	public String getCausal() {
		return causal;
	}

	public void setCausal(String causal) {
		this.causal = causal;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getSubcategoryId() {
		return subcategoryId;
	}

	public void setSubcategoryId(String subcategoryId) {
		this.subcategoryId = subcategoryId;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getExpense() {
		return expense;
	}

	public void setExpense(String expense) {
		this.expense = expense;
	}

	public void setId(String id) {
		this.id = id;
	}

}
