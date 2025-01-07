import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.persistence.EntityManager;
import org.coresync.app.model.HazardClass;
import org.coresync.app.repository.inventory.HazardClassRepository;
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

public class HazardClassRepositoryTest {

    @InjectMocks
    private HazardClassRepository hazardClassRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private JPAStreamer jpaStreamer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    HazardClass mockHazardClass = new HazardClass();

    {
        mockHazardClass.setId(1);
        mockHazardClass.setClassCode("HAZ1");
        mockHazardClass.setDescription("Hazardous Material");
        mockHazardClass.setCreationDate(OffsetDateTime.now());
        mockHazardClass.setCreatedByUser("QKDEV");
        mockHazardClass.setLastUpdateDate(OffsetDateTime.now());
        mockHazardClass.setLastUpdatedByUser("QKDEV");
    }

    @Test
    void testGetHazardClasses() {
        List<HazardClass> mockHazardClasses = List.of(
                mockHazardClass,
                new HazardClass(2, "HAZ2", "Flammable Material", OffsetDateTime.now(), "QTDEV", OffsetDateTime.now(), "QTDEV")
        );

        when(jpaStreamer.stream(HazardClass.class)).thenReturn(mockHazardClasses.stream());

        List<HazardClass> result = hazardClassRepository.getHazardClasses();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jpaStreamer, times(1)).stream(HazardClass.class);
    }

    @Test
    void testGetHazardClassDetail() {
        when(jpaStreamer.stream(HazardClass.class)).thenReturn(Stream.of(mockHazardClass));

        Optional<HazardClass> result = hazardClassRepository.getHazardClassDetail(1);

        assertTrue(result.isPresent());
        assertEquals(mockHazardClass, result.get());
        verify(jpaStreamer, times(1)).stream(HazardClass.class);
    }

    @Test
    void testCreateHazardClass() {
        doNothing().when(entityManager).persist(mockHazardClass);

        HazardClass result = hazardClassRepository.createHazardClass(mockHazardClass);

        assertNotNull(result);
        assertEquals(mockHazardClass, result);
        verify(entityManager, times(1)).persist(mockHazardClass);
    }

    @Test
    void testUpdateHazardClass() {
        when(jpaStreamer.stream(HazardClass.class)).thenReturn(Stream.of(mockHazardClass));
        when(entityManager.merge(mockHazardClass)).thenReturn(mockHazardClass);

        HazardClass result = hazardClassRepository.updateHazardClass(mockHazardClass);

        assertNotNull(result);
        assertEquals(mockHazardClass, result);
        verify(entityManager, times(1)).merge(mockHazardClass);
    }

    @Test
    void testDeleteHazardClass() {
        when(jpaStreamer.stream(HazardClass.class)).thenReturn(Stream.of(mockHazardClass));
        when(entityManager.find(HazardClass.class, 1)).thenReturn(mockHazardClass);

        doNothing().when(entityManager).remove(mockHazardClass);

        hazardClassRepository.deleteHazardClass(1);

        verify(entityManager, times(1)).remove(mockHazardClass);
    }

    @Test
    void testValidateHazardClassExists() {
        when(jpaStreamer.stream(HazardClass.class)).thenReturn(Stream.of(mockHazardClass));

        boolean exists = hazardClassRepository.validateHazardClassExists(1);

        assertTrue(exists, "Hazard Class with ID 1 exists.");
        verify(jpaStreamer, times(1)).stream(HazardClass.class);
    }

    @Test
    void testValidateHazardClassDuplicate() {
        when(jpaStreamer.stream(HazardClass.class)).thenReturn(Stream.of(mockHazardClass));

        boolean exists = hazardClassRepository.validateHazardClassDuplicate("HAZ1");

        assertTrue(exists, "Hazard Class 'HAZ1' exists.");
        verify(jpaStreamer, times(1)).stream(HazardClass.class);
    }
}
