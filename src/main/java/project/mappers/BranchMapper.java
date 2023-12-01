package project.mappers;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import project.model.domain.Branch;
import project.model.dto.BranchDto;

@Getter
@Setter
@Component
public class BranchMapper {

    private Branch branch;
    private BranchDto branchDto;

	public List<BranchDto> entityToDto(List<Branch> listEntity) {
		return listEntity.stream().map(this::entityToDto).toList();
	}

	public BranchDto entityToDto(Branch branch) {
        if (branch == null) {
            return null;
        }

		branchDto = new BranchDto();
        branchDto.setId(branch.getId());
        branchDto.setBranchName(branch.getBranchName());
        branchDto.setCountry(branch.getCountry());
        branchDto.setBranchType(branchDto.definesBranchType());
        return branchDto;
    }

    public Branch dtoToEntity(BranchDto branchDto) {
		branch = new Branch();
        branch.setId(branchDto.getId());
        branch.setBranchName(branchDto.getBranchName());
        branch.setCountry(branchDto.getCountry());
        return branch;
    }

}
