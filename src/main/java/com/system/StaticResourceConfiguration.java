package com.system;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class StaticResourceConfiguration extends WebMvcConfigurerAdapter {
	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
			"classpath:/resources/", "classpath:/static/", "classpath:/static/uploads/" };

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String project_path = System.getProperty("user.dir");
		registry.addResourceHandler("/uploads/**")
				.addResourceLocations("file:///" + project_path + "/src/main/resources/uploads/");
		registry.addResourceHandler("/uploads_document/**")
				.addResourceLocations("file:///" + project_path + "/src/main/resources/uploads_document/");
	}
}
