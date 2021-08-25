package com.am.config;

import com.am.converter.DateProvider;
import com.am.converter.DateToStringConverter;
import com.am.converter.StringProvider;
import com.am.converter.StringToDateConverter;
import java.util.Date;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralConfiguration {

  @Bean("alertModelMapper")
  public ModelMapper getModelMapper() {
    ModelMapper mapper = new ModelMapper();
    mapper
        .getConfiguration()
        .setAmbiguityIgnored(true)
        .setMatchingStrategy(MatchingStrategies.STRICT);
    mapper.createTypeMap(String.class, Date.class);
    mapper.addConverter(new StringToDateConverter());
    mapper.getTypeMap(String.class, Date.class).setProvider(new DateProvider());
    mapper.createTypeMap(Date.class, String.class);
    mapper.addConverter(new DateToStringConverter());
    mapper.getTypeMap(Date.class, String.class).setProvider(new StringProvider());
    return mapper;
  }
}
