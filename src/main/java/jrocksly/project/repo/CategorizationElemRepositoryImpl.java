package jrocksly.project.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import jrocksly.project.dto.CategorizationElemDTO;
import jrocksly.project.model.CategorizationElem;
import jrocksly.project.model.CategorizationElemFactory;
import jrocksly.project.model.Category;
import jrocksly.project.model.Causal;
import jrocksly.project.model.SubCategory;
import jrocksly.project.model.CategorizationElem.Type;

@SuppressWarnings("unchecked")
@Component("hqlCategorizationElemRepo")
public class CategorizationElemRepositoryImpl implements CategorizationElemRepository {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public List<CategorizationElemDTO> getElementsList(Type type) {
		StringBuilder b = new StringBuilder("select new jrocksly.project.dto.CategorizationElemDTO(c) from ")
				.append(type.getEntityName())
				.append(" c");
		return em.createQuery(b.toString()).getResultList();
	}

	@Override
	public List<CategorizationElemDTO> getElementChildsList(Long id, Type type) {
		StringBuilder b = new StringBuilder("select new jrocksly.project.dto.CategorizationElemDTO(c) from ")
				.append(type.getChildEntityName()).append(" c, ")
				.append(type.getEntityName()).append("p ")
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
					.setParameter("parentIdParam", parentId)
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
		return em.createQuery(b.toString())
			.setParameter("idParam", id)
			.getResultList().isEmpty();
	}

}