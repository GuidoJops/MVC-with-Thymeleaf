package project.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Countries are loaded automatically when program starts.

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table (name="country")
public class Country {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	
	public Country(String name) {
		this.name = name;
	}

}
