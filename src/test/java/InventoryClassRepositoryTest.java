import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.persistence.EntityManager;
import org.coresync.app.model.InventoryClass;
import org.coresync.app.repository.inventory.InventoryClassRepository;
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

public class InventoryClassRepositoryTest {

    @InjectMocks
    private InventoryClassRepository inventoryClassRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private JPAStreamer jpaStreamer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    InventoryClass mockInventoryClass = new InventoryClass();

    {
        mockInventoryClass.setId(1);
        mockInventoryClass.setInventoryCodeId(101);
        mockInventoryClass.setDescription("Test Class Description");
        mockInventoryClass.setCreationDate(OffsetDateTime.now());
        mockInventoryClass.setCreatedByUser("QKDEV");
        mockInventoryClass.setLastUpdateDate(OffsetDateTime.now());
        mockInventoryClass.setLastUpdatedByUser("QKDEV");
    }

    @Test
    void testGetInventoryClasses() {
        List<InventoryClass> mockInventoryClasses = List.of(
                mockInventoryClass,
                new InventoryClass() {{
                    setId(2);
                    setInventoryCodeId(102);
                    setDescription("Another Class Description");
                    setCreationDate(OffsetDateTime.now());
                    setCreatedByUser("QKDEV");
                    setLastUpdateDate(OffsetDateTime.now());
                    setLastUpdatedByUser("QKDEV");
                }}
        );

        when(jpaStreamer.stream(InventoryClass.class)).thenReturn(mockInventoryClasses.stream());

        List<InventoryClass> result = inventoryClassRepository.getInventoryClasses();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jpaStreamer, times(1)).stream(InventoryClass.class);
    }

    @Test
    void testGetInventoryClassDetail() {
        when(jpaStreamer.stream(InventoryClass.class)).thenReturn(Stream.of(mockInventoryClass));

        Optional<InventoryClass> result = inventoryClassRepository.getInventoryClassDetail(1);

        assertTrue(result.isPresent());
        assertEquals(mockInventoryClass, result.get());
        verify(jpaStreamer, times(1)).stream(InventoryClass.class);
    }

    @Test
    void testCreateInventoryClass() {
        doNothing().when(entityManager).persist(mockInventoryClass);

        InventoryClass result = inventoryClassRepository.createInventoryClass(mockInventoryClass);

        assertNotNull(result);
        assertEquals(mockInventoryClass, result);
        verify(entityManager, times(1)).persist(mockInventoryClass);
    }

    @Test
    void testUpdateInventoryClass() {
        when(jpaStreamer.stream(InventoryClass.class)).thenReturn(Stream.of(mockInventoryClass));
        when(entityManager.merge(mockInventoryClass)).thenReturn(mockInventoryClass);

        InventoryClass result = inventoryClassRepository.updateInventoryClass(mockInventoryClass);

        assertNotNull(result);
        assertEquals(mockInventoryClass, result);
        verify(entityManager, times(1)).merge(mockInventoryClass);
    }

    @Test
    void testDeleteInventoryClass() {
        when(jpaStreamer.stream(InventoryClass.class)).thenReturn(Stream.of(mockInventoryClass));
        when(entityManager.find(InventoryClass.class, 1)).thenReturn(mockInventoryClass);

        doNothing().when(entityManager).remove(mockInventoryClass);

        inventoryClassRepository.deleteInventoryClass(1);

        verify(entityManager, times(1)).remove(mockInventoryClass);
    }

    @Test
    void testValidateInventoryClassExists() {
        when(jpaStreamer.stream(InventoryClass.class)).thenReturn(Stream.of(mockInventoryClass));

        boolean exists = inventoryClassRepository.validateInventoryClassExists(1);

        assertTrue(exists, "Inventory Class with ID 1 exists.");
        verify(jpaStreamer, times(1)).stream(InventoryClass.class);
    }

    @Test
    void testValidateInventoryClassDuplicate() {
        when(jpaStreamer.stream(InventoryClass.class)).thenReturn(Stream.of(mockInventoryClass));

        boolean exists = inventoryClassRepository.validateInventoryClassDuplicate("Test Class Description");

        assertTrue(exists, "Inventory Class 'Test Class Description' exists.");
        verify(jpaStreamer, times(1)).stream(InventoryClass.class);
    }
}
