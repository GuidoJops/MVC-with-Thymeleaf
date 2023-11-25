package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.io.InputStream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.model.domain.Country;
import project.model.services.CountryService;

@SpringBootApplication
public class ThymeleafMvcApplication implements CommandLineRunner {

	@Autowired
	private CountryService countryService;

	public static void main(String[] args) {
		SpringApplication.run(ThymeleafMvcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// Load countries into DB from Json file
		System.out.println("---COUNTRIES LOADER STARTED---\n");

		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<Country>> typeReference = new TypeReference<List<Country>>() {};
		InputStream inputStream = TypeReference.class.getResourceAsStream("/json/countries.json");
		
		//Confirms whether the countries have been loaded before; if not, proceeds with the loading.
		if (countryService.listCountries().isEmpty()) {
			System.out.println("No Country data was found in the system.");
			System.out.println("Recovering country data.... ");
			List<Country> countries = mapper.readValue(inputStream, typeReference);
			
			//Sort the Countries
			List<Country> sortedCountries = countries.stream()
					.sorted(Comparator.comparing(Country::getName))
					.collect(Collectors.toList());

			//Insert List of Countries to the DB
			countryService.addMultipleCountries(sortedCountries);
			System.out.println("Done!");


		} else {
			System.out.println("We are GOOD! Countries have been previously initialized.");

		}
		System.out.println("\n---END OF COUNTRIES LOADER---");

	};
	

}
