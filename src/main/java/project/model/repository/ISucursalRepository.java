package project.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.model.domain.Sucursal;

@Repository
public interface ISucursalRepository extends JpaRepository<Sucursal, Long>{

	public Sucursal findBynombreSucursal(String nombre);
}
