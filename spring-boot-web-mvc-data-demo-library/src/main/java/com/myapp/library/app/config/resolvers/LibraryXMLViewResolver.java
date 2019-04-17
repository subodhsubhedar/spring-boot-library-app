package com.myapp.library.app.config.resolvers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.view.XmlViewResolver;

@Configuration
public class LibraryXMLViewResolver {

	@Bean
	public XmlViewResolver getLibXmlViewResolver() {

		XmlViewResolver resolver = new XmlViewResolver();
		resolver.setLocation(new ClassPathResource("library-app-views.xml"));

		resolver.setOrder(1);
		return resolver;
	}

}
