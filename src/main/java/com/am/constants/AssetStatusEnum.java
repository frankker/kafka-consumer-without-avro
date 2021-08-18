/*
 * Copyright @ 2021 by Hilti Corporation - all rights reserved
 */

package com.am.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AssetStatusEnum {
  OPR("Operational", "OPR"),
  BRK("Broken", "BRK"),
  INREP("In Repair", "INREP"),
  RETR("Retired", "RETR"),
  LSTORSTOL("Lost or Stolen", "LSTORSTOL"),
  DISPOSED("Disposed", "DISPOSED");

  private String statusName;
  private String statusCode;
}
