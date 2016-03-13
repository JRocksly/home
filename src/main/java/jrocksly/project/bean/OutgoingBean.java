package jrocksly.project.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import jrocksly.project.dto.OutgoingDTO;
import jrocksly.project.model.Outgoing;
import jrocksly.project.repo.OutgoingRepository;

@Component
public class OutgoingBean {
	
	@Resource(name="hqlOutgoingRepo")
	OutgoingRepository outgoingRepo;
	
	public List<OutgoingDTO> getList(Long causalId, Long categoryId, Long subcategoryId, String description,
			Date startDate, Date endDate, BigDecimal startExpense, BigDecimal endExpense) {
		List<OutgoingDTO> output = outgoingRepo.getOutgoingList(causalId, categoryId, subcategoryId, description, 
				startDate, endDate, startExpense, endExpense);
		return output;
	}

	public void create(Long causalId, Long categoryId, Long subCategoryId, String description, Date date, BigDecimal expense) throws Exception {
		if(outgoingRepo.validateCategorizationTree(causalId, categoryId, subCategoryId)) {
			outgoingRepo.saveOutgoing(causalId, categoryId, subCategoryId, description, date, expense);
		}else{
			throw new Exception("Categorizzazione non valida");
		}
	}

	public void delete(Long id) throws Exception {
		Outgoing o = outgoingRepo.getOutgoing(id);
		if(o != null) {
			outgoingRepo.deleteOutgoing(o);
		}else{
			throw new Exception();
		}
		
	}

}
