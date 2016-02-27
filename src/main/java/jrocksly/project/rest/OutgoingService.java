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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jrocksly.project.bean.CategorizationElemBean;
import jrocksly.project.bean.OutgoingBean;
import jrocksly.project.dto.OutgoingDTO;
import jrocksly.project.model.CategorizationElem;

@Controller
@RequestMapping(value = "/outgoing")
public class OutgoingService {

	@Autowired
	private OutgoingBean outgoingBean;
	
	@Autowired
	private CategorizationElemBean bean;
	
	@RequestMapping(method=RequestMethod.GET, produces={"application/json"})
	public ResponseEntity<?> getList(@RequestParam("causal") String causal, @RequestParam("category") String category, 
			@RequestParam("subcategory") String subcategory, @RequestParam("description") String description, 
			@RequestParam("startDate") String sDate, @RequestParam("endDate") String eDate, @RequestParam("startExpense") String sExpense, 
			@RequestParam("endExpense") String eExpense) {
		if(description != null && description.length() < 3) {
			return new ResponseEntity<>("Inserire almeno 3 caratteri per la descrizione!", HttpStatus.BAD_REQUEST);
		}
		Long causalId = null;
		Long categoryId = null;
		Long subcategoryId = null;
		try {
			if(causal != null) {
				causalId = Long.parseLong(causal);
			}
			if(category != null) {
				categoryId = Long.parseLong(category);
			}
			if(subcategory != null) {
				subcategoryId = Long.parseLong(subcategory);
			}
		} catch (NumberFormatException e) {
			return new ResponseEntity<>("Categorizzazione non valida!", HttpStatus.BAD_REQUEST);
		}
		Date startDate = null;
		Date endDate = null;
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALIAN);
		try {
			if(sDate != null) {
				startDate = format.parse(sDate);
			}
			if(eDate != null) {
				endDate = format.parse(eDate);
			}
		} catch (ParseException e) {
			return new ResponseEntity<>("Data di inizio o fine non valida!", HttpStatus.BAD_REQUEST);
		}
		BigDecimal startExpense = null;
		BigDecimal endExpense = null;
		try {
			if(sExpense != null) {
				startExpense = new BigDecimal(sExpense).setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			if(eExpense != null) {
				endExpense = new BigDecimal(eExpense).setScale(2, BigDecimal.ROUND_HALF_UP);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Importo non valido!", HttpStatus.BAD_REQUEST);
		}
		List<OutgoingDTO> output = outgoingBean.getList(causalId, categoryId, subcategoryId, description, startDate,
			endDate, startExpense, endExpense);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public ResponseEntity<?> create(@RequestBody OutgoingDTO outgoing) {
		if(outgoing == null) {
			return new ResponseEntity<>("Errore!", HttpStatus.BAD_REQUEST);
		}else if(outgoing.getDescription() == null || outgoing.getDescription().length() < 3) {
			return new ResponseEntity<>("Inserire almeno 3 caratteri per la descrizione!", HttpStatus.BAD_REQUEST);
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
		try {
			if(!bean.exists(CategorizationElem.Type.CASUAL.getRestApi(), outgoing.getCausalId())) {
				return new ResponseEntity<>("Causale non valida!", HttpStatus.BAD_REQUEST);
			}
			if(outgoing.getCategoryId() != null && !outgoing.getCategoryId().isEmpty()) {
				categoryId = Long.parseLong(outgoing.getCategoryId());
				if(!bean.exists(CategorizationElem.Type.CATEGORY.getRestApi(), outgoing.getCategoryId())) {
					return new ResponseEntity<>("Categoria non valida!", HttpStatus.BAD_REQUEST);
				}
			}
			if(outgoing.getSubcategoryId() != null && !outgoing.getSubcategoryId().isEmpty()) {
				subCategoryId = Long.parseLong(outgoing.getSubcategoryId());
				if(!bean.exists(CategorizationElem.Type.SUBCATEGORY.getRestApi(), outgoing.getSubcategoryId())) {
					return new ResponseEntity<>("Sotto-categoria non valida!", HttpStatus.BAD_REQUEST);
				}
			}
		} catch (NumberFormatException e1) {
			return new ResponseEntity<>("Identificativo non valido!", HttpStatus.BAD_REQUEST);
		} catch (Exception e1) {
			return new ResponseEntity<>("Elemento non trovato!", HttpStatus.NOT_FOUND);
		}
		Date date = null;
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALIAN);
		try {
			date = format.parse(outgoing.getDate());
		} catch (ParseException e) {
			return new ResponseEntity<>("Data non valida!", HttpStatus.BAD_REQUEST);
		}
		BigDecimal expense = new BigDecimal(outgoing.getExpense()).setScale(2, BigDecimal.ROUND_HALF_UP);
		outgoingBean.create(causalId, categoryId, subCategoryId, outgoing.getDescription(), date, expense);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value={"/{id}"}, method=RequestMethod.PUT, consumes={"application/json"}, produces={"application/json"})
	public ResponseEntity<?> edit(@PathVariable("id") String id, 
			OutgoingDTO outgoing) {
		//TODO Completare
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value={"/{id}"}, method=RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		if(id == null || id.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Long outgoingId = null;
		try {
			if(id != null) {
				outgoingId = Long.parseLong(id);
			}
		} catch (NumberFormatException e) {
			return new ResponseEntity<>("Categorizzazione non valida!", HttpStatus.BAD_REQUEST);
		}
		try {
			outgoingBean.delete(outgoingId);
		} catch (Exception e) {
			return new ResponseEntity<>("Elemento non trovato!", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
