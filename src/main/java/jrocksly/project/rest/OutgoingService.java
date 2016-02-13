package jrocksly.project.rest;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jrocksly.project.bean.CategoryBean;
import jrocksly.project.bean.CausalBean;
import jrocksly.project.bean.OutgoingBean;
import jrocksly.project.bean.SubCategoryBean;
import jrocksly.project.dto.OutgoingDTO;

@Controller
@RequestMapping(value = "/outgoing")
public class OutgoingService {

	@Autowired
	private OutgoingBean outgoingBean;
	
	@Autowired
	private CausalBean causalBean;
	
	@Autowired
	private CategoryBean categoryBean;
	
	@Autowired
	private SubCategoryBean subcategoryBean;
	
	
	@RequestMapping(method=RequestMethod.GET, produces={"application/json"})
	public ResponseEntity<?> getOutgoingList(@RequestParam("causal") String causal, @RequestParam("category") String category, 
			@RequestParam("subcategory") String subcategory, @RequestParam("description") String description, 
			@RequestParam("startDate") String sDate, @RequestParam("endDate") String eDate, @RequestParam("startExpense") String sExpense, 
			@RequestParam("endExpense") String eExpense) {
		Long causalId = null;
		Long categoryId = null;
		Long subcategoryId = null;
		if(causal != null) {
			causalId = Long.parseLong(causal);
			if(!causalBean.exists(causalId)) {
				return new ResponseEntity<>("Causale non valida!", HttpStatus.BAD_REQUEST);
			}
		}
		if(category != null) {
			categoryId = Long.parseLong(category);
			if(!categoryBean.exists(categoryId)) {
				return new ResponseEntity<>("Categoria non valida!", HttpStatus.BAD_REQUEST);
			}
		}
		if(subcategory != null) {
			subcategoryId = Long.parseLong(subcategory);
			if(!subcategoryBean.exists(subcategoryId)) {
				return new ResponseEntity<>("Sotto-categoria non valida!", HttpStatus.BAD_REQUEST);
			}
		}
		Date startDate = null;
		Date endDate = null;
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALIAN);
		try {
			startDate = format.parse(sDate);
			endDate = format.parse(eDate);
		} catch (ParseException e) {
			return new ResponseEntity<>("Data di inizio o fine non valida!", HttpStatus.BAD_REQUEST);
		}
		BigDecimal startExpense = new BigDecimal(sExpense).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal endExpense = new BigDecimal(eExpense).setScale(2, BigDecimal.ROUND_HALF_UP);
		List<OutgoingDTO> output = outgoingBean.getOutgoingList(causalId, categoryId, subcategoryId, description, startDate,
				endDate, startExpense, endExpense);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public ResponseEntity<?> createOutgoing(@RequestBody OutgoingDTO outgoing) {
		if(outgoing == null) {
			return new ResponseEntity<>("Errore!", HttpStatus.BAD_REQUEST);
		}else if(outgoing.getCausalId() == null || outgoing.getCausalId().isEmpty()) {
			return new ResponseEntity<>("Causale non valida!", HttpStatus.BAD_REQUEST);
		}else if(outgoing.getDate() == null || !outgoing.getDate().isEmpty()) {
			return new ResponseEntity<>("Data non presente o non valida!", HttpStatus.BAD_REQUEST);
		}else if(outgoing.getExpense() == null || !outgoing.getExpense().isEmpty()) {
			return new ResponseEntity<>("Importo non presente o non valido!", HttpStatus.BAD_REQUEST);
		}
		Long causalId = Long.parseLong(outgoing.getCausalId());
		Long categoryId = null;
		Long subCategoryId = null;
		if(!causalBean.exists(causalId)) {
			return new ResponseEntity<>("Causale non valida!", HttpStatus.BAD_REQUEST);
		}
		if(outgoing.getCategoryId() != null && !outgoing.getCategoryId().isEmpty()) {
			categoryId = Long.parseLong(outgoing.getCategoryId());
			if(!categoryBean.exists(categoryId)) {
				return new ResponseEntity<>("Categoria non valida!", HttpStatus.BAD_REQUEST);
			}
		}
		if(outgoing.getSubcategoryId() != null && !outgoing.getSubcategoryId().isEmpty()) {
			subCategoryId = Long.parseLong(outgoing.getSubcategoryId());
			if(!categoryBean.exists(subCategoryId)) {
				return new ResponseEntity<>("Sotto-categoria non valida!", HttpStatus.BAD_REQUEST);
			}
		}
		Date date = null;
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALIAN);
		try {
			date = format.parse(outgoing.getDate());
		} catch (ParseException e) {
			return new ResponseEntity<>("Data non valida!", HttpStatus.BAD_REQUEST);
		}
		BigDecimal expense = new BigDecimal(outgoing.getExpense()).setScale(2, BigDecimal.ROUND_HALF_UP);
		OutgoingDTO output = outgoingBean.createOutgoing(causalId, categoryId, subCategoryId, outgoing.getDescription(), date, expense);
		return new ResponseEntity<>(output, HttpStatus.CREATED);
	}
	
}
