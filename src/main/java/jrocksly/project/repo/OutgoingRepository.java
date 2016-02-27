package jrocksly.project.repo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jrocksly.project.dto.OutgoingDTO;
import jrocksly.project.model.Outgoing;

public interface OutgoingRepository {
	
	Outgoing getOutgoing(Long id);
	
	List<OutgoingDTO> getOutgoingList(Long causalId, Long categoryId, Long subcategoryId, String description,
			Date startDate, Date endDate, BigDecimal startExpense, BigDecimal endExpense);
	
	void saveOutgoing(Outgoing o);
	
	void deleteOutgoing(Outgoing o);

}
