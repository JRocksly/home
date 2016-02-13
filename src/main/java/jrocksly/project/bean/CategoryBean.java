package jrocksly.project.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jrocksly.project.dao.CategoryRepository;
import jrocksly.project.dto.CategoryDTO;
import jrocksly.project.model.Category;

@Component
public class CategoryBean {
	
	@Autowired
	private CategoryRepository categoryRepo;

	public List<CategoryDTO> getCategoriesList(Long causalId) {
		List<CategoryDTO> output = new ArrayList<CategoryDTO>();
		List<Category> categories = new ArrayList<Category>();
		if(causalId != null) {
			categories = categoryRepo.findByCausalId(causalId);
		}else{
			categories = (List<Category>) categoryRepo.findAll();
		}
		for(Category c : categories) {
			output.add(new CategoryDTO(c));
		}
		return output;
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
