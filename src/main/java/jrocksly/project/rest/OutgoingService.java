package jrocksly.project.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jrocksly.project.bean.OutgoingBean;
import jrocksly.project.dto.OutgoingDTO;

@Controller
@RequestMapping(value = "/outgoing")
public class OutgoingService {

	@Autowired
	private OutgoingBean outgoingBean;
	
	@RequestMapping(method=RequestMethod.GET, produces={"application/json"})
	public ResponseEntity<List<OutgoingDTO>> getOutgoingList() {
		List<OutgoingDTO> output = outgoingBean.getOutgoingList();
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
		OutgoingDTO output = outgoingBean.createOutgoing(outgoing);
		return new ResponseEntity<>(output, HttpStatus.CREATED);
	}
	
}
