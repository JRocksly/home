package jrocksly.project.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "OUTGOING")
public class Outgoing {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private Long idCausal;

	private Long idCategory;

	private Long idSubcategory;

	private String description;

	@Temporal(TemporalType.DATE)
	private Date date;

	private BigDecimal expence;

	public Outgoing() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCausal() {
		return idCausal;
	}

	public void setIdCausal(Long idCausal) {
		this.idCausal = idCausal;
	}

	public Long getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(Long idCategory) {
		this.idCategory = idCategory;
	}

	public Long getIdSubcategory() {
		return idSubcategory;
	}

	public void setIdSubcategory(Long idSubcategory) {
		this.idSubcategory = idSubcategory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getExpence() {
		return expence;
	}

	public void setExpence(BigDecimal expence) {
		this.expence = expence;
	}

}
