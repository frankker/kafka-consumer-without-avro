/*
 * Copyright @ 2021 by Hilti Corporation - all rights reserved
 */

package com.am.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.modelmapper.AbstractConverter;

public class DateToStringConverter extends AbstractConverter<Date, String> {

  private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
  private final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);

  @Override
  protected String convert(Date source) {
    if (source == null) {
      return null;
    }
    return dateFormat.format(source);
  }
}
