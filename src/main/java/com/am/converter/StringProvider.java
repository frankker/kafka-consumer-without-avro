/*
 * Copyright @ 2021 by Hilti Corporation - all rights reserved
 */

package com.am.converter;

import org.modelmapper.AbstractProvider;

public class StringProvider extends AbstractProvider<String> {
  @Override
  public String get() {
    return "";
  }
}
