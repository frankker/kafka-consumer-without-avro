package com.am.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetDto {
  private Long assetId;
  private Long tenantId;
  private Long defaultLocationId;
  private Long currentLocationId;
  private CodeValueDto scanCodeTypeDetails;
  private String scanCode;
  private String inventoryNumber;

  private Long responsibleEmployeeId;

  private String responsibleEmployeeName;

  private String manufacturerName;
  private String model;

  private String name;

  private CodeValueDto assetStatusResponse;

  private String storageLocation;

  private String defaultLocationName;

  private String currentLocationName;

  private CodeValueDto assetType;
  private Long parentId;
  private String materialNumber;
  private Boolean hiltiIntegratedAsset;

  // loaned
  private LocalDate loanEndDate;

  // Rented
  private LocalDate rentalReturnDate;

  // Fleet info
  private LocalDate fleetExchangeOrWarrantyDate;
  private String fleetInventoryNumber;

  // warranty
  private LocalDate warrantyExpirationDate;

  // PBE
  private Long toolId;
  private CodeValueDto ownerShipTypeResponse;
  private String serialNumber;
  private String fleetOrganizationReferenceNumber;
  private LocalDate purchaseDate;
  private String scanCodeType;
  private Long version;
  private boolean isChildAsset;
  private Long ownerId;
  private String ownerName;
  private String description;
  private String rentalToolClaim;
  private String purchaseOrderNumber;
  private Long previousOwnerId;
  private String originEquipmentNumber;

  //  public Long getId() {
  //    return getAssetId();
  //  }
}
