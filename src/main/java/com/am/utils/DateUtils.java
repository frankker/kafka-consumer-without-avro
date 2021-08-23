/*
 * Copyright @ 2021 by Hilti Corporation - all rights reserved
 */

package com.am.utils;

public class DateUtils {

  private DateUtils() {}

  public static class InvalidDateFormatException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidDateFormatException(String message) {
      super(message);
    }
  }
}
