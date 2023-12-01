package project.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.model.domain.Country;
import project.model.repository.CountryRepository;

//Countries are loaded automatically when program starts.

@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryRepository countryRepository;
	
	@Override
	public List<Country> listCountries() {
		return countryRepository.findAll();
	}

	@Override
	public void addCountry(Country country) {
		countryRepository.save(country);
	}

	@Override
	public void deleteCountry(Long id) {
		countryRepository.deleteById(id);
	}

	@Override
	public void addMultipleCountries(List<Country> countries) {
		countryRepository.saveAll(countries);
	}

}
