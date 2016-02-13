package jrocksly.project.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import jrocksly.project.model.Causal;

@Transactional
public interface CausalRepository extends CrudRepository<Causal, Long> {
	
	Causal findByLabel(String label);
	
}
