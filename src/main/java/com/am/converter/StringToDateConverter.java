/*
 * Copyright @ 2021 by Hilti Corporation - all rights reserved
 */

package com.am.converter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Date;
import org.modelmapper.AbstractConverter;

public class StringToDateConverter extends AbstractConverter<String, Date> {

  private static final String DATE_FORMAT_3MICROSECONDS = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
  private static final String DATE_FORMAT_6MICROSECONDS = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX";
  private static final String DATE_FORMAT_7MICROSECONDS = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSX";
  private static final String DATE_FORMAT_WITHOUT_MICROSECONDS = "yyyy-MM-dd'T'HH:mm:ssX";

  private static final DateTimeFormatter formatter =
      new DateTimeFormatterBuilder()
          .appendOptional(DateTimeFormatter.ofPattern(DATE_FORMAT_3MICROSECONDS))
          .appendOptional(DateTimeFormatter.ofPattern(DATE_FORMAT_6MICROSECONDS))
          .appendOptional(DateTimeFormatter.ofPattern(DATE_FORMAT_7MICROSECONDS))
          .appendOptional(DateTimeFormatter.ofPattern(DATE_FORMAT_WITHOUT_MICROSECONDS))
          .toFormatter();

  @Override
  protected Date convert(String source) {
    if (source == null) {
      return null;
    }

    try {
      LocalDateTime parsedDate = LocalDateTime.parse(source, formatter);
      return Date.from(parsedDate.atZone(ZoneId.systemDefault()).toInstant());
    } catch (DateTimeParseException ex) {
      throw ex;
    }
  }
}
