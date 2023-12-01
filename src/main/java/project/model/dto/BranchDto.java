package project.model.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import project.model.domain.Country;

@Getter
@Setter
public class BranchDto {

	private static final Set<String> EU_COUNTRIES = Set.of("Germany", "Austria", "Belgium", "Bulgaria", "Cyprus",
			"Croatia", "Denmark", "Spain", "Slovakia", "Slovenia",
			"Estonia", "Finland", "France", "Greece", "Hungary",
			"Ireland", "Italy", "Latvia", "Lithuania", "Luxembourg",
			"Malta", "Netherlands", "Poland", "Portugal",
			"Czech Republic", "Romania", "Sweden");
	
	private Long id;
	
	@NotBlank(message = "Branch Name is required")
	private String branchName;
	
	private Country country;
	
	private String branchType;
	
	public String definesBranchType() {
		return isEuCountry() ? "EU" : "Outside EU";
	}

	public boolean isEuCountry() {
		return EU_COUNTRIES.contains(country.getName());
	}

}
