package jrocksly.project.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import jrocksly.project.model.Category;

@Transactional
public interface CategoryRepository extends CrudRepository<Category, Long> {
	
	List<Category> findByCausalId(Long causalId);
	
}
