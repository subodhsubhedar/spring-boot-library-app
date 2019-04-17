package com.myapp.library.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.myapp.library.controller.LibrarySubjectController;
import com.myapp.library.menu.controller.LibraryBookControllerTestManager;
import com.myapp.library.menu.controller.LibraryErrorControllerTestManager;
import com.myapp.library.menu.controller.LibraryMenuControllerTestManager;
import com.myapp.library.menu.controller.LibrarySubjectControllerTestManager;
import com.myapp.library.menu.controller.LibraryWelcomeControllerTestManager;
import com.myapp.library.menu.dao.LibraryDaoIntegrationTestManager;
import com.myapp.library.menu.service.LibraryServiceIntegrationTestManager;
import com.myapp.library.menu.service.LibraryServiceUnitTestManager;

@RunWith(Suite.class)
@SuiteClasses({ LibraryMenuControllerTestManager.class, LibraryWelcomeControllerTestManager.class,
		LibrarySubjectControllerTestManager.class, LibraryBookControllerTestManager.class,
		LibraryErrorControllerTestManager.class, LibraryDaoIntegrationTestManager.class,
		LibraryServiceIntegrationTestManager.class, LibraryServiceUnitTestManager.class })
public class LibraryAppTestSuite {

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(LibraryAppTestSuite.class);
		int i = 1;

		for (Failure failure : result.getFailures()) {
			System.out.println("###############  " + "FAILURE : " + i + "  ###############");
			System.out.println("\n" + failure.toString());
			failure.getException().printStackTrace();
			i++;
		}

		System.out.println("Test successful? " + result.wasSuccessful());

		System.out.println("\n###############  " + "END OF TEST, TOTAL RUNS : " + i + "  ###############");
	}

}
 