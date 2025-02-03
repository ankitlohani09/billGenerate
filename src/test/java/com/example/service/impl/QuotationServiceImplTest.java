package com.example.service.impl;

import com.example.entity.Quotation;
import com.example.repository.QuotationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class QuotationServiceImplTest {

    @Mock
    private QuotationRepository quotationRepository;

    @InjectMocks
    private QuotationServiceImpl quotationService;

    private Quotation quotation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // initialize mocks
        quotation = new Quotation();
        quotation.setId(1L);
        quotation.setName("Test Quotation");
        quotation.setMobileNo("1234567890");
        quotation.setDate(String.valueOf(LocalDate.now()));
        quotation.setAddress("Test Address");
    }

    @Test
    void testGetAllQuotations() {
        // Test if all quotations are fetched correctly
        when(quotationRepository.findAll()).thenReturn(List.of(quotation));

        var quotations = quotationService.getAllQuotations();

        assertEquals(1, quotations.size());
        verify(quotationRepository, times(1)).findAll();
    }

    @Test
    void testSaveQuotation() {
        // Test saving a quotation
        when(quotationRepository.save(any(Quotation.class))).thenReturn(quotation);

        quotationService.saveQuotation(quotation);

        verify(quotationRepository, times(1)).save(any(Quotation.class));
    }

    @Test
    void testFindQuotationById() {
        // Test finding a quotation by ID
        when(quotationRepository.findById(1L)).thenReturn(Optional.of(quotation));

        var foundQuotation = quotationService.findQuotationById(1L);

        assertNotNull(foundQuotation);
        assertEquals(quotation.getId(), foundQuotation.getId());
        verify(quotationRepository, times(1)).findById(1L);
    }

    @Test
    void testFindQuotationByIdNotFound() {
        // Test when quotation is not found
        when(quotationRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            quotationService.findQuotationById(1L);
        });

        assertEquals("Quotation not found", thrown.getMessage());
    }

    @Test
    void testUpdateQuotation() {
        // Test updating an existing quotation
        when(quotationRepository.findById(1L)).thenReturn(Optional.of(quotation));
        when(quotationRepository.save(any(Quotation.class))).thenReturn(quotation);

        quotation.setName("Updated Quotation");

        quotationService.updateQuotation(1L, quotation);

        assertEquals("Updated Quotation", quotation.getName());
        verify(quotationRepository, times(1)).save(any(Quotation.class));
    }

    @Test
    void testDeleteQuotation() {
        // Test deleting a quotation
        doNothing().when(quotationRepository).deleteById(1L);

        quotationService.deleteQuotation(1L);

        verify(quotationRepository, times(1)).deleteById(1L);
    }
}
