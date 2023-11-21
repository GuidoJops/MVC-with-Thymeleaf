package project.model.services;

import java.util.List;

import project.model.domain.Pais;


//Los Paises se cargan automaticamente al iniciar el programa

public interface IPaisService {
	
	List<Pais> listaPaises();
	
	void addMultiplePais (List<Pais> paises);
	
	void addPais(Pais pais);
	
	void deletePais(Long id);

}
