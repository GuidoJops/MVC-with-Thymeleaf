package project.model.services;

import java.util.List;

import project.model.domain.Country;


//Countries are loaded automatically when program starts.

public interface CountryService {

	List<Country> listCountries();

	void addMultipleCountries (List<Country> countries);

	void addCountry(Country country);

	void deleteCountry(Long id);

}
