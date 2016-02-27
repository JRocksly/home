package jrocksly.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CATEGORY")
public class Category extends CategorizationElem {

	@NotNull
	@Column(name = "CAUSAL_ID")
	private Long parentId;

	public Category() {
		super();
	}

	public Category(Long id, String label, Long parentId) {
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
