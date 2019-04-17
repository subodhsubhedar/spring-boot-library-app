package com.myapp.library.controller;

import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.myapp.library.controller.validation.SearchSubjectValidator;
import com.myapp.library.controller.validation.SubjectSearchCriteria;
import com.myapp.library.controller.validation.SubjectValidator;
import com.myapp.library.entity.Subject;
import com.myapp.library.exception.LibraryServiceException;
import com.myapp.library.menu.service.LibraryService;

@Controller
@RequestMapping("/menu")
public class LibrarySubjectController {

	@Autowired
	private LibraryService catalogueService;

	@Autowired
	private SubjectValidator validator;

	@Autowired
	private SearchSubjectValidator searchSubjectValidator;

	@InitBinder(value = { "subject", "searchSubjectCriteria" })
	protected void initBinder(WebDataBinder binder) {

		if (binder.getTarget().getClass().equals(Subject.class)) {
			binder.setValidator(validator);

		} else if (binder.getTarget().getClass().equals(SubjectSearchCriteria.class)) {
			binder.setValidator(searchSubjectValidator);
		}
	}

	@RequestMapping(value = "/all-subjects", method = RequestMethod.GET)
	public ModelAndView listAllSubjects(ModelMap map) {

		System.out.println("listAllSubjects1 method...");
		ModelAndView mv = new ModelAndView("all-subjects-view");

		try {
			Set<Subject> subjectSet = this.findAllSubjects();
			if (subjectSet != null) {
				mv.addObject("allSubjectsList", subjectSet);
			}

		} catch (LibraryServiceException e) {
			e.printStackTrace();
		}
		return mv;

	}

	/**
	 * 
	 * 
	 * @throws LibraryServiceException
	 */
	public Set<Subject> findAllSubjects() throws LibraryServiceException {

		Set<Subject> subjectSet = catalogueService.findAllSubjects();

		if (subjectSet != null && !subjectSet.isEmpty()) {
			System.out.println("\nTotal Subjects available : " + subjectSet.size());
			subjectSet.forEach(name -> {
				System.out.println(name.toString());
			});
		} else {
			System.out.println("\nNo Subjects are available currently in the Library Catalogue.");
		}
		return subjectSet;
	}

	@RequestMapping(value = "/search-subject", method = RequestMethod.GET)
	public ModelAndView searchSubject(@ModelAttribute("searchSubjectTitle") final @Validated String  subjectTitle) {

		ModelAndView mv = new ModelAndView("search-subject-view");

		try {
			Subject subject = getSubject(subjectTitle);
			if (subject != null) {
				mv.addObject("subject", subject);
			}

		} catch (LibraryServiceException e) {
			e.printStackTrace();
		}
		return mv;

	}

	@RequestMapping(value = "/delete-subject", method = RequestMethod.GET)
	public ModelAndView deleteSubject(@ModelAttribute("deleteSubjectTitle") final String subjectTitle) {

		ModelAndView mv = new ModelAndView("delete-subject-view");

		try {
			Subject subject = getSubject(subjectTitle);
			if (subject != null) {
				mv.addObject("subject", subject);
			}

			if (subject != null && (subject.getReferences() == null || (subject.getReferences().isEmpty()))) {
				mv.addObject("noBooks", true);
			} else {
				mv.addObject("noBooks", false);
			}

		} catch (LibraryServiceException e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/delete-subject", method = RequestMethod.POST)
	public ModelAndView processDeleteSubject(@ModelAttribute("subject") final @Validated Subject subject) {

		ModelAndView mv = new ModelAndView("delete-subject-view");
		System.out.println("Subject title to be deleted : " + subject.getSubtitle());
		try {
			catalogueService.deleteSubject(subject.getSubtitle());
			mv.addObject("deletedSubjectTitle", subject.getSubtitle());

		} catch (LibraryServiceException e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/add-new-subject", method = RequestMethod.GET)
	public ModelAndView addNewSubject(Subject subject) {

		return new ModelAndView("add-subject-view");
	}

	@RequestMapping(value = "/add-new-subject", method = RequestMethod.POST)
	public ModelAndView createSubject(@ModelAttribute("subject") final @Validated Subject subject,
			BindingResult result) {

		ModelAndView mv = new ModelAndView("add-subject-view");

		if (result.hasErrors()) {
			return mv;
		}

		System.out.println("Subject  to be added: " + subject);

		Random random = new Random();
		subject.setSubjectId(random.nextLong());

		try {
			catalogueService.createSubject(subject);
		} catch (Exception e) {
			e.printStackTrace();
		}

		mv.addObject("addedSubjectTitle", subject.getSubtitle());

		return mv;
	}

	@RequestMapping(value = "/search-subject-by-duration", method = RequestMethod.GET)
	public ModelAndView searchSubjectByDuration(
			@ModelAttribute("searchSubjectCriteria") final SubjectSearchCriteria searchSubjectCriteria) {

		return new ModelAndView("search-subject-by-duration-view");
	}

	@RequestMapping(value = "/search-subject-by-duration", method = RequestMethod.POST)
	public ModelAndView searchSubjectByDuration(
			@ModelAttribute("searchSubjectCriteria") final @Validated SubjectSearchCriteria subjectSearchCriteria,
			BindingResult result) {

		if (result.hasErrors()) {
			ModelAndView mv = new ModelAndView("search-subject-by-duration-view");

			return mv;
		}

		Set<Subject> subjectSet = null;
		try {
			subjectSet = catalogueService.findSubjectByDuration(subjectSearchCriteria.getDurationStart(),
					subjectSearchCriteria.getDurationEnd());
		} catch (Exception e) {
			e.printStackTrace();
		}

		ModelAndView mvSuccess = new ModelAndView("all-subjects-view");

		if (subjectSet != null) {
			mvSuccess.addObject("allSubjectsList", subjectSet);
		}

		return mvSuccess;
	}

	/**
	 * 
	 * @param subjTitle
	 * @throws LibraryServiceException
	 */
	public Subject getSubject(String subjTitle) throws LibraryServiceException {
		Subject subject = catalogueService.getSubject(subjTitle);

		if (subject != null) {
			System.out.println("\nSubject retrieved : " + subject);
		} else {
			System.out.println("\nSubject not Found.");
		}
		return subject;
	}
}
