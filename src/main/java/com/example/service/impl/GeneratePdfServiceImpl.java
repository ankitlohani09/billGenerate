package com.example.service.impl;

import com.example.entity.Admin;
import com.example.entity.Quotation;
import com.example.repository.QuotationRepository;
import com.example.service.GeneratePdfService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import jakarta.servlet.ServletContext;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.util.Optional;

@Service
public class GeneratePdfServiceImpl implements GeneratePdfService {

    private final QuotationRepository quotationRepository;

    private final ServletContext servletContext;

    private final AdminServiceImpl adminServiceImpl;

    public GeneratePdfServiceImpl(QuotationRepository quotationRepository, ServletContext servletContext, AdminServiceImpl adminServiceImpl) {
        this.quotationRepository = quotationRepository;
        this.servletContext = servletContext;
        this.adminServiceImpl = adminServiceImpl;
    }

    private void addImageFromUrl(Document document, String imageUrl, float width, float height, int alignment) throws IOException, DocumentException {
        try (InputStream inputStream = new URL(imageUrl).openStream()) {
            Image image = Image.getInstance(IOUtils.toByteArray(inputStream));
            image.scaleToFit(width, height); // Scale image to the given dimensions
            image.setAlignment(alignment); // Align image as specified
            document.add(image); // Add image to the document
        }
    }

    public byte[] generatePdfForQuotation(Long quotationId) throws DocumentException, IOException {
        Optional<Quotation> quotationOptional = quotationRepository.findById(quotationId);
        Quotation quotation = new Quotation();

        if (quotationOptional.isPresent()) {
            quotation = quotationOptional.get();
        }

        // PDF Setup
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        BaseColor darkBlueColour = new BaseColor(34, 9, 86);
        // Custom Fonts
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, darkBlueColour);
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
        Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
        Font smallerFont = new Font(normalFont.getBaseFont(), 8, Font.BOLD);

        // Add Logo
        if (!LogoServiceImpl.S3_LOGO_URL.isEmpty()) {
            addImageFromUrl(document, LogoServiceImpl.S3_LOGO_URL, 120, 120, Element.ALIGN_RIGHT);
        } else {
            addImageFromClasspath(document, LogoServiceImpl.LOCAL_LOGO_PATH, 120, 120, Element.ALIGN_RIGHT);
        }

        // Title Section
        addParagraph(document, "RADHIKA ENTERPRISES", titleFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "HEADING TOWARDS HEALTHIER EARTH", boldFont, Element.ALIGN_LEFT, 20);

        // Add Hotel Image
        addImageFromClasspath(document, "static/image/DummyHotelImg.png", 390, 300, Element.ALIGN_CENTER);
        addMultipleNewLines(document, 2);

        Admin adminDetail = adminServiceImpl.getAdminDetails(1L);

        // Contact Information
        addParagraph(document,"Contact : " + adminDetail.getOwnerContactNo() + "\n" +
                "E-Mail :-\n" +
                 adminDetail.getOwnerEmail() + " \n" +
                "Address : " + adminDetail.getOwnerAddress() + " \n" +
                "GST Registration Number: 23APSPB8959G2ZG",
                boldFont, Element.ALIGN_LEFT, 15);

        addMultipleNewLines(document, 6);

        // Quotation Details Table
        Paragraph paragraph = new Paragraph("QUOTATION – " + quotationId, headerFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        addMultipleNewLines(document, 2);
        document.add(new Paragraph("NAME: " + quotation.getName(), boldFont));
        document.add(new Paragraph("MOB NO: " + quotation.getMobileNo(), boldFont));
        document.add(new Paragraph("DATE: " + quotation.getDate(), boldFont));
        document.add(new Paragraph("ADD: " + quotation.getAddress(), boldFont));

        // Adding the detailed text in the document
        addMultipleNewLines(document, 2);
        document.add(new Paragraph("Sub: Quotation for " + quotation.getPlantCapacity() + " " + quotation.getOnOffGrid() + " solar system on Erection procurement and commissioning basis", normalFont));
        document.add(Chunk.NEWLINE); // New line for readability
        addParagraph(document, "Thank you for selecting RADHIKA ENTERPRISE . Our company has provided ROOF TOP SOLAR SOLUTION and has built a reputation of integrity and efficiency.", normalFont, Element.ALIGN_LEFT, 0);
        addParagraph(document, "Our team has many years of experience and we are dedicated to providing you with the most up-to-date service possible.", normalFont, Element.ALIGN_LEFT, 0);
        addParagraph(document, "We are pleased to welcome you as a new RADHIKA ENTERPRISE family member. We feel honored that you have chosen us to fulfill your solar energy requirement, your complete satisfaction is our first priority! And look forward to serving you.", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "Project Description:", boldFont, Element.ALIGN_LEFT, 0); // Project Description heading
        addParagraph(document, "A grid-tied solar system is an interactive grid system where electricity generated by solar energy is used by the load and the excess energy is fed back to the grid.", normalFont, Element.ALIGN_LEFT, 0);
        addParagraph(document, "It uses a grid-tie interactive inverter that converts the direct current from the panels to alternating current to be used by the loads or transferred to the grid.", normalFont, Element.ALIGN_LEFT, 0);
        addParagraph(document, "This project will include the installation of the solar system consisting of rooftop mounting of Solar PV modules with mounting accessories, Net Meter, and MPPT solar grid-tie inverter.", normalFont, Element.ALIGN_LEFT, 0);

        addMultipleNewLines(document, 2);
        // Proposed Site Table
        PdfPTable proposedSiteTable = createTable(2);
        proposedSiteTable.setWidthPercentage(100);
        proposedSiteTable.setSpacingBefore(10f);

        // Add the header cell that spans two columns
        addColspanHeader(proposedSiteTable, "Proposed Site", boldFont);
        // Adding remaining cells with border directly
        addTableCellWithBorder(proposedSiteTable, "Name: ", boldFont, null);
        addTableCellWithBorder(proposedSiteTable, quotation.getProposedSiteName(), normalFont, null);
        addTableCellWithBorder(proposedSiteTable, "Location: ", boldFont, null);
        addTableCellWithBorder(proposedSiteTable, quotation.getLocation(), normalFont, null);
        document.add(proposedSiteTable);

        // Solar PV Specification Table
        PdfPTable solarTable = createTable(2);
        addColspanHeader(solarTable, "Solar PV System Specification: On-Grid Rooftop", boldFont);
        addTableCellWithBorder(solarTable, "Plant Capacity: ", boldFont, null);
        addTableCellWithBorder(solarTable, quotation.getPlantCapacity(), normalFont, null);
        addTableCellWithBorder(solarTable, "Module Technology: ", boldFont, null);
        addTableCellWithBorder(solarTable, quotation.getModuleTechnology(), normalFont, null);
        addTableCellWithBorder(solarTable, "Mounting Structure Technology: ", boldFont, null);
        addTableCellWithBorder(solarTable, quotation.getMountingStructureTechnology(), normalFont, null);
        addTableCellWithBorder(solarTable, "Project Scheme: ", boldFont, null);
        addTableCellWithBorder(solarTable, quotation.getProjectScheme(), normalFont, null);
        addTableCellWithBorder(solarTable, "Power Evacuation: ", boldFont, null);
        addTableCellWithBorder(solarTable, quotation.getPowerEvacuation(), normalFont, null);
        addTableCellWithBorder(solarTable, "Solar Plant Output Connection: ", boldFont, null);
        addTableCellWithBorder(solarTable, quotation.getSolarPlantOutputConnection(), normalFont, null);
        addTableCellWithBorder(solarTable, "Approx. Area: ", boldFont, null);
        addTableCellWithBorder(solarTable, quotation.getApproxArea(), normalFont, null);
        addTableCellWithBorder(solarTable, "Scheme: ", boldFont, null);
        addTableCellWithBorder(solarTable, quotation.getScheme(), normalFont, null);
        document.add(solarTable);

        // Proposal Bases Inquiry Table
        PdfPTable inquiryTable = createTable(2);
        addColspanHeader(inquiryTable, "Proposal Bases Inquiry Received", boldFont);
        addTableCellWithBorder(inquiryTable, "From: ", boldFont, null);
        addTableCellWithBorder(inquiryTable, quotation.getInquiryReceivedFrom(), normalFont, null);
        addTableCellWithBorder(inquiryTable, "Date: ", boldFont, null);
        addTableCellWithBorder(inquiryTable, String.valueOf(quotation.getProposalBasesInquiryReceivedDate()), normalFont, null);
        addTableCellWithBorder(inquiryTable, "Offer Validity: ", boldFont, null);
        addTableCellWithBorder(inquiryTable, quotation.getOfferValidity(), normalFont, null);
        document.add(inquiryTable);

        addMultipleNewLines(document, 1);

        addParagraph(document, "Technical Specifications and BOM FOR " + quotation.getPlantCapacity() + " SOLAR POWER PLANT:", boldFont, Element.ALIGN_CENTER, 15);
        // Technical Specifications Table
        PdfPTable technicalSpecificationsTable = createTable(5);
        technicalSpecificationsTable.setWidthPercentage(113);
        technicalSpecificationsTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        technicalSpecificationsTable.setPaddingTop(10);

        addTableCellWithBorder(technicalSpecificationsTable, "S No.", boldFont, BaseColor.LIGHT_GRAY);
        addTableCellWithBorder(technicalSpecificationsTable, "Component", boldFont, BaseColor.LIGHT_GRAY);
        addTableCellWithBorder(technicalSpecificationsTable, "Specification", boldFont, BaseColor.LIGHT_GRAY);
        addTableCellWithBorder(technicalSpecificationsTable, "Make", boldFont, BaseColor.LIGHT_GRAY);
        addTableCellWithBorder(technicalSpecificationsTable, "Quantity", boldFont, BaseColor.LIGHT_GRAY);

        // Define the data for rows
        String[][] data = {
                {"1", "Solar PV Module", "MONOPERC HALFCUT BIFACIAL PANEL 545WP " + quotation.getPlantCapacity(), quotation.getSolarPVModulesMake(), quotation.getSolarPVModulesQty()},
                {"2", "Solar Inverter", "On-grid type MPPT based solar inverter " + quotation.getPlantCapacity(), quotation.getSolarInverterMake(), quotation.getSolarInverterQty()},
                {"3", "Mounting Structure", "Mounting Structure 140*60 MM & 41*41 ","Galvanized", quotation.getMountingStructureQty()},
                {"4", "AC Cables", "AC side-AL. ARMORED/COPPER FLEXIBLE (AS PER SIDE CONDITION)", "Polycab/ISI", quotation.getAcCablesQty()},
                {"5", "DC Cables", "DC side copper", "Polycab/ISI", quotation.getDcCablesQty()},
                {"6", "Distribution Boxes (DC)", "DC distribution box with built-in SPD and DC Fuses IP65 protected", "HEVELLS & PHOENIX", quotation.getDistributionBoxesDcQty()},
                {"7", "Distribution Boxes (AC)", "AC Combiner box with built-in SPD and AC MCB/MCCB IP65 protected Spike type lighting arrestor, 1 MTR", "HEVELLS & PHOENIX", quotation.getDistributionBoxesAcQty()},
                {"8", "Earthing", "Copper bonded chemical earthings. Insulated copper conductor for earthing connection", "TRUE POWER", quotation.getEarthlingQty()},
                {"9", "System Monitoring", "Wi-Fi based Remote monitoring and weather monitoring system and data logger inbuilt in the inverter", "AS PER INVERTER", quotation.getSystemMonitoringQty()},
                {"10", "MC4 Connectors", "TUV approved, UV protected STD", "", quotation.getMc4ConnectorsQty()},
                {"11", "Switchgears", "MCBs, MCCBs, Isolators etc.", "HAVELLS/ABB", quotation.getSwitchGearsQty()},
                {"12", "Balance of System", "TUV approved PVC conduits, cable ties, electric tapes, enclosures etc.", "Polycab/Steel grip", quotation.getBalanceOfSystemQty()},
                {"13", "Net Meter", "Net meter and modem for MPEB", "SECURE/HPL", quotation.getNetMeterQty()}
        };
        // Adding rows to the table
        for (String[] row : data) {
            for (String cellData : row) {
                addTableCellWithBorder(technicalSpecificationsTable, cellData, smallerFont, null);
            }
        }

        document.add(technicalSpecificationsTable);

        addMultipleNewLines(document, 1);
        addParagraph(document, "As per your requirements we are sending you the quotation for ON-grid Solar Power Project.\n" +
                "Kindly find the details below:", normalFont, Element.ALIGN_LEFT, 10);

        PdfPTable priceTable = createTable(3);

        BaseColor lightOrange = new BaseColor(249,190,143);

        addTableCellWithBorder(priceTable, "PLANT SIZE", boldFont, lightOrange);
        addTableCellWithBorder(priceTable, "Total Amount Payable", boldFont, lightOrange);
        addTableCellWithBorder(priceTable, "Direct Subsidy Benefit in consumer account after commissioning of solar power plant:", boldFont, lightOrange);

        addTableCellWithBorder(priceTable, "PRICES " + quotation.getPlantCapacity() + " " + quotation.getOnOffGrid().toUpperCase() + " POWER PLANT", boldFont, null);
        addTableCellWithBorder(priceTable, quotation.getTotalAmountPayable(), boldFont, null);
        addTableCellWithBorder(priceTable, quotation.getDirectSubsidyBenefit(), boldFont, null);

        document.add(priceTable);

        addParagraph(document, "SUBSIDY AS PER GOVT. GUIDELINE (MNRE): RS . 78,000/- IN 45-90 DAYS OF COMPLETION", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "SUBSIDY WILL BE CREDITED TO CUSTOMER’S ACCOUNT", normalFont, Element.ALIGN_LEFT, 10);

        addMultipleNewLines(document, 1);

        addParagraph(document, "SCOPE OF WORK", boldFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "Solar Modules – as per IEC specs. Design, Manufacture & Supply of Solar power system as per specification & BOM given in our offer", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "Installation & Commissioning of Solar Power Plant up to LT Panel including Synchronization with Grid. SUPPLY AND CIVIL FOUNDATION WORK FOR MODULE MOUNTING STRUCTURE, IF ANY.", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "Project Management. Providing after-sales service during warranty period from the date of installation. To carry out all the processes as per MNRE guidelines. To undertake further AMC if required by the customer at mutually agreed cost.", normalFont, Element.ALIGN_LEFT, 10);

        addParagraph(document, "SCOPE OF SUPPLY (FACILITIES)", boldFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "There will be no open wire and all the electrical wires & cable will run through the conduits.", normalFont, Element.ALIGN_LEFT, 10);

        // Add GENERAL SAFETY, COMMUNICATION AND PROCESSES section
        addParagraph(document, "GENERAL SAFETY, COMMUNICATION AND PROCESSES:", boldFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "We will meet and adhere to meeting the General, Safety, Communication and Processes.", normalFont, Element.ALIGN_LEFT, 10);

        // Add SPECIFIC REQUIREMENTS section
        addParagraph(document, "SPECIFIC REQUIREMENTS – Compliance with Relevant Standards, Professional Accreditations:", boldFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "All systems, components and equipment will comply with, and be designed and installed in accordance with, all relevant MNRE guideline.", normalFont, Element.ALIGN_LEFT, 10);

        // Add MAINTENANCE section
        addParagraph(document, "MAINTENANCE:", boldFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "As such no major maintenance is required as there is no moving part. However, regular cleaning of the solar panels advised and general check of the inverters and other electric equipment’s is advised once a year. For 5 Years.", normalFont, Element.ALIGN_LEFT, 10);

        // Add Client Scope section
        addParagraph(document, "Client Scope:", boldFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "Providing shadow free area on roof/ground on roof.", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "Construction power, construction water etc.", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "Switch-yard and transformers and safety structures if needed.", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "Providing proper ventilated room for installation of PCU and other electronic items.", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "Providing safe storage place for material during installation commissioning period.", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "Providing water and temporary electricity connection during execution.", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "Load Increment if any.", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "Changing of service cable if demanded by MPEB officer.", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "Providing Wi-Fi range till inverter.", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "Expenses related to name change in electricity bill.", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "Any charges imposed by state dis com ISPL and client will discuss same and resolve it mutually.", normalFont, Element.ALIGN_LEFT, 10);

        // Add Terms and Conditions
        addParagraph(document, "Terms & Conditions:", boldFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "Payment Terms – 25% Advance against purchase order, 75% Before material dispatch.", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "Documents Required –", boldFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "Self-attested documents required as per below list \n", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "- Latest Electricity Bill", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "- Copy Pan Card (Clear)", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "- Aadhar Card", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "- Passport Size Photo", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "- Ownership Document / Registry (Nagar Nigam Property Tax Receipt)\n" +
                "SLD to be signed by customer (SLD will be provided by us) ", normalFont, Element.ALIGN_LEFT, 10);

        // Add Warranty details
        addParagraph(document, "Warranty:", boldFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "Solar Modules: 25 YEARS AS PROVIDED BY THE MANUFACTURER", boldFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "Solar Inverter: 8/10 YEARS AS PROVIDED BY THE MANUFACTURER", boldFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "Force Majeure Offer is subject to force majeure by which it means cause beyond our reasonable control such as " +
                "war invasion, civil disobedience, government orders or restrictions, strikes, lockouts, riots, fire, materials wagons, " +
                "shipping space or any other earthquakes, floods, accident, breakdown of machinery, delay or inability to obtain " +
                "labor, raw material, causes whatsoever beyond our reasonable control, affecting us or our subcontractors, " +
                "suppliers. ", normalFont, Element.ALIGN_LEFT, 10);

        addMultipleNewLines(document,2);
        // Add Bank Details
        addParagraph(document, "“ RADHIKA ENTERPRISES ”", boldFont, Element.ALIGN_CENTER, 10);
        addParagraph(document, "ACCOUNT DETAILS -", boldFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "A/C HOLDER NAME: RADHIKA ENTERPRISES", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "BANK NAME – KOTAK MAHINDRA BANK", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "A/C No. 8435555882", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "IFSC CODE – KKBK0005915", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "Entity CRN - 932828882", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "BRANCH – Vijay Nagar", normalFont, Element.ALIGN_LEFT, 10);
        addParagraph(document, "INDORE 452010", normalFont, Element.ALIGN_LEFT, 20);

        addMultipleNewLines(document,3);
        // Add Contact Details
        addParagraph(document, "RADHIKA ENTERPRISES", boldFont, Element.ALIGN_CENTER, 0);
        addParagraph(document, "GSTIN 23APSPB8959G2ZG", normalFont, Element.ALIGN_CENTER, 20);
        addMultipleNewLines(document, 1);

        document.close();
        return outputStream.toByteArray();
    }

    private void addParagraph(Document document, String text, Font font, int alignment, float spacingAfter) throws DocumentException {
        Paragraph paragraph;

        // Agar "RADHIKA ENTERPRISE" ya "RADHIKA ENTERPRISE family member" text ho toh bold karna
        if (text.contains("Thank you for selecting RADHIKA ENTERPRISE") || text.contains("RADHIKA ENTERPRISE family member")) {
            Font boldFont = new Font(font.getFamily(), font.getSize(), Font.BOLD);
            paragraph = new Paragraph();
            paragraph.setAlignment(alignment);
            paragraph.setSpacingAfter(spacingAfter);

            // "RADHIKA ENTERPRISE" ko bold karna
            String[] parts = text.split("RADHIKA ENTERPRISE");
            paragraph.add(new Chunk(parts[0], font)); // Normal text
            paragraph.add(new Chunk("RADHIKA ENTERPRISE", boldFont)); // Bold text
            if (parts.length > 1) {
                paragraph.add(new Chunk(parts[1], font)); // Remaining text
            }
        }
        // Agar "HEADING TOWARDS HEALTHIER EARTH" text ho toh dark green color apply karna
        else if (text.equals("HEADING TOWARDS HEALTHIER EARTH")) {
            Font coloredFont = new Font(font.getFamily(), font.getSize(), font.getStyle(), new BaseColor(85, 107, 47));
            paragraph = new Paragraph();
            paragraph.add(new Chunk(text, coloredFont));
            paragraph.setAlignment(alignment);
            paragraph.setSpacingAfter(spacingAfter);
        }
        else {
            paragraph = new Paragraph(text, font);
            paragraph.setAlignment(alignment);
            paragraph.setSpacingAfter(spacingAfter);
        }

        document.add(paragraph); // Paragraph ko document me add karein
    }

    private void addImageFromClasspath(Document document, String path, float width, float height, int alignment) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);

        if (inputStream == null) {
            String realPath = servletContext.getRealPath(path);
            if (realPath != null) {
                realPath = realPath.replaceFirst("static\\\\static", "static");
                File file = new File(realPath);
                if (file.exists()) {
                    try {
                        inputStream = new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException("File not found: " + realPath, e);
                    }
                }
            }
            if (inputStream == null) {
                throw new RuntimeException("File not found: " + path);
            }
        }
        try (InputStream inputStreamFinal = inputStream) {
            Image image = Image.getInstance(ImageIO.read(inputStreamFinal), null);
            image.scaleAbsolute(width, height);
            image.setAlignment(alignment);
            document.add(image);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while adding image to PDF: " + e.getMessage(), e);
        }
    }

    private PdfPTable createTable(int numColumns) {
        PdfPTable table = new PdfPTable(numColumns);
        table.setWidthPercentage(100);
        table.setPaddingTop(10);
        return table;
    }

    private void addColspanHeader(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setColspan(table.getNumberOfColumns());
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(10);
        table.addCell(cell);
    }

    private void addTableCellWithBorder(PdfPTable table, String text, Font font, BaseColor backgroundColor) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.BOX);
        cell.setPadding(10);
        if (backgroundColor != null) {
            cell.setBackgroundColor(backgroundColor);  // Apply background color if provided
        }
        table.addCell(cell);
        table.setSpacingAfter(15);
    }

    private void addMultipleNewLines(Document document, int count) throws DocumentException {
        for (int i = 0; i < count; i++) {
            document.add(new Paragraph(" "));
        }
    }
}
