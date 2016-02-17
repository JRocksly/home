package jrocksly.project.rest;

import java.sql.SQLException;
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
import jrocksly.project.bean.CausalBean;
import jrocksly.project.dto.CategoryDTO;

@Controller
@RequestMapping(value = "/category")
public class CategoryService {

	@Autowired
	private CausalBean causalBean;
	
	@Autowired
	private CategoryBean categoryBean;
	
	@RequestMapping(method=RequestMethod.GET, produces={"application/json"})
	public ResponseEntity<?> getCategoryList(@RequestParam(value="causal") String causal)  {
		Long causalId = null;
		if(causal != null) {
			try {
				causalId = Long.parseLong(causal);
			} catch (NumberFormatException e) {
				return new ResponseEntity<>("Causale non valida!", HttpStatus.BAD_REQUEST);
			}
		} 
		List<CategoryDTO> output = categoryBean.getCategoriesList(causalId);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public ResponseEntity<?> createCategory(@RequestBody CategoryDTO category) {
		Long causalId = null;
		if(category == null) {
			return new ResponseEntity<>("Errore!", HttpStatus.BAD_REQUEST);
		}else if(category.getLabel() == null || category.getLabel().isEmpty()) {
			return new ResponseEntity<>("Inserire un nome valido per la categoria!", HttpStatus.BAD_REQUEST);
		}else if(category.getCausalId() == null  || category.getCausalId().isEmpty()) {
			return new ResponseEntity<>("Categoria non valida!", HttpStatus.BAD_REQUEST);
		}else{
			try {
				causalId = Long.parseLong(category.getCausalId());
				if(!causalBean.exists(causalId)) {
					throw new Exception();
				}
			} catch (Exception e) {
				return new ResponseEntity<>("Categoria non valida!", HttpStatus.BAD_REQUEST);
			}
		}
		CategoryDTO output;
		try {
			output = categoryBean.createCategory(causalId, category.getLabel());
		} catch (SQLException e) {
			return new ResponseEntity<>("Categoria gia' presente!", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(output, HttpStatus.CREATED);
	}
	
	@RequestMapping(value={"/{id}"}, method=RequestMethod.PUT, consumes={"application/json"}, produces={"application/json"})
	public ResponseEntity<?> editCategory(@PathVariable("id") String id, @RequestBody CategoryDTO causal) {
		return null;
	}
	
	@RequestMapping(value={"/{id}"}, method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteCategory(@PathVariable("id") String id) {
		return null;
	}
	
}
