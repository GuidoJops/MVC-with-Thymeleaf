package project.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.mappers.BranchMapper;
import project.model.domain.Branch;
import project.model.dto.BranchDto;
import project.model.repository.BranchRepository;

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

		if (existingBranch == null || existingBranch.getBranchName() == null) {
			branchRepository.save(branchMapper.dtoToEntity(branchDto));
			return true;
		}

		String branchName = existingBranch.getBranchName();
		if (branchName.equalsIgnoreCase(branchDto.getBranchName())) {
			System.out.println("Branch Name already taken.");
			return false;
		}

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
			System.out.println("Branch not found");
			return false;
		}
		branchRepository.deleteById(id);
		System.out.println(branch.getBranchName() + " deleted");
		return true;
	}

}
