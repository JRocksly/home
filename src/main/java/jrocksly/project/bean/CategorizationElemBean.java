package jrocksly.project.bean;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import jrocksly.project.dto.CategorizationElemDTO;
import jrocksly.project.model.CategorizationElem;
import jrocksly.project.model.CategorizationElem.Type;
import jrocksly.project.repo.CategorizationElemRepository;

@Component
public class CategorizationElemBean {
	
	@Resource(name="hqlCategorizationElemRepo")
	CategorizationElemRepository categorizationElemRepo;
	
	public List<CategorizationElemDTO> getList(String type) throws Exception {
		Type entityType = Type.findByApi(type);
		if(entityType == null) {
			throw new Exception();
		}
		List<CategorizationElemDTO> output = categorizationElemRepo.getElementsList(entityType);
		return output;
	}

	public List<CategorizationElemDTO> getChildsList(String type, String id) throws Exception {
		Type entityType = Type.findByApi(type);
		if(entityType == null) {
			throw new Exception();
		}
		Long elementId = Long.parseLong(id);
		List<CategorizationElemDTO> output = categorizationElemRepo.getElementChildsList(elementId, entityType);
		return output;
		
	}
	
	public void create(String type, CategorizationElemDTO elem) throws Exception {
		Type entityType = Type.findByApi(type);
		if(entityType == null) {
			throw new Exception();
		}
		Long parentId = null;
		if(elem.getParentId() != null && !elem.getParentId().isEmpty()) {
			parentId = Long.parseLong(elem.getParentId());
		}
		categorizationElemRepo.saveElement(entityType, elem.getLabel(), parentId);
	}

	public boolean parentExists(String type, String parentId) throws Exception {
		Type entityType = Type.findByApi(type);
		if(entityType.equals(Type.CASUAL)) {
			return true;
		}
		Type parentType = Type.findParentEntity(entityType.getEntityName());
		if(entityType == null || parentType == null) {
			throw new Exception();
		}
		Long parent = null;
		if(parentId != null && !parentId.isEmpty()) {
			parent = Long.parseLong(parentId);
		}
		return categorizationElemRepo.parentExist(entityType, parentType, parent);
	}

	public void update(String type, String id, String label) throws Exception {
		CategorizationElem c = getElement(type, id);
		if(c != null) {
			categorizationElemRepo.update(c, type, label);
		}else{
			throw new Exception();
		}
	}

	public void delete(String type, String id) throws Exception {
		CategorizationElem c = getElement(type, id);
		if(c != null) {
			categorizationElemRepo.delete(c);
		}else{
			throw new Exception();
		}
	}

	public boolean isUsed(String type, String id) throws Exception {
		Type entityType = Type.findByApi(type);
		if(entityType == null) {
			throw new Exception();
		}
		Long elementId = Long.parseLong(id);
		return categorizationElemRepo.isUsed(entityType, elementId);
		
	}

	public boolean exists(String type, String id) throws Exception {
		CategorizationElem c = getElement(type, id);
		return c != null;
	}
	
	private CategorizationElem getElement(String type, String id) throws Exception {
		Type entityType = Type.findByApi(type);
		if(entityType == null) {
			throw new Exception();
		}
		Long elementId = null;
		if(id != null && !id.isEmpty()) {
			elementId = Long.parseLong(id);
		}
		CategorizationElem c = categorizationElemRepo.getElement(entityType, elementId);
		return c;
	}
	
}
