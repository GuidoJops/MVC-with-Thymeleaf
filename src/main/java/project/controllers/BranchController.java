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
		List<BranchDto> branches = branchService.getAllBranches();

		model.addAttribute("title", "Available Branches");
		model.addAttribute("branches", branches);

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
	public String searchBranch(@RequestParam("nameOrId") String nameOrId, Model model, RedirectAttributes msg) {
		BranchDto branchDto = getBranchDto(nameOrId);
		if (branchDto == null) {
			msg.addFlashAttribute("error", "The data entered does not match any Branch in the system.");
			return "redirect:/branch";
		}

		model.addAttribute("title", "Branch found");
		model.addAttribute("branches", branchDto);
		msg.addFlashAttribute("success", "Yes! We know that name.");

//		return "/views/branches/branches-list"; // no me muestra el "msg"
		return "redirect:/branch"; // con esto me muestra el "msg" pero no el model


	}

	private BranchDto getBranchDto(String nameOrId) {
		BranchDto branchDto;
		if (nameOrId.length() < 1) {
			return null;
		}

		if (isNumeric(nameOrId)) {
			Long id = Long.parseLong(nameOrId);
			branchDto = branchService.getOneBranchById(id);
		} else {
			branchDto = branchService.getOneBranchByName(nameOrId);
		}

		return branchDto;
	}

	private boolean isNumeric(String str) {
		return str.chars().allMatch( Character::isDigit );
	}

	@PostMapping("/save")
	public String saveBranch(@Valid @ModelAttribute BranchDto branchDto,
							 BindingResult result, Model model, RedirectAttributes msg) {

		//User input Validation
		if (hasErrorsOrMissingCountry(branchDto, result)) {
			handleErrors(branchDto, model, msg);
			msg.addFlashAttribute("error", "Verify that all fields are complete.");
			return "redirect:/branch/add";
		}

		//System Validation
		if (!branchService.saveBranch(branchDto)) {
			msg.addFlashAttribute("error", "Name already taken.");
			return "redirect:/branch/add";
		}

		msg.addFlashAttribute("success", "Branch has been saved successfully");
		return "redirect:/branch";
	}

	private boolean hasErrorsOrMissingCountry(BranchDto branchDto, BindingResult result) {
		return result.hasErrors() || branchDto.getCountry() == null;
	}

	private void handleErrors(BranchDto branchDto, Model model, RedirectAttributes msg) {
		List<Country> countries = countryService.listCountries();

		model.addAttribute("title", "Add Branch");
		model.addAttribute("button", "Save Branch");
		model.addAttribute("branch", branchDto);
		model.addAttribute("countries", countries);

	}

	@GetMapping("/edit/{id}")
	public String editBranch(@PathVariable("id") Long id, Model model, RedirectAttributes msg) {
		BranchDto branchDto = branchService.getOneBranchById(id);

		if (branchDto == null) {
			System.out.println("Branch not found");
			msg.addFlashAttribute("error", "The data entered does not match any Branch in the system.");
			return "redirect:/branch";
		}

		List<Country> countries = countryService.listCountries();
		model.addAttribute("title", "Edit Branch");
		model.addAttribute("button", "Update Branch");
		model.addAttribute("branch", branchDto);
		model.addAttribute("countries", countries);
		return "/views/branches/form-save-branch";
	}

	@GetMapping("/delete/{id}")
	public String deleteBrunch(@PathVariable("id") Long id, RedirectAttributes msg) {

		if (branchService.deleteBranchById(id)) {
			msg.addFlashAttribute("warning", "Branch deleted");
		} else {
			msg.addFlashAttribute("error", "The data entered does not match any Branch in the system.");
		}
		return "redirect:/branch";
	}

}



