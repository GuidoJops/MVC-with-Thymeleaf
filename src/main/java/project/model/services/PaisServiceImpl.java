package project.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.model.domain.Pais;
import project.model.repository.IPaisRepository;

//Los Paises se cargan automaticamente al iniciar el programa

@Service
public class PaisServiceImpl implements IPaisService {

	@Autowired
	private IPaisRepository paisRepo;
	
	@Override
	public List<Pais> listaPaises() {
		return paisRepo.findAll();
	}

	@Override
	public void addPais(Pais pais) {
		System.out.println("Pais Guardado");
		paisRepo.save(pais);
		
	}

	@Override
	public void deletePais(Long id) {
		paisRepo.deleteById(id);
	}

	@Override
	public void addMultiplePais(List<Pais> paises) {
		paisRepo.saveAll(paises);
	}

}
