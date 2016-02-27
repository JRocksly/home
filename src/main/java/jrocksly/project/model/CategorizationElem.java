package jrocksly.project.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class CategorizationElem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	@NotNull
	protected String label;
	
	public CategorizationElem() {
		super();
	}

	public CategorizationElem(Long id, String label) {
		super();
		this.id = id;
		this.label = label;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public enum Type {
	    CASUAL("Causal", "causal", "Category"), 
	    CATEGORY("Category", "category", "SubCategory"),
	    SUBCATEGORY("SubCategory", "subcategory", null);
		
		private String entityName;
		private String restApi;
		private String childEntityName;
		
		Type(String name, String rest, String child) {
			this.entityName = name;
			this.restApi = rest;
			this.childEntityName = child;
		}

		public static Type findByName(String name){
		    for(Type t : values()){
		        if( t.getEntityName().equals(name)){
		            return t;
		        }
		    }
		    return null;
		}
		
		public static Type findByApi(String api){
		    for(Type t : values()){
		        if( t.getRestApi().equals(api)){
		            return t;
		        }
		    }
		    return null;
		}
		
		public static Type findParentEntity(String entity){
			for(Type t : values()){
		        if( t.getChildEntityName().equals(entity)){
		            return t;
		        }
		    }
		    return null;
		}
		
		public String getEntityName() {
			return entityName;
		}
		
		public String getRestApi() {
			return restApi;
		}
		
		public String getChildEntityName() {
			return childEntityName;
		}
		
	}
	
}
