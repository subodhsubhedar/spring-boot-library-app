package com.myapp.library.menu.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.myapp.library.controller.LibraryMenuController;
import com.myapp.library.controller.validation.MenuValidator;
import com.myapp.library.menu.MainMenu;
import com.myapp.library.menu.MainMenuModel;

@RunWith(SpringRunner.class)
@WebMvcTest(value = { LibraryMenuController.class, MenuValidator.class })
@WithMockUser(username = "subodh", password = "suyog")
public class LibraryMenuControllerTestManager {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}

	@Test
	public void testMainMenu_shouldReturnMenuView() throws Exception {

		List<MainMenuModel> menuModelList = new ArrayList<MainMenuModel>();

		for (MainMenu menu : MainMenu.values()) {
			MainMenuModel menuModel = new MainMenuModel(menu.getKey(), menu.getValue());
			menuModelList.add(menuModel);
		}

		this.mockMvc.perform(get("/menu/show-main"))
				.andExpect(model().attribute("welcomeMsg", "Welcome .."))
				.andExpect(model().attribute("selectionMsg", "Please choose one of the option below"))
				.andExpect(model().attribute("menuModelList", menuModelList))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(view().name("main-menu"));

	}
	
	
	@Test
	public void testMainMenuNullModel_shouldReturnMenuView() throws Exception {

		List<MainMenuModel> menuModelList = new ArrayList<MainMenuModel>();

		for (MainMenu menu : MainMenu.values()) {
			MainMenuModel menuModel = new MainMenuModel(menu.getKey(), menu.getValue());
			menuModelList.add(menuModel);
		}

		MainMenuModel defaultModel = new MainMenuModel(MainMenu.ADD_NEW_SUBJECT.getKey(), MainMenu.ADD_NEW_SUBJECT.getValue());
		
		
		MainMenuModel mainMenuModel = new MainMenuModel(0, null);
		
		this.mockMvc.perform(get("/menu/show-main")
				.flashAttr("mainMenuModel", mainMenuModel)
				)
				.andExpect(model().attribute("welcomeMsg", "Welcome .."))
				.andExpect(model().attribute("selectionMsg", "Please choose one of the option below"))
				.andExpect(model().attribute("menuModelList", menuModelList))
				.andExpect(model().attribute("mainMenuModel", defaultModel))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(view().name("main-menu"));

	}

	@Test
	public void testGoBack_shouldRedirectMenuView() throws Exception {

		this.mockMvc.perform(get("/menu/goBackToMainMenu")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/menu/show-main")).andDo(print());
	}

	@Test
	public void testQuit_shouldRedirectToRoot() throws Exception {

		this.mockMvc.perform(get("/menu/quit")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"))
				.andDo(print());
	}

	@Test
	public void testPostMainMenuWithInvalidMenuIndex_shouldThrowValidationError() throws Exception {

		this.mockMvc.perform(post("/menu/process-main")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("menuIndex", "")
				.param("menuCriteria", "")
				.param("menuDesc", "")
				)
					.andDo(print()).andExpect(status().isOk())
					.andExpect(model().attributeHasFieldErrors("mainMenuModel", "menuIndex"))
					.andExpect(view().name("main-menu"));
	}

	@Test
	public void testPostMainMenuWithInvalidMenuCriterai_shouldThrowValidationError() throws Exception {

		 
		List<Integer> criteriaMenuList = Arrays.asList(3, 4, 5, 6);
		
		for(int menuVal : criteriaMenuList) {
			
			 this.mockMvc.perform(post("/menu/process-main")
					 .contentType(MediaType.APPLICATION_FORM_URLENCODED)
					 .param("menuIndex", String.valueOf(menuVal))
					 .param("menuDesc", "test")
					 .param("menuCriteria", "")
					 	).andDo(print()).andExpect(status().isOk())
						.andExpect(model().attributeHasFieldErrors("mainMenuModel", "menuCriteria"))
			 			.andExpect(view().name("main-menu"));
		}
	} 
	
	@Test
	public void testPostMainMenuWithInApplicableMenuCriteria_shouldNotThrowValidationError() throws Exception {

		 
		List<Integer> criteriaMenuList = Arrays.asList(1,2,7,8,9,0);
		
		for(int menuVal : criteriaMenuList) {
			
			 this.mockMvc.perform(post("/menu/process-main")
					 .contentType(MediaType.APPLICATION_FORM_URLENCODED)
					 .param("menuIndex", String.valueOf(menuVal))
					 .param("menuDesc", "test")
					 .param("menuCriteria", "")
					 )
			 			.andDo(print()).andExpect(status().is3xxRedirection());
		}
	}
	
	@Test
	public void testPostMainMenuWithValidMenuCriteria_shouldRedirectToCorrectView() throws Exception {

		List<Integer> criteriaMenuList = Arrays.asList(3, 4, 5, 6);
		
		Map<Integer, String> menuMap =  new HashMap<Integer,String>(); 
		menuMap.put(3, "delete-subject");
		menuMap.put(4, "delete-book");
		menuMap.put(5, "search-subject");
		menuMap.put(6, "search-book");
		
		for(int menuVal : criteriaMenuList) {
			
			 this.mockMvc.perform(post("/menu/process-main")
					 .contentType(MediaType.APPLICATION_FORM_URLENCODED)
					 .param("menuIndex", String.valueOf(menuVal))
					 .param("menuDesc", "test")
					 .param("menuCriteria", "Spring Boot")
					 	)
			 	.andDo(print()).andExpect(status().is3xxRedirection())
			 	.andExpect(redirectedUrl(menuMap.get(menuVal)));
		}
	} 
	
	@Test
	public void testPostMainMenuWithDefault_shouldRedirectToCorrectView() throws Exception {

			 this.mockMvc.perform(post("/menu/process-main")
					 .contentType(MediaType.APPLICATION_FORM_URLENCODED)
					 .param("menuIndex","999")
					 .param("menuDesc", "test")
					 .param("menuCriteria", "Spring Boot")
					 	)
			 	.andDo(print()).andExpect(status().is3xxRedirection())
			 	.andExpect(redirectedUrl("#"));
	} 

}