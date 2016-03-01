package jrocksly.project.repo;

import java.util.List;

import jrocksly.project.dto.CategorizationElemDTO;
import jrocksly.project.model.CategorizationElem;
import jrocksly.project.model.CategorizationElem.Type;

public interface CategorizationElemRepository {
	
	CategorizationElem getElement(Type type, Long id);
	
	List<CategorizationElemDTO> getElementsList(Type type);
	
	List<CategorizationElemDTO> getElementChildsList(Long id, Type type);
	
	void saveElement(Type type, String label, Long parentId);
	
	boolean parentExist(Type type, Type parentType, Long parentId);

	void delete(CategorizationElem c);
	
	boolean isUsed(Type type, Long id) throws Exception;

	void update(CategorizationElem c, String type, String label) throws Exception;

}
