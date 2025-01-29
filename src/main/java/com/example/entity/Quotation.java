package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getThankYou() {
        return thankYou;
    }

    public void setThankYou(String thankYou) {
        this.thankYou = thankYou;
    }

    public String getOnOffGrid() {
        return onOffGrid;
    }

    public void setOnOffGrid(String onOffGrid) {
        this.onOffGrid = onOffGrid;
    }

    public String getProposedSiteName() {
        return proposedSiteName;
    }

    public void setProposedSiteName(String proposedSiteName) {
        this.proposedSiteName = proposedSiteName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPlantCapacity() {
        return plantCapacity;
    }

    public void setPlantCapacity(String plantCapacity) {
        this.plantCapacity = plantCapacity;
    }

    public String getModuleTechnology() {
        return moduleTechnology;
    }

    public void setModuleTechnology(String moduleTechnology) {
        this.moduleTechnology = moduleTechnology;
    }

    public String getMountingStructureTechnology() {
        return mountingStructureTechnology;
    }

    public void setMountingStructureTechnology(String mountingStructureTechnology) {
        this.mountingStructureTechnology = mountingStructureTechnology;
    }

    public String getProjectScheme() {
        return projectScheme;
    }

    public void setProjectScheme(String projectScheme) {
        this.projectScheme = projectScheme;
    }

    public String getPowerEvacuation() {
        return powerEvacuation;
    }

    public void setPowerEvacuation(String powerEvacuation) {
        this.powerEvacuation = powerEvacuation;
    }

    public String getSolarPlantOutputConnection() {
        return solarPlantOutputConnection;
    }

    public void setSolarPlantOutputConnection(String solarPlantOutputConnection) {
        this.solarPlantOutputConnection = solarPlantOutputConnection;
    }

    public String getApproxArea() {
        return approxArea;
    }

    public void setApproxArea(String approxArea) {
        this.approxArea = approxArea;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getInquiryReceivedFrom() {
        return inquiryReceivedFrom;
    }

    public void setInquiryReceivedFrom(String inquiryReceivedFrom) {
        this.inquiryReceivedFrom = inquiryReceivedFrom;
    }

    public LocalDate getProposalBasesInquiryReceivedDate() {
        return proposalBasesInquiryReceivedDate;
    }

    public void setProposalBasesInquiryReceivedDate(LocalDate proposalBasesInquiryReceivedDate) {
        this.proposalBasesInquiryReceivedDate = proposalBasesInquiryReceivedDate;
    }

    public String getOfferValidity() {
        return offerValidity;
    }

    public void setOfferValidity(String offerValidity) {
        this.offerValidity = offerValidity;
    }

    public String getSolarPVModulesQty() {
        return solarPVModulesQty;
    }

    public void setSolarPVModulesQty(String solarPVModulesQty) {
        this.solarPVModulesQty = solarPVModulesQty;
    }

    public String getSolarInverterQty() {
        return solarInverterQty;
    }

    public void setSolarInverterQty(String solarInverterQty) {
        this.solarInverterQty = solarInverterQty;
    }

    public String getMountingStructureQty() {
        return mountingStructureQty;
    }

    public void setMountingStructureQty(String mountingStructureQty) {
        this.mountingStructureQty = mountingStructureQty;
    }

    public String getAcCablesQty() {
        return acCablesQty;
    }

    public void setAcCablesQty(String acCablesQty) {
        this.acCablesQty = acCablesQty;
    }

    public String getDcCablesQty() {
        return dcCablesQty;
    }

    public void setDcCablesQty(String dcCablesQty) {
        this.dcCablesQty = dcCablesQty;
    }

    public String getDistributionBoxesDcQty() {
        return distributionBoxesDcQty;
    }

    public void setDistributionBoxesDcQty(String distributionBoxesDcQty) {
        this.distributionBoxesDcQty = distributionBoxesDcQty;
    }

    public String getDistributionBoxesAcQty() {
        return distributionBoxesAcQty;
    }

    public void setDistributionBoxesAcQty(String distributionBoxesAcQty) {
        this.distributionBoxesAcQty = distributionBoxesAcQty;
    }

    public String getEarthlingQty() {
        return earthlingQty;
    }

    public void setEarthlingQty(String earthlingQty) {
        this.earthlingQty = earthlingQty;
    }

    public String getSystemMonitoringQty() {
        return systemMonitoringQty;
    }

    public void setSystemMonitoringQty(String systemMonitoringQty) {
        this.systemMonitoringQty = systemMonitoringQty;
    }

    public String getMc4ConnectorsQty() {
        return mc4ConnectorsQty;
    }

    public void setMc4ConnectorsQty(String mc4ConnectorsQty) {
        this.mc4ConnectorsQty = mc4ConnectorsQty;
    }

    public String getSwitchGearsQty() {
        return switchGearsQty;
    }

    public void setSwitchGearsQty(String switchGearsQty) {
        this.switchGearsQty = switchGearsQty;
    }

    public String getBalanceOfSystemQty() {
        return balanceOfSystemQty;
    }

    public void setBalanceOfSystemQty(String balanceOfSystemQty) {
        this.balanceOfSystemQty = balanceOfSystemQty;
    }

    public String getNetMeterQty() {
        return netMeterQty;
    }

    public void setNetMeterQty(String netMeterQty) {
        this.netMeterQty = netMeterQty;
    }

    public String getPlantSize() {
        return plantSize;
    }

    public void setPlantSize(String plantSize) {
        this.plantSize = plantSize;
    }

    public String getTotalAmountPayable() {
        return totalAmountPayable;
    }

    public void setTotalAmountPayable(String totalAmountPayable) {
        this.totalAmountPayable = totalAmountPayable;
    }

    public String getDirectSubsidyBenefit() {
        return directSubsidyBenefit;
    }

    public void setDirectSubsidyBenefit(String directSubsidyBenefit) {
        this.directSubsidyBenefit = directSubsidyBenefit;
    }

    public String getSolarPVModulesMake() {
        return solarPVModulesMake;
    }

    public void setSolarPVModulesMake(String solarPVModulesMake) {
        this.solarPVModulesMake = solarPVModulesMake;
    }

    public String getSolarInverterMake() {
        return solarInverterMake;
    }

    public void setSolarInverterMake(String solarInverterMake) {
        this.solarInverterMake = solarInverterMake;
    }

    public String getOtherBrand() {
        return otherBrand;
    }

    public void setOtherBrand(String otherBrand) {
        this.otherBrand = otherBrand;
    }
}

