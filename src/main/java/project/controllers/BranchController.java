package project.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.model.domain.Country;
import project.model.dto.BranchDto;
import project.model.services.CountryService;
import project.model.services.BranchService;
import jakarta.validation.Valid;

@Controller
@RequestMapping ("/branch")
public class BranchController {
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private CountryService countryService;
	

	@GetMapping("")
	public String listBranches(Model model){
		model.addAttribute("title", "Available Branches");
		model.addAttribute("branches", branchService.getAllBranches());

		return "/views/branches/branches-list";
	}
	
	@GetMapping("/add")
	public String showNewBranchForm(Model model) {
		BranchDto branchDto = new BranchDto();
		List<Country> countries = countryService.listCountries();
	
		model.addAttribute("title", "Add Branch");
		model.addAttribute("button", "Save Branch");
		model.addAttribute("branch", branchDto);
		model.addAttribute("countries",countries);

		return "/views/branches/form-save-branch";
	}
	
	@GetMapping("/search")
	public String searchById(@RequestParam("nameOrId") String nameOrId, Model model, RedirectAttributes msg) {
        boolean isNumeric = nameOrId.chars().allMatch( Character::isDigit );
        BranchDto branchDto = new BranchDto();
        
        if(isNumeric) {
        	Long id = Long.parseLong(nameOrId);
        	branchDto = branchService.getOneBranchById(id);

        } else {
        	branchDto = branchService.getOneBranchByName(nameOrId);
        }
        
        if(branchDto !=null) {
			model.addAttribute("title", "Branch found");
			model.addAttribute("branches", branchDto);
			return "/views/branches/branches-list";
			
		} else {
			System.out.println("No branch found");
			msg.addFlashAttribute("error", "The data entered does not match any Branch in the system.");
		}
		
		return "redirect:/branch";
		
	}
	
	//NO FUNCIONA VALIDACION POR CAMPO EN LA VISTA
	@PostMapping("/save")
	public String saveBranch(@Valid @ModelAttribute BranchDto branchDto,
								BindingResult result, Model model, RedirectAttributes msg ) {
		
		if(result.hasErrors() || branchDto.getCountryBranch()==null) {
			System.out.println(result.getAllErrors());
			List<Country> countries = countryService.listCountries();
			
			model.addAttribute("title", "Agregar Sucursal");
			model.addAttribute("button", "Guardar Sucursal");
			model.addAttribute("branch", branchDto);
			model.addAttribute("countries",countries);
			
			System.out.println("Errors detected, check form");
			msg.addFlashAttribute("error", "Verify that all fields are complete.");

			return "/views/branches/form-save-branch";
		}
		
		branchService.saveBranch(branchDto);
		System.out.println("Branch has been saved");
		msg.addFlashAttribute("success", "Branch has been saved successfully");

				
		return "redirect:/branch";
	}

	@GetMapping("/edit/{id}")
	public String editBranch(@PathVariable("id") Long id, Model model, RedirectAttributes msg) {
		BranchDto branchDto = branchService.getOneBranchById(id);
		List<Country> countries = countryService.listCountries();

		
		if(branchDto !=null) {
			model.addAttribute("title", "Edit Branch");
			model.addAttribute("button", "Update Branch");
			model.addAttribute("branch", branchDto);
			model.addAttribute("countries",countries);
			return "/views/branches/form-save-branch";
			
		} else {
			System.out.println("Branch not found");
			msg.addFlashAttribute("error", "The data entered does not match any Branch in the system.");
		}
	
		return "redirect:/branch";
	
	}

	@GetMapping("/delete/{id}")
	public String deleteBrunch(@PathVariable("id") Long id, RedirectAttributes msg) {
		BranchDto branchDto = branchService.getOneBranchById(id);
		
		if(branchDto !=null) {
			branchService.deleteBranchById(branchDto.getId());
			msg.addFlashAttribute("warning", branchDto.getBranchName()+" deleted");
			return "redirect:/branch";
						
		} else {
			System.out.println("Branch not found");
			msg.addFlashAttribute("error", "The data entered does not match any Branch in the system.");
		}
		return "redirect:/branch";
	
	}
	

}