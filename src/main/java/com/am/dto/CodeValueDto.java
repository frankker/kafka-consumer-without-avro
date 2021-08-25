/*
 * Copyright @ 2021 by Hilti Corporation - all rights reserved
 */

package com.am.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CodeValueDto implements Serializable {

  private static final long serialVersionUID = 2412608425101241803L;

  private String code;

  private String value;
}
