package jrocksly.project.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jrocksly.project.dao.CausalRepository;
import jrocksly.project.dto.CausalDTO;
import jrocksly.project.model.Causal;

@Component
public class CausalBean {

	@Autowired
	private CausalRepository causalRepo;

	public List<CausalDTO> getCausalList() {
		List<CausalDTO> output = new ArrayList<CausalDTO>();
		List<Causal> causalList = (List<Causal>) causalRepo.findAll();
		for(Causal c : causalList) {
			output.add(new CausalDTO(c));
		}
		return output;
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
