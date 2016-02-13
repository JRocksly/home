package jrocksly.project.bean;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jrocksly.project.dao.CategoryRepository;
import jrocksly.project.dto.CategoryDTO;
import jrocksly.project.model.Category;

@Component
public class CategoryBean {
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<CategoryDTO> getCategoriesList(Long causalId) {
		return em.createQuery("select new jrocksly.project.dto.CategoryDTO(ca) "
				+ "from Category ca, Causal c "
				+ "where c.id = causalIdParam and c.id = ca.causalId")
				.setParameter("causalIdParam", causalId)
				.getResultList();
	}

	public CategoryDTO createCategory(Long causalId, String label) throws SQLException {
		Category newCategory = new Category();
		newCategory.setCausalId(causalId);
		newCategory.setLabel(label);
		categoryRepo.save(newCategory);
		return new CategoryDTO(newCategory);
	}

	public boolean exists(Long categoryId) {
		return categoryRepo.exists(categoryId);
	}

}
