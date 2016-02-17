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

import jrocksly.project.bean.CausalBean;
import jrocksly.project.dto.CausalDTO;

@Controller
@RequestMapping(value = "/causal")
public class CausalService {

	@Autowired
	private CausalBean causalBean;
	
	@RequestMapping(method=RequestMethod.GET, produces={"application/json"})
	public ResponseEntity<?> getCausalList() {
		List<CausalDTO> output = causalBean.getCausalList();
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public ResponseEntity<?> createCausal(@RequestBody CausalDTO causal) {
		if(causal == null || causal.getLabel() == null || causal.getLabel().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		CausalDTO output;
		try {
			output = causalBean.createCausal(causal.getLabel());
		} catch (SQLException e) {
			return new ResponseEntity<>("Causale gia' presente!", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(output, HttpStatus.CREATED);
	}
	
	@RequestMapping(value={"/{id}"}, method=RequestMethod.PUT, consumes={"application/json"}, produces={"application/json"})
	public ResponseEntity<?> editCausal(@PathVariable("id") String id, @RequestBody CausalDTO causal) {
		return null;
	}
	
	@RequestMapping(value={"/{id}"}, method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteCausal(@PathVariable("id") String id) {
		return null;
	}
	
}
