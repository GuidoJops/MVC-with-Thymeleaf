package project.mappers;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import project.model.domain.Branch;
import project.model.dto.BranchDto;

@Component
@Getter
@Setter
public class BranchMapper {
	
	private Branch branch;
	private BranchDto branchDto;
	
	

	public BranchDto entityToDto(Branch branch) {
		if(branch == null) {
			return null;
		}

		String CountryName = branch.getCountryBranch().getName();
		BranchDto branchDto = new BranchDto();

		branchDto.setId(branch.getId());
		branchDto.setBranchName(branch.getBranchName());
		branchDto.setCountryBranch(branch.getCountryBranch());
		branchDto.setBranchType(branchDto.definesBranchType(CountryName));

		System.out.println("Devolviendo DTO...");

		return branchDto;
	}
	
	public List<BranchDto> entityToDto(List<Branch> listEntity){
		System.out.println("Devolviendo Lista DTO...");

		return listEntity.stream().map(x-> entityToDto(x)).toList();
	}
	
	public Branch dtoToEntity(BranchDto branchDto) {
		Branch branch = new Branch();
		
		branch.setId(branchDto.getId());
		branch.setBranchName(branchDto.getBranchName());
		branch.setCountryBranch(branchDto.getCountryBranch());
		
		System.out.println("Devolviendo Entidad...");

		return branch;
		
	}
	
}
