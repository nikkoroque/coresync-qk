import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.persistence.EntityManager;
import org.coresync.app.model.HazardCode;
import org.coresync.app.repository.inventory.HazardCodeRepository;
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

public class HazardCodeRepositoryTest {

    @InjectMocks
    private HazardCodeRepository hazardCodeRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private JPAStreamer jpaStreamer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    HazardCode mockHazardCode = new HazardCode();

    {
        mockHazardCode.setId(1);
        mockHazardCode.setHazardClassId(1);
        mockHazardCode.setDescription1("Description 1");
        mockHazardCode.setDescription2("Description 2");
        mockHazardCode.setMaterialPackageCode("MP-001");
        mockHazardCode.setGovtMaterialCode("GOV-123");
        mockHazardCode.setCreationDate(OffsetDateTime.now());
        mockHazardCode.setCreatedByUser("TestUser");
        mockHazardCode.setLastUpdateDate(OffsetDateTime.now());
        mockHazardCode.setLastUpdatedByUser("TestUser");
    }

    @Test
    void testGetHazardCodes() {
        List<HazardCode> mockHazardCodes = List.of(
                mockHazardCode,
                new HazardCode(2, 2, "Desc 1", "Desc 2", "MP-002", "GOV-456", OffsetDateTime.now(), "User2", OffsetDateTime.now(), "User2")
        );

        when(jpaStreamer.stream(HazardCode.class)).thenReturn(mockHazardCodes.stream());

        List<HazardCode> result = hazardCodeRepository.getHazardCodes();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jpaStreamer, times(1)).stream(HazardCode.class);
    }

    @Test
    void testGetHazardCodeDetail() {
        when(jpaStreamer.stream(HazardCode.class)).thenReturn(Stream.of(mockHazardCode));

        Optional<HazardCode> result = hazardCodeRepository.getHazardCodeDetail(1);

        assertTrue(result.isPresent());
        assertEquals(mockHazardCode, result.get());
        verify(jpaStreamer, times(1)).stream(HazardCode.class);
    }

    @Test
    void testCreateHazardCode() {
        doNothing().when(entityManager).persist(mockHazardCode);

        HazardCode result = hazardCodeRepository.createHazardCode(mockHazardCode);

        assertNotNull(result);
        assertEquals(mockHazardCode, result);
        verify(entityManager, times(1)).persist(mockHazardCode);
    }

    @Test
    void testUpdateHazardCode() {
        when(jpaStreamer.stream(HazardCode.class)).thenReturn(Stream.of(mockHazardCode));
        when(entityManager.merge(mockHazardCode)).thenReturn(mockHazardCode);

        HazardCode result = hazardCodeRepository.updateHazardCode(mockHazardCode);

        assertNotNull(result);
        assertEquals(mockHazardCode, result);
        verify(entityManager, times(1)).merge(mockHazardCode);
    }

    @Test
    void testDeleteHazardCode() {
        when(jpaStreamer.stream(HazardCode.class)).thenReturn(Stream.of(mockHazardCode));
        when(entityManager.find(HazardCode.class, 1)).thenReturn(mockHazardCode);

        doNothing().when(entityManager).remove(mockHazardCode);

        hazardCodeRepository.deleteHazardCode(1);

        verify(entityManager, times(1)).remove(mockHazardCode);
    }

    @Test
    void testValidateHazardCodeExists() {
        when(jpaStreamer.stream(HazardCode.class)).thenReturn(Stream.of(mockHazardCode));

        boolean exists = hazardCodeRepository.validateHazardCodeExists(1);

        assertTrue(exists, "Hazard Code with ID 1 exists.");
        verify(jpaStreamer, times(1)).stream(HazardCode.class);
    }
}
