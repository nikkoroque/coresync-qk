import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.persistence.EntityManager;
import org.coresync.app.model.InventoryCode;
import org.coresync.app.repository.inventory.InventoryCodeRepository;
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

public class InventoryCodeRepositoryTest {

    @InjectMocks
    private InventoryCodeRepository inventoryCodeRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private JPAStreamer jpaStreamer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    InventoryCode mockInventoryCode = new InventoryCode();

    {
        mockInventoryCode.setId(1);
        mockInventoryCode.setDescription("Test Description");
        mockInventoryCode.setCreationDate(OffsetDateTime.now());
        mockInventoryCode.setCreatedByUser("QKDEV");
        mockInventoryCode.setLastUpdateDate(OffsetDateTime.now());
        mockInventoryCode.setLastUpdatedByUser("QKDEV");
    }

    @Test
    void testGetInventoryCodes() {
        List<InventoryCode> mockInventoryCodes = List.of(
                mockInventoryCode,
                new InventoryCode() {{
                    setId(2);
                    setDescription("Another Description");
                    setCreationDate(OffsetDateTime.now());
                    setCreatedByUser("TestUser2");
                    setLastUpdateDate(OffsetDateTime.now());
                    setLastUpdatedByUser("TestUser2");
                }}
        );

        when(jpaStreamer.stream(InventoryCode.class)).thenReturn(mockInventoryCodes.stream());

        List<InventoryCode> result = inventoryCodeRepository.getInventoryCodes();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jpaStreamer, times(1)).stream(InventoryCode.class);
    }

    @Test
    void testGetInventoryCodeDetail() {
        when(jpaStreamer.stream(InventoryCode.class)).thenReturn(Stream.of(mockInventoryCode));

        Optional<InventoryCode> result = inventoryCodeRepository.getInventoryCodeDetail(1);

        assertTrue(result.isPresent());
        assertEquals(mockInventoryCode, result.get());
        verify(jpaStreamer, times(1)).stream(InventoryCode.class);
    }

    @Test
    void testCreateInventoryCode() {
        doNothing().when(entityManager).persist(mockInventoryCode);

        InventoryCode result = inventoryCodeRepository.createInventoryCode(mockInventoryCode);

        assertNotNull(result);
        assertEquals(mockInventoryCode, result);
        verify(entityManager, times(1)).persist(mockInventoryCode);
    }

    @Test
    void testUpdateInventoryCode() {
        when(jpaStreamer.stream(InventoryCode.class)).thenReturn(Stream.of(mockInventoryCode));
        when(entityManager.merge(mockInventoryCode)).thenReturn(mockInventoryCode);

        InventoryCode result = inventoryCodeRepository.updateInventoryCode(mockInventoryCode);

        assertNotNull(result);
        assertEquals(mockInventoryCode, result);
        verify(entityManager, times(1)).merge(mockInventoryCode);
    }

    @Test
    void testDeleteInventoryCode() {
        when(jpaStreamer.stream(InventoryCode.class)).thenReturn(Stream.of(mockInventoryCode));
        when(entityManager.find(InventoryCode.class, 1)).thenReturn(mockInventoryCode);

        doNothing().when(entityManager).remove(mockInventoryCode);

        inventoryCodeRepository.deleteInventoryCode(1);

        verify(entityManager, times(1)).remove(mockInventoryCode);
    }

    @Test
    void testValidateInventoryCodeExists() {
        when(jpaStreamer.stream(InventoryCode.class)).thenReturn(Stream.of(mockInventoryCode));

        boolean exists = inventoryCodeRepository.validateInventoryCodeExists(1);

        assertTrue(exists, "Inventory Code with ID 1 exists.");
        verify(jpaStreamer, times(1)).stream(InventoryCode.class);
    }

    @Test
    void testValidateInventoryCodeDuplicate() {
        when(jpaStreamer.stream(InventoryCode.class)).thenReturn(Stream.of(mockInventoryCode));

        boolean exists = inventoryCodeRepository.validateInventoryCodeDuplicate("Test Description");

        assertTrue(exists, "Inventory Code 'Test Description' exists.");
        verify(jpaStreamer, times(1)).stream(InventoryCode.class);
    }
}
