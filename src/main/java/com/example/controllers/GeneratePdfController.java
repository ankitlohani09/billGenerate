package com.example.controllers;

import com.example.entity.Admin;
import com.example.entity.Quotation;
import com.example.service.impl.AdminServiceImpl;
import com.example.service.impl.GeneratePdfServiceImpl;
import com.example.service.impl.LogoServiceImpl;
import com.example.service.impl.QuotationServiceImpl;
import com.itextpdf.text.DocumentException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/generatePdf")
public class GeneratePdfController {

    private final GeneratePdfServiceImpl generatePdfService;

    private final QuotationServiceImpl quotationServiceImpl;
    private final AdminServiceImpl adminServiceImpl;

    public GeneratePdfController(GeneratePdfServiceImpl generatePdfService, QuotationServiceImpl quotationServiceImpl, AdminServiceImpl adminServiceImpl) {
        this.generatePdfService = generatePdfService;
        this.quotationServiceImpl = quotationServiceImpl;
        this.adminServiceImpl = adminServiceImpl;
    }

    // Generate PDF for a quotation
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> generateQuotationPdf(@PathVariable Long id) {
        try {
            byte[] pdfBytes = generatePdfService.generatePdfForQuotation(id);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=quotation.pdf");
            headers.add("Content-Type", "application/pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        } catch (DocumentException e) {
            throw new RuntimeException("Error generating PDF", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/server2/{id}")
    public String generatePdfViaServer2(@PathVariable Long id, Model model) {
        // Fetch quotation data from service or database
        Quotation quotation = fetchQuotationData(id);
        Admin admin = adminServiceImpl.getAdminDetails(1L);

        // Add dynamic data to the model
        model.addAttribute("quotation", quotation);
        model.addAttribute("admin", admin);
        model.addAttribute("logoUrl", LogoServiceImpl.S3_LOGO_URL);
        model.addAttribute("hotelUrl", LogoServiceImpl.S3_DUMMY_HOTEL_IMG_SRC);

        // Return the Thymeleaf template name
        return "generate-pdf";
    }

    private Quotation fetchQuotationData(Long id) {
        return quotationServiceImpl.findQuotationById(id);
    }
}
