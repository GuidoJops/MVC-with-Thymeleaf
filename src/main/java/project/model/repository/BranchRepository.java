package project.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import project.model.domain.Branch;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long>{

	@Query("SELECT b FROM Branch b WHERE LOWER(b.branchName) = LOWER(:name)")
	Branch findByBranchName(String name);
}
