package com.example.service.impl;

import com.example.entity.Quotation;
import com.example.repository.QuotationRepository;
import com.example.service.QuotationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        quotationRepository.save(quotation);
    }

    public Quotation findQuotationById(Long id) {
        Optional<Quotation> quotation = quotationRepository.findById(id);
        return quotation.orElseThrow(()-> new RuntimeException("Quotation not found"));
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
