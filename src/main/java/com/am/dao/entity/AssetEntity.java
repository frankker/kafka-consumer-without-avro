package com.am.dao.entity;

import com.am.constants.AssetStatusEnum;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "asset")
@Getter
@Setter
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class AssetEntity {

  private static final long serialVersionUID = -4992045498634670360L;

  @Id
  @Column(name = "asset_id")
  private Long assetId;

  @Column(name = "tenant_id")
  private Long tenantId;

  @Column(name = "responsible_employee_id")
  private Long responsibleEmployeeId;

  @Column(name = "responsible_employee_name")
  private String responsibleEmployeeName;

  @Column(name = "name")
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "asset_status")
  private AssetStatusEnum assetStatus;

  @Column(name = "asset_type_code")
  private String assetTypeCode;

  @Column(name = "parent_id")
  private Long parentId;

  @Column(name = "material_number")
  private String materialNumber;

  @Column(name = "hilti_integrated_asset")
  private Boolean hiltiIntegratedAsset;

  @Column(name = "default_location_id")
  private Long defaultLocationId;

  @Column(name = "current_location_id")
  private Long currentLocationId;

  @Column(name = "scan_code_type_code")
  private String scanCodeType;

  @Column(name = "scan_code")
  private String scanCode;

  @Column(name = "manufacturer_name")
  private String manufacturerName;

  @Column(name = "model")
  private String model;

  @Column(name = "storage_location")
  private String storageLocation;

  @Column(name = "default_location_name")
  private String defaultLocationName;

  @Column(name = "current_location_name")
  private String currentLocationName;

  @Column(name = "loan_end_date")
  private String loanEndDate;

  @Column(name = "rental_return_date")
  private String rentalReturnDate;

  @Column(name = "fleet_exchange_or_warranty_date")
  private String fleetExchangeOrWarrantyDate;

  @Column(name = "fleet_inventory_number")
  private String fleetInventoryNumber;

  @Column(name = "warranty_expiration_date")
  private String warrantyExpirationDate;

  @Column(name = "tool_id")
  private Long toolId;

  @Column(name = "ownership_type_code")
  private String ownerShipTypeCode;

  @Column(name = "serial_number")
  private String serialNumber;

  @Column(name = "fleet_organization_reference_number")
  private String fleetOrganizationReferenceNumber;

  @Column(name = "purchase_date")
  private String purchaseDate;

  @Column(name = "inventory_number")
  private String inventoryNumber;

  @Column(name = "owner_id")
  private Long ownerId;

  @Column(name = "owner_name")
  private String ownerName;

  @Column(name = "description")
  private String description;

  @Column(name = "rental_tool_claim")
  private String rentalToolClaim;

  @Column(name = "purchase_order_number")
  private String purchaseOrderNumber;

  @Column(name = "previous_owner_id")
  private Long previousOwnerId;

  @Column(name = "origin_equipment_number")
  private String originEquipmentNumber;

  @Column(name = "version")
  private Long version;

  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "created_by", updatable = false)
  @CreatedBy
  private String createdBy;

  @LastModifiedBy
  @Column(name = "updated_by")
  private String updatedBy;

  @PrePersist
  public void onPrePersist() {
    this.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));
    this.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));
  }

  @PreUpdate
  public void onPreUpdate() {
    this.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));
  }
}
