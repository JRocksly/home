package jrocksly.project.bean;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jrocksly.project.dao.SubCategoryRepository;
import jrocksly.project.dto.SubCategoryDTO;
import jrocksly.project.model.SubCategory;

@Component
public class SubCategoryBean {
	
	@Autowired
	private SubCategoryRepository subCategoryRepo;
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<SubCategoryDTO> getSubCategoryList() {
		return em.createQuery("select new jrocksly.project.dto.SubCategoryDTO(sc) from SubCategory sc").getResultList();
	}

	public SubCategoryDTO createSubCategory(Long categoryId, String label) {
		SubCategory newSubCategory = new SubCategory();
		newSubCategory.setCategoryId(categoryId);
		newSubCategory.setLabel(label);
		em.persist(newSubCategory);
		return new SubCategoryDTO(newSubCategory);
	}
	
	@SuppressWarnings("unchecked")
	public List<SubCategoryDTO> getSubCategoryByCategoryList(Long categoryId) {
		return em.createQuery("select new jrocksly.project.dto.SubCategoryDTO(sc) "
				+ "from Category ca, SubCategory sc "
				+ "where ca.id = categoryIdParam and ca.id = cs.categoryId")
				.setParameter("categoryIdParam", categoryId)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<SubCategoryDTO> getSubCategoryByCausalList(Long causalId) {
		return em.createQuery("select new jrocksly.project.dto.SubCategoryDTO(sc) "
				+ "from Category ca, SubCategory sc, Causal c "
				+ "where c.id = causalIdParam and ca.id = cs.categoryId and ca.causalId = c.id")
				.setParameter("causalIdParam", causalId)
				.getResultList();
	}
	
	public boolean exists(Long subcategoryId) {
		return subCategoryRepo.exists(subcategoryId);
	}

}
