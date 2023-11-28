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

	private static final String ATTRIBUTE_NAME_TITLE = "title";
	private static final String ATTRIBUTE_NAME_BRANCHES = "branches";
	private static final String ATTRIBUTE_NAME_SUCCESS = "success";
	private static final String ATTRIBUTE_NAME_ERROR = "error";
	private static final String SEARCH_ERROR_MESSAGE = "The data entered does not match any Branch in the system.";
	private static final String URL_REDIRECT_BRANCH = "redirect:/branch";

	@Autowired
	private BranchService branchService;
	
	@Autowired
	private CountryService countryService;
	
	@GetMapping("")
	public String listBranches(Model model){
		List<BranchDto> branches = branchService.getAllBranches();
		model.addAttribute(ATTRIBUTE_NAME_TITLE, "Available Branches");
		model.addAttribute(ATTRIBUTE_NAME_BRANCHES, branches);
		return "/views/branches/branches-list";
	}

	@GetMapping("/search")
	public String searchBranch(@RequestParam("nameOrId") String nameOrId, Model model, RedirectAttributes msg) {
		BranchDto branchDto = getBranchDto(nameOrId);
		if (branchDto == null) {
			msg.addFlashAttribute(ATTRIBUTE_NAME_ERROR, SEARCH_ERROR_MESSAGE);
			return URL_REDIRECT_BRANCH;
		}
		model.addAttribute(ATTRIBUTE_NAME_TITLE, "Branch found");
		model.addAttribute(ATTRIBUTE_NAME_BRANCHES, branchDto);
		model.addAttribute(ATTRIBUTE_NAME_SUCCESS, "Yes! We know that Branch.");
		return "/views/branches/branches-list";
	}

	@GetMapping("/add")
	public String showNewOrUpdateBranchForm(Model model) {
		return showBranchForm(model, new BranchDto(), "Add Branch", "Save Branch");
	}

	@PostMapping("/save")
	public String saveBranch(@Valid @ModelAttribute BranchDto branchDto,
							 BindingResult result, RedirectAttributes msg) {
		//User input Validation
		if (hasErrorsOrMissingCountry(branchDto, result)) {
			msg.addFlashAttribute(ATTRIBUTE_NAME_ERROR, "Verify that all fields are complete.");
			return getRedirectForBranchForm(branchDto);
		}
		//System Validation
		if (!branchService.saveBranch(branchDto)) {
			msg.addFlashAttribute(ATTRIBUTE_NAME_ERROR, "Name already taken.");
			return getRedirectForBranchForm(branchDto);
		}
		msg.addFlashAttribute(ATTRIBUTE_NAME_SUCCESS, "Branch has been saved successfully");
		return URL_REDIRECT_BRANCH;
	}

	@GetMapping("/edit/{id}")
	public String editBranch(@PathVariable("id") Long id, Model model, RedirectAttributes msg) {
		BranchDto branchDto = branchService.getOneBranchById(id);
		if (branchDto == null) {
			System.out.println("Branch not found");
			msg.addFlashAttribute(ATTRIBUTE_NAME_ERROR, SEARCH_ERROR_MESSAGE);
			return URL_REDIRECT_BRANCH;
		}
		return showBranchForm(model, branchDto, "Edit Branch", "Update Branch");
	}

	@GetMapping("/delete/{id}")
	public String deleteBrunch(@PathVariable("id") Long id, RedirectAttributes msg) {
		if (branchService.deleteBranchById(id)) {
			msg.addFlashAttribute("warning", "Branch deleted.");
		} else {
			msg.addFlashAttribute(ATTRIBUTE_NAME_ERROR, SEARCH_ERROR_MESSAGE);
		}
		return URL_REDIRECT_BRANCH;
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

	private String showBranchForm(Model model, BranchDto branchDto, String title, String button) {
		List<Country> countries = countryService.listCountries();
		model.addAttribute(ATTRIBUTE_NAME_TITLE, title);
		model.addAttribute("button", button);
		model.addAttribute("branch", branchDto);
		model.addAttribute("countries", countries);
		return "/views/branches/form-save-branch";
	}

	private boolean hasErrorsOrMissingCountry(BranchDto branchDto, BindingResult result) {
		return result.hasErrors() || branchDto.getCountry() == null;
	}

	private String getRedirectForBranchForm(BranchDto branchDto) {
		return (branchDto.getId() != null) ? "redirect:/branch/edit/" + branchDto.getId() : "redirect:/branch/add";
	}






}



