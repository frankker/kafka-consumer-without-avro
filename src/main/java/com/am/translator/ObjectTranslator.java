/*
 * Copyright @ 2021 by Hilti Corporation - all rights reserved
 */

package com.am.translator;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ObjectTranslator {

  @Qualifier("alertModelMapper")
  private final ModelMapper modelMapper;

  @Autowired
  public ObjectTranslator(@Qualifier("alertModelMapper") ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public <T, R> R translate(T source, Class<R> targetType) {
    return modelMapper.map(source, targetType);
  }

  public <T, R> List<R> translateToList(List<T> entities, Class<R> targetType) {
    return entities.stream()
        .map(source -> modelMapper.map(source, targetType))
        .collect(Collectors.toList());
  }

  public <T, R> void translate(T source, R target) {
    modelMapper.map(source, target);
  }
}
