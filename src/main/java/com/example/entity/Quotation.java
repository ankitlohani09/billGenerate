package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quotation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String mobileNo;
    private String date;
    private String address;
    private String subject;
    private String thankYou;
    private String onOffGrid;

    private String proposedSiteName;
    private String location;

    private String plantCapacity;
    private String moduleTechnology;
    private String mountingStructureTechnology;
    private String projectScheme;
    private String powerEvacuation;
    private String solarPlantOutputConnection;
    private String approxArea;
    private String scheme;

    private String inquiryReceivedFrom;
    private LocalDate proposalBasesInquiryReceivedDate;
    private String offerValidity;

    private String solarPVModulesQty;
    private String solarInverterQty;
    private String mountingStructureQty;
    private String acCablesQty;
    private String dcCablesQty;
    private String distributionBoxesDcQty;
    private String distributionBoxesAcQty;
    private String earthlingQty;
    private String systemMonitoringQty;
    private String mc4ConnectorsQty;
    private String switchGearsQty;
    private String balanceOfSystemQty;
    private String netMeterQty;

    private String plantSize;
    private String totalAmountPayable;
    private String directSubsidyBenefit;

    private String solarPVModulesMake;
    private String solarInverterMake;
    private String otherBrand;
}

