package com.example.controllers;

import com.example.entity.Quotation;
import com.example.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.example.controllers.HomeController.*;

@Controller
@RequestMapping("/quotation")
public class QuotationController {

    private final QuotationService quotationService;

    @Autowired
    public QuotationController(QuotationService quotationService) {
        this.quotationService = quotationService;
    }

    @GetMapping("/getAll")
    public String getAllQuotation(Model model) {
        model.addAttribute("quotations", quotationService.getAllQuotations());
        return "view-all-quotation";
    }

    // Show form to add a new quotation
    @GetMapping("/showCreateForm")
    public String showCreateQuotationForm(Model model) {
        Quotation quotation = new Quotation();
        model.addAttribute("quotation", quotation);
        model.addAttribute("ownerName", OWNER_NAME);
        model.addAttribute("currentDate", LocalDate.now());
        model.addAttribute("offerValidity", OFFER_VALIDITY);
        return "create-quotation";
    }

    // Add a new quotation
    @PostMapping("/create")
    public String addQuotation(@ModelAttribute Quotation quotation) {
        quotationService.saveQuotation(quotation);
        return "redirect:/quotation/view/" + quotation.getId();
    }

    // Show form to edit a quotation
    @GetMapping("/edit/{id}")
    public String showEditQuotationForm(@PathVariable Long id, Model model) {
        Quotation quotation = quotationService.findQuotationById(id);
        model.addAttribute("quotation", quotation);
        model.addAttribute("ownerName", OWNER_NAME);
        model.addAttribute("currentDate", LocalDate.now());
        model.addAttribute("offerValidity", OFFER_VALIDITY);
        return "edit-quotation";
    }

    // Update a quotation
    @PostMapping("/update/{id}")
    public String updateQuotation(@PathVariable Long id, @ModelAttribute Quotation quotation) {
        quotationService.updateQuotation(id, quotation);
        return "redirect:/quotation/getAll";
    }

    // Delete a specific quotation
    @GetMapping("/delete/{id}")
    public String deleteQuotation(@PathVariable Long id) {
        quotationService.deleteQuotation(id);
        return "redirect:/quotation/getAll";
    }

    // View a specific quotation
    @GetMapping("/view/{id}")
    public String viewQuotation(@PathVariable Long id, Model model) {
        Quotation quotation = quotationService.findQuotationById(id);



        model.addAttribute("ownerName", OWNER_NAME);
        model.addAttribute("offerValidity", OFFER_VALIDITY);
        model.addAttribute("quotation", quotation);
        return "view-quotation";
    }

}
