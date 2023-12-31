package project.model.services;

import java.util.List;

import project.model.dto.BranchDto;

public interface BranchService {

	List<BranchDto> getAllBranches();
	
	BranchDto getOneBranchById(Long id);
	
	BranchDto getOneBranchByName(String name);
	
	boolean saveBranch (BranchDto branchDto);
	
	boolean deleteBranchById (Long id);
	
	
	

}
