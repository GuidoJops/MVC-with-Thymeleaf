package project.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.model.domain.Pais;

//Los Paises se cargan automaticamente al iniciar el programa

@Repository
public interface IPaisRepository extends JpaRepository<Pais, Long> {

}
