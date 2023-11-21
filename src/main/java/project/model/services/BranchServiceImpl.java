package project.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.mappers.BranchMapper;
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
		List<BranchDto> dto = branchMapper.entityToDto(branchRepository.findAll());
		return dto;
	}

	@Override
	public void saveBranch(BranchDto branchDto) {
		branchRepository.save(branchMapper.dtoToEntity(branchDto));
	}

	@Override
	public BranchDto getOneBranchById(Long id) {
		return branchMapper.entityToDto(branchRepository.findById(id).orElse(null));
	}

	@Override
	public void deleteBranchById(Long id) {
		branchRepository.deleteById(id);

	}

	@Override
	public BranchDto getOneBranchByName(String nombre) {
		return branchMapper.entityToDto(branchRepository.findBybranchName(nombre));
	}



}
