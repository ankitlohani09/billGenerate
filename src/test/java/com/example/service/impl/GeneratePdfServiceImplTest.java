//package com.example.service.impl;
//
//import com.example.repository.QuotationRepository;
//import com.example.service.impl.AdminServiceImpl;
//import com.example.service.impl.GeneratePdfServiceImpl;
//import com.itextpdf.text.*;
//import jakarta.servlet.ServletContext;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.*;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.io.File;
//import java.lang.reflect.Method;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({GeneratePdfServiceImpl.class, File.class})
//public class GeneratePdfServiceImplTest {
//
//    @InjectMocks
//    private GeneratePdfServiceImpl generatePdfService;
//
//    @Mock
//    private Document mockDocument;
//
//    @Mock
//    private QuotationRepository quotationRepository;
//
//    @Mock
//    private ServletContext servletContext;
//
//    @Mock
//    private AdminServiceImpl adminServiceImpl;
//
//    @Mock
//    private File mockFile;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        generatePdfService = new GeneratePdfServiceImpl(quotationRepository, servletContext, adminServiceImpl);
//        mockDocument = mock(Document.class);
//
//        ReflectionTestUtils.setField(generatePdfService, "servletContext", servletContext);
//    }
//
//    @Test
//    public void testAddParagraph() throws Exception {
//        // Arrange
//        String text = "Test Paragraph";
//        Font font = new Font();
//        int alignment = Element.ALIGN_LEFT;
//        float spacingAfter = 10.0f;
//
//        // Get the private method using reflection
//        Method method = GeneratePdfServiceImpl.class.getDeclaredMethod("addParagraph", Document.class, String.class, Font.class, int.class, float.class);
//        method.setAccessible(true);  // Make private method accessible
//
//        // Act - Call private method via reflection
//        method.invoke(generatePdfService, mockDocument, text, font, alignment, spacingAfter);
//
//        // Assert - Verify if the add method was called on the mock Document
//        Mockito.verify(mockDocument, Mockito.times(1)).add(Mockito.any(Paragraph.class));
//    }
//
//    @Test
//    public void testAddImageFromClasspath_ValidImage() throws Exception {
//        // Arrange
//        String imagePath = "validImage.jpg"; // Replace with an actual test image path.
//        float width = 100;
//        float height = 100;
//        int alignment = Element.ALIGN_CENTER;
//
//        // Get the private method using reflection
//        Method method = GeneratePdfServiceImpl.class.getDeclaredMethod("addImageFromClasspath", Document.class, String.class, float.class, float.class, int.class);
//        method.setAccessible(true);  // Make private method accessible
//
//        // Mock servletContext to return the image path
//        when(servletContext.getRealPath(imagePath)).thenReturn(imagePath);
//
//        // Mock File creation (we mock the actual file object)
//        File mockFile = PowerMockito.mock(File.class);
//        PowerMockito.whenNew(File.class).withArguments(imagePath).thenReturn(mockFile);
//        when(mockFile.exists()).thenReturn(true);
//
//        // Act - Call private method via reflection
//        method.invoke(generatePdfService, mockDocument, imagePath, width, height, alignment);
//
//        // Assert - Verify if the add method was called on the mock Document
//        verify(mockDocument, Mockito.times(1)).add(Mockito.any(Image.class));  // Verify that add() was called once with any Image
//    }
//
//    @Test
//    public void testAddImageFromClasspath_InvalidImage() throws Exception {
//        // Arrange
//        String imagePath = "invalidImage.jpg"; // Invalid image path
//        float width = 100;
//        float height = 100;
//        int alignment = Element.ALIGN_CENTER;
//
//        // Get the private method using reflection
//        Method method = GeneratePdfServiceImpl.class.getDeclaredMethod("addImageFromClasspath", Document.class, String.class, float.class, float.class, int.class);
//        method.setAccessible(true);  // Make private method accessible
//
//        // Act & Assert - Verify that an exception is thrown
//        assertThrows(RuntimeException.class, () -> {
//            method.invoke(generatePdfService, mockDocument, imagePath, width, height, alignment);
//        });
//    }
//}
