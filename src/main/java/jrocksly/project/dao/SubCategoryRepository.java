package jrocksly.project.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jrocksly.project.model.SubCategory;

@Transactional
public interface SubCategoryRepository extends CrudRepository<SubCategory, Long> {
	
	List<SubCategory> findByCategoryId(Long categoryId);

	@Query("select sc from SubCategory sc, Category ca, Causal c where c.id = ca.causal_id and ca.id = sc.categoryId and c.id = :causalId")
	List<SubCategory> findByCausalId(Long causalId);
	
}
