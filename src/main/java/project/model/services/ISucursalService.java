package project.model.services;

import java.util.List;

import project.model.dto.SucursalDto;

public interface ISucursalService {
	
	
	List<SucursalDto> getAllSucursales();
	
	SucursalDto getOneById(Long id);
	
	SucursalDto getOneByName(String nombre);
	
	void saveSucursal (SucursalDto sucursalDto);
	
	void deleteSucursal (Long id);
	
	
	

}
