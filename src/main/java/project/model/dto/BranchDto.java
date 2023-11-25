package project.model.dto;

import java.util.Arrays;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import project.model.domain.Country;

@Getter
@Setter
public class BranchDto {
	
	private Long id;
	
	@NotBlank(message = "Branch Name is required")
	private String branchName;
	
	private Country country;
	
	private String branchType;
	
	private List<String> euCountries = Arrays.asList("Germany","Austria","Belgium","Bulgaria","Cyprus",
			"Croatia","Denmark","Spain","Slovakia","Slovenia",
			"Estonia","Finland","France","Greece","Hungary",
			"Ireland","Italy","Latvia","Lithuania","Luxembourg",
			"Malta","Netherlands","Poland","Portugal",
			"Czech Republic","Romania","Sweden");

	public String definesBranchType(String countryName) {
		String type="Outside EU";
		
		if(euCountrySearch(countryName)) {
			type= "EU";
		}
		return type;
		 
	}

	public boolean euCountrySearch(String nombrePais) {
		boolean paisOk = false;
		int contador= 0;
		
		while (contador < euCountries.size() && !paisOk) {
			if (euCountries.get(contador).equalsIgnoreCase(nombrePais)){
				paisOk = true;
			}
			contador ++;
		}	
		return paisOk;
		
	}

}
