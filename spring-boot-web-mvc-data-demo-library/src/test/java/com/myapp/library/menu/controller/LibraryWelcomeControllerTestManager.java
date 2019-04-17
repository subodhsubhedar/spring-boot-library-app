package com.myapp.library.menu.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.myapp.library.controller.LibraryWelcomeController;

@RunWith(SpringRunner.class)
@WebMvcTest(LibraryWelcomeController.class)
public class LibraryWelcomeControllerTestManager {

	@Autowired
	private MockMvc mockMvc;

	@Before
	public void setUp() {

	}

	@WithMockUser(value = "Subodh")
	@Test
	public void testWelcomePage_shouldReturnCorrectView() throws Exception {

		this.mockMvc.perform(get("")
				.param("loggedInUser", "Subodh"))
				.andExpect(status().isOk())
				.andExpect(view().name("default"));

	}

}
