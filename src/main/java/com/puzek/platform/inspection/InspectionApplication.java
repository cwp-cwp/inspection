package com.puzek.platform.inspection;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan({"com.puzek.platform.inspection.dao", "com.cwp.cloud"})
@EnableScheduling
public class InspectionApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
        return applicationBuilder.sources(InspectionApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(InspectionApplication.class, args);
	}

}
