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

import jrocksly.project.bean.CategorizationElemBean;
import jrocksly.project.dto.CategorizationElemDTO;

@Controller
@RequestMapping(value = "/element")
public class CategorizationElemService {
	
	@Autowired
	private CategorizationElemBean bean;
	
	@RequestMapping(value={"/{type}"}, method=RequestMethod.GET, produces={"application/json"})
	public ResponseEntity<?> getList(@PathVariable("type") String type) {
		List<CategorizationElemDTO> output;
		try {
			output = bean.getList(type);
		} catch (Exception e) {
			return new ResponseEntity<>("Tipo di elemento inesistente!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
	
	@RequestMapping(value={"/{type}/childs/{id}"}, method=RequestMethod.GET, produces={"application/json"})
	public ResponseEntity<?> getChildList(@PathVariable("type") String type, 
			@PathVariable("id") String id) {
		List<CategorizationElemDTO> output;
		try {
			output = bean.getChildsList(type, id);
		} catch (Exception e) {
			return new ResponseEntity<>("Tipo di elemento inesistente!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
	
	@RequestMapping(value={"/{type}"}, method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public ResponseEntity<?> create(@PathVariable("type") String type, 
			@RequestBody CategorizationElemDTO elem) {
		try {
			if(elem == null || elem.getLabel() == null || elem.getLabel().length() < 4 || !bean.parentExists(type, elem.getParentId())) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			bean.create(type, elem);
		} catch (SQLException e) {
			return new ResponseEntity<>("Nome gia' esistente!", HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value={"/{type}/elem/{id}"}, method=RequestMethod.PUT, consumes={"application/json"}, produces={"application/json"})
	public ResponseEntity<?> edit(@PathVariable("type") String type, 
			@PathVariable("id") String id, 
			@RequestBody String label) {
		if(label == null || label == null || label.length() < 4 || id == null || id.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			bean.update(type, id, label);
		} catch (Exception e) {
			return new ResponseEntity<>("Elemento non trovato!", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value={"/{type}/element/{id}"}, method=RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("type") String type, 
			@PathVariable("id") String id) {
		if(id == null || id.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			if(!bean.isUsed(type, id)) {
				bean.delete(type, id);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}else{
				return new ResponseEntity<>("Impossibile eliminare! Etichetta in uso!", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Elemento non trovato!", HttpStatus.NOT_FOUND);
		}
	}
	
}
