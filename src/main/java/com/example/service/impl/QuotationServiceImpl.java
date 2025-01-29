package com.example.service.impl;

import com.example.entity.Quotation;
import com.example.repository.QuotationRepository;
import com.example.service.QuotationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.controllers.HomeController.OFFER_VALIDITY;
import static com.example.controllers.HomeController.OWNER_NAME;

@Service
public class QuotationServiceImpl implements QuotationService {

    private final QuotationRepository quotationRepository;

    public QuotationServiceImpl(QuotationRepository quotationRepository) {
        this.quotationRepository = quotationRepository;
    }

    public List<Quotation> getAllQuotations() {
        return quotationRepository.findAll();
    }

    public void saveQuotation(Quotation quotation) {
        quotation.setSubject("Quotation for " + quotation.getPlantCapacity() + quotation.getOnOffGrid()
                + " solar system on Erection procurement and commissioning basis");
        quotation.setModuleTechnology("144cell DCR Monoperc  Half cut Bifacial");
        quotation.setMountingStructureTechnology("Fixed Tilt");
        quotation.setProjectScheme("Turnkey Rooftop EPC");
        quotation.setPowerEvacuation("220V - 320V");
        quotation.setSolarPlantOutputConnection("To existing LT connection");
        quotation.setApproxArea(quotation.getApproxArea() +" sq. Feet Required");
        quotation.setScheme("Captive - Net Metering");

        quotation.setInquiryReceivedFrom(OWNER_NAME);
        quotation.setProposalBasesInquiryReceivedDate(LocalDate.now());
        quotation.setOfferValidity(OFFER_VALIDITY);

        quotation.setProposalBasesInquiryReceivedDate(LocalDate.now());
        quotationRepository.save(quotation);
    }

    public Quotation findQuotationById(Long id) {
        Optional<Quotation> quotation = quotationRepository.findById(id);
        return quotation.orElseThrow(() -> new RuntimeException("Quotation not found"));
    }

    public void updateQuotation(Long id, Quotation quotation) {
        Quotation existQuotation = findQuotationById(id);
        if (existQuotation != null) {
            existQuotation.setName(quotation.getName());
            existQuotation.setMobileNo(quotation.getMobileNo());
            existQuotation.setDate(quotation.getDate());
            existQuotation.setAddress(quotation.getAddress());
            existQuotation.setSubject(quotation.getSubject());
            existQuotation.setThankYou(quotation.getThankYou());

            existQuotation.setProposedSiteName(quotation.getProposedSiteName());
            existQuotation.setLocation(quotation.getLocation());

            existQuotation.setPlantCapacity(quotation.getPlantCapacity());
            existQuotation.setModuleTechnology(quotation.getModuleTechnology());
            existQuotation.setMountingStructureTechnology(quotation.getMountingStructureTechnology());
            existQuotation.setProjectScheme(quotation.getProjectScheme());
            existQuotation.setPowerEvacuation(quotation.getPowerEvacuation());
            existQuotation.setSolarPlantOutputConnection(quotation.getSolarPlantOutputConnection());
            existQuotation.setApproxArea(quotation.getApproxArea());
            existQuotation.setScheme(quotation.getScheme());

            existQuotation.setInquiryReceivedFrom(quotation.getInquiryReceivedFrom());
            existQuotation.setProposalBasesInquiryReceivedDate(quotation.getProposalBasesInquiryReceivedDate());
            existQuotation.setOfferValidity(quotation.getOfferValidity());
        } else {
            throw new RuntimeException("Unable to find quotation with id " + id);
        }
        quotationRepository.save(existQuotation);
    }

    public void deleteQuotation(Long id) {
        quotationRepository.deleteById(id);
    }
}
