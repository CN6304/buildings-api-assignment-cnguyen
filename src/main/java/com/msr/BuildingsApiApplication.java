package com.msr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.model.Site;
import com.msr.model.SiteUse;
import com.msr.model.UseType;
import com.msr.service.SiteService;
import com.msr.service.SiteUseService;
import com.msr.service.UseTypeService;
import com.msr.util.ClasspathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BuildingsApiApplication {
	private static final Logger log = LoggerFactory.getLogger(BuildingsApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BuildingsApiApplication.class, args);
	}

	@Bean
	@Profile("dev")
	CommandLineRunner dataLoader(SiteService siteService,
								   SiteUseService siteUseService,
								   UseTypeService useTypeService) {
		return args -> {
			ObjectMapper mapper = new ObjectMapper();

			String json = ClasspathUtil.readFileToString("data/sites.json", Site.class);
			List<Site> sites = Arrays.asList(mapper.readValue(json, Site[].class));
			log.info("Loaded site data in H2 database: " + siteService.save(sites));

			json = ClasspathUtil.readFileToString("data/use_types.json", UseType.class);
			List<UseType> useTypes = Arrays.asList(mapper.readValue(json, UseType[].class));
			log.info("Loaded use_type data in H2 database: " + useTypeService.save(useTypes));

			json = ClasspathUtil.readFileToString("data/site_uses.json", SiteUse.class);
			List<SiteUse> siteUses = Arrays.asList(mapper.readValue(json, SiteUse[].class));
			log.info("Loaded site_use in H2 database: " + siteUseService.save(siteUses));
		};
	}
}
