package jrocksly.project.bean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jrocksly.project.dao.OutgoingRepository;
import jrocksly.project.dto.OutgoingDTO;
import jrocksly.project.model.Outgoing;

@Component
public class OutgoingBean {

	@Autowired
	private OutgoingRepository outgoingRepo;
	
	@Autowired
	private CausalBean causalBean;
	
	public List<OutgoingDTO> getOutgoingList() {
		// TODO Auto-generated method stub
		return null;
	}

	public OutgoingDTO createOutgoing(OutgoingDTO outgoing) {
		Outgoing newOutgoing = new Outgoing();
		//TODO aggia' fa chistu
		return null;
	}

}
