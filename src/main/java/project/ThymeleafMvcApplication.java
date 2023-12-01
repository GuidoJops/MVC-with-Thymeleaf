package project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.io.InputStream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.model.domain.Country;
import project.model.services.CountryService;

@Slf4j
@SpringBootApplication
public class ThymeleafMvcApplication implements CommandLineRunner {

	private static final String COUNTRIES_PATH = "/json/countries.json";

	@Autowired
	private CountryService countryService;

	public static void main(String[] args) {
		SpringApplication.run(ThymeleafMvcApplication.class, args);
	}

	@Override
	public void run(String... args) {

		log.info("---COUNTRIES LOADER STARTED---");

		if (countryService.listCountries().isEmpty()) {
			try {
				loadCountriesFromJson();
			} catch (Exception ex) {
				log.error("Loading countries from JSON... ERROR!: " + ex.getMessage());
			}
		} else {
			log.info("We are GOOD! Countries have been previously initialized.");
		}

		log.info("---END OF COUNTRIES LOADER---");
	}

	private void loadCountriesFromJson() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<Country>> typeReference = new TypeReference<List<Country>>() {};
		InputStream inputStream = TypeReference.class.getResourceAsStream(COUNTRIES_PATH);

		List<Country> countries = mapper.readValue(inputStream, typeReference);
		log.info("Loading countries from JSON... DONE!");
		saveCountriesIntoDb(countries);
		log.info("Saving countries into DB... DONE!");
	}

	private void saveCountriesIntoDb(List<Country> countries) {
		List<Country> sortedCountries = sortCountriesByName(countries);
		countryService.addMultipleCountries(sortedCountries);
	}

	private List<Country> sortCountriesByName(List<Country> countries) {
		return countries.stream()
				.sorted(Comparator.comparing(Country::getName))
				.toList();
	}
}
