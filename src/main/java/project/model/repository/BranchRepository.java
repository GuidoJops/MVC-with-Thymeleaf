package project.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.model.domain.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long>{

	Branch findBybranchName(String nombre);
}
