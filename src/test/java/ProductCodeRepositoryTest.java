
import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.persistence.EntityManager;
import org.coresync.app.model.ProductCode;
import org.coresync.app.repository.inventory.ProductCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ProductCodeRepositoryTest {
    @InjectMocks
    private ProductCodeRepository productCodeRepository;
    @Mock
    private EntityManager entityManager;
    @Mock
    private JPAStreamer jpaStreamer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    ProductCode mockProductCode = new ProductCode(1, "NA","Test", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV");

    @Test
    void testGetProductCodes() {
        List<ProductCode> mockProductCodes = List.of(new ProductCode(1, "NA","Test", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV"), new ProductCode(2, "NA","Test1", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV"));

        when(jpaStreamer.stream(ProductCode.class)).thenReturn(mockProductCodes.stream());

        List<ProductCode> result = productCodeRepository.getProductCodes();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jpaStreamer, times(1)).stream(ProductCode.class);
    }

    @Test
    void testGetProductCodeDetail() {
        when(jpaStreamer.stream(ProductCode.class)).thenReturn(Stream.of(mockProductCode));

        Optional<ProductCode> result = productCodeRepository.getProductCodeDetail(1);

        assertTrue(result.isPresent());
        assertEquals(mockProductCode, result.get());
        verify(jpaStreamer, times(1)).stream(ProductCode.class);
    }

    @Test
    void testCreateProductCode() {
        doNothing().when(entityManager).persist(mockProductCode);

        ProductCode result = productCodeRepository.createProductCode(mockProductCode);

        assertNotNull(result);
        assertEquals(mockProductCode, result);
        verify(entityManager, times(1)).persist(mockProductCode);
    }

    @Test
    void testUpdateProductCode() {
        when(jpaStreamer.stream(ProductCode.class)).thenReturn(Stream.of(mockProductCode));
        when(entityManager.merge(mockProductCode)).thenReturn(mockProductCode);

        ProductCode result = productCodeRepository.updateProductCode(mockProductCode);

        assertNotNull(result);
        assertEquals(mockProductCode, result);
        verify(entityManager, times(1)).merge(mockProductCode);
    }

    @Test
    void testDeleteProductCode() {
        when(jpaStreamer.stream(ProductCode.class)).thenReturn(Stream.of(mockProductCode));
        when(entityManager.find(ProductCode.class, 1)).thenReturn(mockProductCode);

        doNothing().when(entityManager).remove(mockProductCode);
    }

    @Test
    void testValidateProductCodeExists() {
        when(jpaStreamer.stream(ProductCode.class)).thenReturn(Stream.of(mockProductCode));

        boolean exists = productCodeRepository.validateProductCodeExists(1);

        assertTrue(exists, "Product code with ID 1 exists.");
        verify(jpaStreamer, times(1)).stream(ProductCode.class);
    }

    @Test
    void validateProductCodeDuplicate() {
        when(jpaStreamer.stream(ProductCode.class)).thenReturn(Stream.of(mockProductCode));

        boolean exists = productCodeRepository.validateProductCodeDuplicate("Test");

        assertTrue(exists, "Product Code Test exists.");
        verify(jpaStreamer, times(1)).stream(ProductCode.class);
    }
}
