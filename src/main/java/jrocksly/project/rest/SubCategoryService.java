package jrocksly.project.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jrocksly.project.bean.CategoryBean;
import jrocksly.project.bean.SubCategoryBean;
import jrocksly.project.dto.SubCategoryDTO;

@Controller
@RequestMapping(value = "/subcategory")
public class SubCategoryService {

	@Autowired
	private CategoryBean categoryBean;
	
	@Autowired
	private SubCategoryBean subCategoryBean;
	
	@RequestMapping(method=RequestMethod.GET, produces={"application/json"})
	public ResponseEntity<?> getSubCategoryList(@RequestParam(value="causal") String causal, 
			@RequestParam(value="category") String category)  {
		Long causalId = null;
		Long categoryId = null;
		try {
			if(category != null) {
				categoryId = Long.parseLong(category);
				List<SubCategoryDTO> output = subCategoryBean.getSubCategoryByCategoryList(categoryId);
				return new ResponseEntity<>(output, HttpStatus.OK);
			}else if(causal != null) {
				causalId = Long.parseLong(causal);
				List<SubCategoryDTO> output = subCategoryBean.getSubCategoryByCausalList(causalId);
				return new ResponseEntity<>(output, HttpStatus.OK);
			}else{
				List<SubCategoryDTO> output = subCategoryBean.getSubCategoryList();
				return new ResponseEntity<>(output, HttpStatus.OK);
			}
		}catch (NumberFormatException e) {
			return new ResponseEntity<>("Categoria o causale non valida!", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public ResponseEntity<?> createSubCategory(@RequestBody SubCategoryDTO subCategory) {
		if(subCategory == null) {
			return new ResponseEntity<>("Errore!", HttpStatus.BAD_REQUEST);
		}else if(subCategory.getLabel() == null || subCategory.getLabel().isEmpty()) {
			return new ResponseEntity<>("Inserire un nome valido per la sotto-categoria!", HttpStatus.BAD_REQUEST);
		}else if(subCategory.getCategoryId() == null || subCategory.getCategoryId().isEmpty()) {
			return new ResponseEntity<>("Categoria non valida!", HttpStatus.BAD_REQUEST);
		}else{
			Long categoryId = null;
			try {
				categoryId = Long.parseLong(subCategory.getCategoryId());
				if(!categoryBean.exists(categoryId)) {
					throw new Exception();
				}
			} catch (Exception e) {
				return new ResponseEntity<>("Categoria non valida!", HttpStatus.BAD_REQUEST);
			}
		}
		SubCategoryDTO output = subCategoryBean.createSubCategory(Long.parseLong(subCategory.getCategoryId()), subCategory.getLabel());
		return new ResponseEntity<>(output, HttpStatus.CREATED);
	}
	
	@RequestMapping(value={"/{id}"}, method=RequestMethod.PUT, consumes={"application/json"}, produces={"application/json"})
	public ResponseEntity<?> editSubCategory(@PathVariable("id") String id, @RequestBody SubCategoryDTO causal) {
		return null;
	}
	
	@RequestMapping(value={"/{id}"}, method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteSubCategory(@PathVariable("id") String id) {
		return null;
	}
	
}
