package project.model.services;

import java.util.List;

import project.model.domain.Country;


//Los Paises se cargan automaticamente al iniciar el programa

public interface CountryService {
	
	List<Country> listCountries();
	
	void addMultipleCountries (List<Country> countries);
	
	void addCountry(Country country);
	
	void deleteCountry(Long id);

}
