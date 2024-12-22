import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.persistence.EntityManager;
import org.coresync.app.model.HazardClassification;
import org.coresync.app.repository.inventory.HazardClassificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//Unit Test
public class HazardClassificationRepositoryTest {

    @InjectMocks
    private HazardClassificationRepository hazardClassificationRepository;

    @Mock
    private JPAStreamer jpaStreamer;

    @Mock
    private EntityManager entityManager;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFilterHazardClassifications() {
        List<HazardClassification> mockClasses = List.of(
                new HazardClassification(1, "7","Bleach", Timestamp.valueOf("2024-12-21 15:12:00"), "QKDEV", Timestamp.valueOf("2024-12-21 15:12:00"), "QKDEV"),
                new HazardClassification(2, "8","Bacon", Timestamp.valueOf("2024-12-21 15:12:00"), "QKDEV", Timestamp.valueOf("2024-12-21 15:12:00"), "QKDEV")
        );

        when(jpaStreamer.stream(HazardClassification.class)).thenReturn(mockClasses.stream());

        Stream<HazardClassification> result = hazardClassificationRepository.filterHazardClassification(
                null, "7", null, null, null, null, null);

        assertNotNull(result);
        assertEquals(1, result.count());
        verify(jpaStreamer, times(1)).stream(HazardClassification.class);
    }

    @Test
    void testGetHazardClassificationDetail() {
        HazardClassification mockClass = new HazardClassification(1,"7","Bleach", Timestamp.valueOf("2024-12-21 15:12:00"), "QKDEV", Timestamp.valueOf("2024-12-21 15:12:00"), "QKDEV");

        when(jpaStreamer.stream(HazardClassification.class)).thenReturn(Stream.of(mockClass));

        Optional<HazardClassification> result = hazardClassificationRepository.getHazardClassificationDetail("7");

        assertTrue(result.isPresent());
        assertEquals(mockClass, result.get());
        verify(jpaStreamer, times(1)).stream(HazardClassification.class);
    }

    @Test
    void testAddHazrdClassification() {
        HazardClassification newClass = new HazardClassification(1, "7","Bleach", Timestamp.valueOf("2024-12-21 15:12:00"), "QKDEV", Timestamp.valueOf("2024-12-21 15:12:00"), "QKDEV");

        doNothing().when(entityManager).persist(newClass);

        HazardClassification result = hazardClassificationRepository.addHazardClassification(newClass);

        assertNotNull(result);
        assertEquals(newClass, result);
        verify(entityManager, times(1)).persist(newClass);
    }

    @Test
    void testUpdateHazardClassification() {
        HazardClassification updateClass = new HazardClassification("7","BleachUpdate", Timestamp.valueOf("2024-12-21 15:12:00"), "QKTEST");

        when(jpaStreamer.stream(HazardClassification.class)).thenReturn(Stream.of(updateClass));
        when(entityManager.merge(updateClass)).thenReturn(updateClass);

        HazardClassification result = hazardClassificationRepository.updateHazardClassification(updateClass);

        assertNotNull(result, "The updated Hazard Classification should not be null");
        assertEquals(updateClass, result, "The updated Hazard Classification ID should match the input");
        verify(jpaStreamer, times(1)).stream(HazardClassification.class);
        verify(entityManager, times(1)).merge(updateClass);
    }

    @Test
    void testHazardClassificationCodeExists() {
        HazardClassification mockClass = new HazardClassification("7","BleachUpdate", Timestamp.valueOf("2024-12-21 15:12:00"), "QKTEST");

        when(jpaStreamer.stream(HazardClassification.class)).thenReturn(Stream.of(mockClass));

        boolean exists = hazardClassificationRepository.hazardClassificationExists("7");

        assertTrue(exists, "Hazard Classification Code 7 should exist");
        verify(jpaStreamer, times(1)).stream(HazardClassification.class);
    }

    @Test
    void testDeleteHazardClassification() {
        // Arrange
        HazardClassification deleteClass = new HazardClassification(
                1, "7", "Bleach", Timestamp.valueOf("2024-12-21 15:12:00"), "QKDEV", Timestamp.valueOf("2024-12-21 15:12:00"), "QKDEV"
        );

        // Mock jpaStreamer to return the entity for the given hzrdClsCd
        when(jpaStreamer.stream(HazardClassification.class))
                .thenReturn(Stream.of(deleteClass).filter(h -> h.getHzrdClsCd().equals("7")));

        // Mock entityManager behavior
        when(entityManager.contains(deleteClass)).thenReturn(false);
        when(entityManager.merge(deleteClass)).thenReturn(deleteClass);
        doNothing().when(entityManager).remove(deleteClass);

        // Act
        hazardClassificationRepository.deleteHazardClassification("7");

        // Assert
        verify(jpaStreamer, times(1)).stream(HazardClassification.class);
        verify(entityManager, times(1)).merge(deleteClass);
        verify(entityManager, times(1)).remove(deleteClass);
    }

}
