package project.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.model.domain.Country;

//Countries are loaded automatically when program starts.

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

}
