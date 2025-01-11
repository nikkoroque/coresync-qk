import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.persistence.EntityManager;
import org.coresync.app.model.ManufacturerMaster;
import org.coresync.app.repository.inventory.ManufacturerMasterRepository;
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

public class ManufacturerMasterRepositoryTest {

    @InjectMocks
    private ManufacturerMasterRepository manufacturerRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private JPAStreamer jpaStreamer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    ManufacturerMaster mockManufacturer = new ManufacturerMaster();

    {
        mockManufacturer.setId(1);
        mockManufacturer.setManufacturerCode("MFG001");
        mockManufacturer.setManufacturerName("Test Manufacturer");
        mockManufacturer.setAddress("123 Main St");
        mockManufacturer.setContactNumber("123-456-7890");
        mockManufacturer.setEmail("test@manufacturer.com");
        mockManufacturer.setCreationDate(OffsetDateTime.now());
        mockManufacturer.setCreatedByUser("TestUser");
        mockManufacturer.setLastUpdateDate(OffsetDateTime.now());
        mockManufacturer.setLastUpdatedByUser("TestUser");
    }

    @Test
    void testGetManufacturers() {
        List<ManufacturerMaster> mockManufacturers = List.of(
                mockManufacturer,
                new ManufacturerMaster() {{
                    setId(2);
                    setManufacturerCode("MFG002");
                    setManufacturerName("Another Manufacturer");
                    setAddress("456 Another St");
                    setContactNumber("987-654-3210");
                    setEmail("another@manufacturer.com");
                    setCreationDate(OffsetDateTime.now());
                    setCreatedByUser("User2");
                    setLastUpdateDate(OffsetDateTime.now());
                    setLastUpdatedByUser("User2");
                }}
        );

        when(jpaStreamer.stream(ManufacturerMaster.class)).thenReturn(mockManufacturers.stream());

        List<ManufacturerMaster> result = manufacturerRepository.getManufacturers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jpaStreamer, times(1)).stream(ManufacturerMaster.class);
    }

    @Test
    void testGetManufacturerDetail() {
        when(jpaStreamer.stream(ManufacturerMaster.class)).thenReturn(Stream.of(mockManufacturer));

        Optional<ManufacturerMaster> result = manufacturerRepository.getManufacturerDetail(1);

        assertTrue(result.isPresent());
        assertEquals(mockManufacturer, result.get());
        verify(jpaStreamer, times(1)).stream(ManufacturerMaster.class);
    }

    @Test
    void testCreateManufacturer() {
        doNothing().when(entityManager).persist(mockManufacturer);

        ManufacturerMaster result = manufacturerRepository.createManufacturer(mockManufacturer);

        assertNotNull(result);
        assertEquals(mockManufacturer, result);
        verify(entityManager, times(1)).persist(mockManufacturer);
    }

    @Test
    void testUpdateManufacturer() {
        when(jpaStreamer.stream(ManufacturerMaster.class)).thenReturn(Stream.of(mockManufacturer));
        when(entityManager.merge(mockManufacturer)).thenReturn(mockManufacturer);

        ManufacturerMaster result = manufacturerRepository.updateManufacturer(mockManufacturer);

        assertNotNull(result);
        assertEquals(mockManufacturer, result);
        verify(entityManager, times(1)).merge(mockManufacturer);
    }

    @Test
    void testDeleteManufacturer() {
        when(jpaStreamer.stream(ManufacturerMaster.class)).thenReturn(Stream.of(mockManufacturer));
        when(entityManager.find(ManufacturerMaster.class, 1)).thenReturn(mockManufacturer);

        doNothing().when(entityManager).remove(mockManufacturer);

        manufacturerRepository.deleteManufacturer(1);

        verify(entityManager, times(1)).remove(mockManufacturer);
    }

    @Test
    void testValidateManufacturerExists() {
        when(jpaStreamer.stream(ManufacturerMaster.class)).thenReturn(Stream.of(mockManufacturer));

        boolean exists = manufacturerRepository.validateManufacturerExists(1);

        assertTrue(exists, "Manufacturer with ID 1 exists.");
        verify(jpaStreamer, times(1)).stream(ManufacturerMaster.class);
    }

    @Test
    void testValidateManufacturerDuplicate() {
        when(jpaStreamer.stream(ManufacturerMaster.class)).thenReturn(Stream.of(mockManufacturer));

        boolean exists = manufacturerRepository.validateManufacturerDuplicate("MFG001");

        assertTrue(exists, "Manufacturer with Code 'MFG001' exists.");
        verify(jpaStreamer, times(1)).stream(ManufacturerMaster.class);
    }
}
