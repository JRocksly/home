package jrocksly.project.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import jrocksly.project.dto.CategorizationElemDTO;
import jrocksly.project.model.CategorizationElem;
import jrocksly.project.model.CategorizationElem.Type;
import jrocksly.project.model.CategorizationElemFactory;
import jrocksly.project.model.Category;
import jrocksly.project.model.Causal;
import jrocksly.project.model.SubCategory;

@SuppressWarnings("unchecked")
@Component("hqlCategorizationElemRepo")
@Transactional
public class CategorizationElemRepositoryImpl implements CategorizationElemRepository {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public List<CategorizationElemDTO> getElementsList(Type type) {
		StringBuilder b = new StringBuilder("select new jrocksly.project.dto.CategorizationElemDTO(c) from ")
				.append(type.getEntityName())
				.append(" c order by c.label");
		return em.createQuery(b.toString()).getResultList();
	}

	@Override
	public List<CategorizationElemDTO> getElementChildsList(Long id, Type type) {
		StringBuilder b = new StringBuilder("select new jrocksly.project.dto.CategorizationElemDTO(c) from ")
				.append(type.getChildEntityName()).append(" c, ")
				.append(type.getEntityName()).append(" p ")
				.append("where p.id = :IdParam and p.id = c.parentId");
		return em.createQuery(b.toString())
				.setParameter("IdParam", id)
				.getResultList();
	}

	@Override
	public void saveElement(Type type, String label, Long parentId) {
		CategorizationElemFactory factory = new CategorizationElemFactory();
		em.persist(factory.getCategorizationElem(
				type, 
				label, 
				parentId));
	}

	@Override
	public boolean parentExist(Type type, Type parentType, Long parentId) {
		if(!Type.CASUAL.equals(type)) {
			StringBuilder b = new StringBuilder("from ")
					.append(parentType.getEntityName()).append(" p ")
					.append("where p.id = :IdParam");
			try {
				CategorizationElem elem = (CategorizationElem) em.createQuery(b.toString())
					.setParameter("IdParam", parentId)
					.getSingleResult();
				return null != elem;
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	@Override
	public CategorizationElem getElement(Type type, Long id) {
		if(CategorizationElem.Type.CASUAL.equals(type)) {
			return em.find(Causal.class, id);
		}else if(CategorizationElem.Type.CATEGORY.equals(type)) {
			return em.find(Category.class, id);
		}else if(CategorizationElem.Type.SUBCATEGORY.equals(type)) {
			return em.find(SubCategory.class, id);
		}
		return null;
	}

	@Override
	public void delete(CategorizationElem c) {
		em.remove(c);
	}

	@Override
	public boolean isUsed(Type type, Long id) throws Exception {
		StringBuilder b = new StringBuilder("from Outgoing o where ");
		if(CategorizationElem.Type.CASUAL.equals(type)) {
			b.append("o.causalId = :idParam");
		}else if(CategorizationElem.Type.CATEGORY.equals(type)) {
			b.append("o.categoryId = :idParam");
		}else if(CategorizationElem.Type.SUBCATEGORY.equals(type)) {
			b.append("o.subcategoryId = :idParam");
		}else{
			throw new Exception();
		}
		return !em.createQuery(b.toString())
			.setParameter("idParam", id)
			.getResultList().isEmpty();
	}

	@Override
	public void update(CategorizationElem c, String type, String label) throws Exception {
		StringBuilder b = new StringBuilder("update ");
		if(CategorizationElem.Type.CASUAL.getRestApi().equals(type)) {
			b.append("Causal c ");
		}else if(CategorizationElem.Type.CATEGORY.getRestApi().equals(type)) {
			b.append("Category c ");
		}else if(CategorizationElem.Type.SUBCATEGORY.getRestApi().equals(type)) {
			b.append("SubCategory c ");
		}else{
			throw new Exception();
		}
		b.append("set c.label = :labelParam where c.id = :idParam");
		em.createQuery(b.toString())
			.setParameter("labelParam", label)
			.setParameter("idParam", c.getId())
			.executeUpdate();
	}

}
