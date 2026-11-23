package com.onclass.ms_tecnology.infrastructure.configuration;

import com.onclass.ms_tecnology.application.usecase.AssociateCapabilityTechnologiesUseCase;
import com.onclass.ms_tecnology.application.usecase.GetTechnologiesByCapabilityUseCase;
import com.onclass.ms_tecnology.application.usecase.RegisterTechnologyUseCase;
import com.onclass.ms_tecnology.domain.repository.TechnologyRepository;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public RegisterTechnologyUseCase registerTechnologyUseCase(TechnologyRepository technologyRepository) {
        return new RegisterTechnologyUseCase(technologyRepository);
    }

    @Bean
    public AssociateCapabilityTechnologiesUseCase associateCapabilityTechnologiesUseCase(TechnologyRepository technologyRepository) {
        return new AssociateCapabilityTechnologiesUseCase(technologyRepository);
    }

    @Bean
    public GetTechnologiesByCapabilityUseCase getTechnologiesByCapabilityUseCase(TechnologyRepository technologyRepository) {
        return new GetTechnologiesByCapabilityUseCase(technologyRepository);
    }

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }
}
