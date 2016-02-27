package jrocksly.project.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CAUSAL")
public class Causal extends CategorizationElem {

	public Causal() {
		super();
	}

	public Causal(Long id, String label) {
		super(id, label);
	}
	
}
