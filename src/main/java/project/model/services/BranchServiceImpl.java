package project.model.services;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.mappers.BranchMapper;
import project.model.domain.Branch;
import project.model.dto.BranchDto;
import project.model.repository.BranchRepository;

@Slf4j
@Service
public class BranchServiceImpl implements BranchService {
	
	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private BranchMapper branchMapper;
	
	@Override
	public List<BranchDto> getAllBranches() {
		return branchMapper.entityToDto(branchRepository.findAll());
	}

	@Override
	public boolean saveBranch(BranchDto branchDto) {
		Branch existingBranch = branchRepository.findByBranchName(branchDto.getBranchName());

		if (shouldSaveBranch(existingBranch, branchDto)) {
			branchRepository.save(branchMapper.dtoToEntity(branchDto));
			return true;
		}
		log.info("Branch Name already taken.");
		return false;
	}

	@Override
	public BranchDto getOneBranchById(Long id) {
		return branchMapper.entityToDto(branchRepository.findById(id).orElse(null));
	}

	@Override
	public BranchDto getOneBranchByName(String name) {

		return branchMapper.entityToDto(branchRepository.findByBranchName(name));
	}

	@Override
	public boolean deleteBranchById(Long id) {
		Branch branch = branchRepository.findById(id).orElse(null);
		if (branch == null) {
			log.info("Branch not found");
			return false;
		}
		branchRepository.deleteById(id);
		log.info(branch.getBranchName() + " deleted");
		return true;
	}

	private boolean shouldSaveBranch(Branch branch, BranchDto branchdto){
		return branch == null || branch.getBranchName() == null ||
				isBranchValid(branch, branchdto);
	}

	private boolean isBranchValid(Branch existingBranch, BranchDto branchDto) {
		return existingBranch.getId() > 0 && existingBranch.getBranchName().equalsIgnoreCase(branchDto.getBranchName());
	}

}
