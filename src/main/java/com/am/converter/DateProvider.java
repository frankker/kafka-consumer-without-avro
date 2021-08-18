/*
 * Copyright @ 2021 by Hilti Corporation - all rights reserved
 */

package com.am.converter;

import java.util.Date;
import org.modelmapper.AbstractProvider;

public class DateProvider extends AbstractProvider<Date> {
  @Override
  public Date get() {
    return new Date();
  }
}
