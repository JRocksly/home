package jrocksly.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SUB_CATEGORY")
public class SubCategory extends CategorizationElem {
	
	@NotNull
	@Column(name = "CATEGORY_ID")
	private Long parentId;
	
	public SubCategory() {
		super();
	}

	public SubCategory(Long id, String label, Long parentId) {
		super(id, label);
		this.parentId = parentId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
}
