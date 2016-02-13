package jrocksly.project.bean;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jrocksly.project.dao.CausalRepository;
import jrocksly.project.dto.CausalDTO;
import jrocksly.project.model.Causal;

@Component
public class CausalBean {

	@Autowired
	private CausalRepository causalRepo;
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<CausalDTO> getCausalList() {
		return em.createQuery("select new jrocksly.project.dto.CausalDTO(c) from Causal c").getResultList();
	}

	public CausalDTO createCausal(String label) throws SQLException {
		Causal newCausal = new Causal();
		newCausal.setLabel(label);
		causalRepo.save(newCausal);
		return new CausalDTO(newCausal);
	}

	public boolean exists(Long causalId) {
		return causalRepo.exists(causalId);
	}

}
