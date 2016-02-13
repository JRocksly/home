package jrocksly.project.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jrocksly.project.dao.OutgoingRepository;
import jrocksly.project.dto.OutgoingDTO;
import jrocksly.project.model.Outgoing;

@Component
public class OutgoingBean {

	@Autowired
	private OutgoingRepository outgoingRepo;
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<OutgoingDTO> getOutgoingList(Long causalId, Long categoryId, Long subcategoryId, String description,
			Date startDate, Date endDate, BigDecimal startExpense, BigDecimal endExpense) {
		boolean causalPresent = false;
		boolean categoryPresent = false;
		boolean subcategoryPresent = false;
		boolean descriptionPresent = false;
		boolean startDatePresent = false;
		boolean endDatePresent = false;
		boolean startExpensePresent = false;
		boolean endExpensePresent = false;
		StringBuilder builder = new StringBuilder("select new jrocksly.project.dto.OutgoingDTO(o, c, ca, cs) "
				+ "from Outgoing o, Causal c, Category ca, SubCategory cs "
				+ "where o.causalId = c.id and o.categoryId = ca.id and o.subcategoryId = cs.id ");
		if(subcategoryId != null) {
			builder.append(" and o.subcategoryId = :subcategoryIdParam ");
			subcategoryPresent = true;
		}else if(categoryId != null) {
			builder.append(" and o.categoryId = :categoryIdParam ");
			categoryPresent = true;
			
		}else if(causalId != null) {
			builder.append(" and o.causalId = :causalIdParam ");
			causalPresent = true;
		}
		if(description != null && description.length() > 3) {
			builder.append(" and o.description like %:categoryIdParam% ");
			categoryPresent = true;
		}
		if(startDate != null) {
			if(endDate != null) {
				builder.append(" and o.date between :startDateParam and :endDateParam ");
				endDatePresent = true;
			}else{
				builder.append(" and o.date > :startDateParam ");
			}
			startDatePresent = true;
		}else if(endDate != null) {
			builder.append(" and o.date < :endDateParam ");
			endDatePresent = true;
		}
		if(startExpense != null) {
			if(endExpense != null) {
				builder.append(" and o.expense between :startExpenseParam and :endExpenseParam ");
				endExpensePresent = true;
			}else{
				builder.append(" and o.expense > :startExpenseParam ");
			}
			startExpensePresent = true;
		}else if(endExpense != null) {
			builder.append(" and o.expense < :endExpenseParam ");
			endExpensePresent = true;
		}
		builder.append(" order by o.date desc");
		Query q = em.createQuery(builder.toString());
		if(subcategoryPresent) {
			q.setParameter("subcategoryIdParam", subcategoryId);
		}else if(categoryPresent) {
			q.setParameter("categoryIdParam", categoryId);
		}else if(causalPresent) {
			q.setParameter("causalIdParam", causalId);
		}
		if(descriptionPresent) {
			q.setParameter("descriptionParam", description);
		}
		if(startDatePresent) {
			q.setParameter("startDateParam", startDate);
		}
		if(endDatePresent) {
			q.setParameter("endDateParam", endDate);
		}
		if(startExpensePresent) {
			q.setParameter("startExpenseParam", startExpense);
		}
		if(endExpensePresent) {
			q.setParameter("endExpenseParam", endExpense);
		}
		return q.getResultList();
	}

	public OutgoingDTO createOutgoing(Long causalId, Long categoryId, Long subCategoryId, String description, 
			Date date, BigDecimal expense) {
		Outgoing outgoing = new Outgoing();
		outgoing.setCausalId(causalId);
		outgoing.setCategoryId(categoryId);
		outgoing.setSubcategoryId(subCategoryId);
		outgoing.setDate(date);
		outgoing.setExpense(expense);
		outgoingRepo.save(outgoing);
		return new OutgoingDTO(outgoing);
	}

}
