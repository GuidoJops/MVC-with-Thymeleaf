package project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import project.model.domain.Country;
import project.model.services.CountryService;


//Countries are loaded automatically when program starts.

@Controller
@RequestMapping ("/country")
public class CountryController {

	@Autowired
	private CountryService countryService;
	
	
	@GetMapping("/getAll")
	public ResponseEntity<List<Country>> getCountries(){
		List<Country> country = countryService.listCountries();
		if (country.isEmpty()) {
			return new ResponseEntity<>(country, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(country,HttpStatus.OK);
	}
	
	@PostMapping("/saveAll") 
	public ResponseEntity<List<Country>> addCountries(@RequestBody List<Country> countries) {
		countryService.addMultipleCountries(countries);
		return new ResponseEntity<>(countries, HttpStatus.CREATED);
	}
	
	
	@PostMapping("/add") 
	public ResponseEntity<Country> addCountry(@RequestBody Country country) {
		countryService.addCountry(country);
		return new ResponseEntity<>(country, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCountry(@PathVariable("id") Long id) {
		countryService.deleteCountry(id);
		return new ResponseEntity<>("Country Deleted", HttpStatus.OK);
	}
	
}
