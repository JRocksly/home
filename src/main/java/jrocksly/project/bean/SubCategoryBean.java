package jrocksly.project.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jrocksly.project.dao.CategoryRepository;
import jrocksly.project.dao.SubCategoryRepository;
import jrocksly.project.dto.SubCategoryDTO;
import jrocksly.project.model.SubCategory;

@Component
public class SubCategoryBean {
	
	@Autowired
	private SubCategoryRepository subCategoryRepo;

	public List<SubCategoryDTO> getSubCategoryList() {
		List<SubCategoryDTO> output = new ArrayList<SubCategoryDTO>();
		List<SubCategory> subCategories = (List<SubCategory>) subCategoryRepo.findAll();
		for(SubCategory c : subCategories) {
			output.add(new SubCategoryDTO(c));
		}
		return output;
	}

	public SubCategoryDTO createSubCategory(Long categoryId, String label) {
		SubCategory newSubCategory = new SubCategory();
		newSubCategory.setCategoryId(categoryId);
		newSubCategory.setLabel(label);
		subCategoryRepo.save(newSubCategory);
		return new SubCategoryDTO(newSubCategory);
	}

	public List<SubCategoryDTO> getSubCategoryByCategoryList(Long categoryId) {
		List<SubCategoryDTO> output = new ArrayList<SubCategoryDTO>();
		List<SubCategory> subCategories = subCategoryRepo.findByCategoryId(categoryId);
		for(SubCategory c : subCategories) {
			output.add(new SubCategoryDTO(c));
		}
		return output;
	}

	public List<SubCategoryDTO> getSubCategoryByCausalList(Long causalId) {
		List<SubCategoryDTO> output = new ArrayList<SubCategoryDTO>();
		List<SubCategory> subCategories = subCategoryRepo.findByCausalId(causalId);
		for(SubCategory c : subCategories) {
			output.add(new SubCategoryDTO(c));
		}
		return output;
	}

}
