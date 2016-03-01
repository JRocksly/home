package jrocksly.project.model;

import jrocksly.project.model.CategorizationElem.Type;

public class CategorizationElemFactory {

	public CategorizationElem getCategorizationElem(Type type, String label, Long parentId) {

		if (type == null) {
			return null;
		} 
		if (type.equals(CategorizationElem.Type.CASUAL)) {
			Causal c = new Causal();
			c.setLabel(label);
			return c;
		}
		if (type.equals(CategorizationElem.Type.CATEGORY)) {
			Category c = new Category();
			c.setLabel(label);
			c.setParentId(parentId);
			return c;
		}
		if (type.equals(CategorizationElem.Type.SUBCATEGORY)) {
			SubCategory c = new SubCategory();
			c.setLabel(label);
			c.setParentId(parentId);
			return c;
		}
		return null;
		
	}

}
