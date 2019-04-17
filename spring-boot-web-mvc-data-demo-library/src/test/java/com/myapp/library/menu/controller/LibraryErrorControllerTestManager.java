package com.myapp.library.menu.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.myapp.library.controller.ErrorController;
import com.myapp.library.controller.LibraryBookController;
import com.myapp.library.controller.validation.BookValidator;
import com.myapp.library.exception.LibraryServiceException;
import com.myapp.library.menu.service.LibraryService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = { ErrorController.class, LibraryBookController.class, BookValidator.class })
@WithMockUser(username = "subodh", password = "suyog")
public class LibraryErrorControllerTestManager {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private LibraryService serviceMock;

	@Autowired
	private ErrorController errorController;

	@Mock
	private HttpServletRequest httpServlerRequestMock;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testErrorController_shouldShowErrorView() throws Exception {

		when(httpServlerRequestMock.getRequestURL()).thenReturn(new StringBuffer("/menu/all-books"));

		ModelAndView mv = errorController.handleException(httpServlerRequestMock,
				new LibraryServiceException("DB Connection error"));

		assertTrue(mv.getViewName().equals("error"));

	}

}
