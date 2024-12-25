import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.persistence.EntityManager;
import org.coresync.app.model.TrackItemCode;
import org.coresync.app.repository.inventory.TrackItemCodeRepository;
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

public class TrackItemCodeRepositoryTest {
    @InjectMocks
    private TrackItemCodeRepository trackItemCodeRepository;
    @Mock
    private EntityManager entityManager;
    @Mock
    private JPAStreamer jpaStreamer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTrackItemCodes() {
        List<TrackItemCode> mockItemCodes = List.of(new TrackItemCode(1, "N", "Not Tracked", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV"), new TrackItemCode(2, "B", "Tracked by Barcode", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV"));

        when(jpaStreamer.stream(TrackItemCode.class)).thenReturn(mockItemCodes.stream());

        List<TrackItemCode> result = trackItemCodeRepository.getAllTrackItemCodes();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jpaStreamer, times(1)).stream(TrackItemCode.class);
    }

    @Test
    void testGetTrackItemCodeDetail() {
        TrackItemCode mockItemCode = new TrackItemCode(1, "N", "Not Tracked", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"),"QKDEV");

        when(jpaStreamer.stream(TrackItemCode.class)).thenReturn(Stream.of(mockItemCode));

        Optional<TrackItemCode> result = trackItemCodeRepository.getTrackItemCodeDetail(1);

        assertTrue(result.isPresent());
        assertEquals(mockItemCode, result.get());
        verify(jpaStreamer, times(1)).stream(TrackItemCode.class);
    }

    @Test
    void testAddTrackItemCode() {
        TrackItemCode newItemCode = new TrackItemCode(1, "N", "Not Tracked", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"),"QKDEV");

        doNothing().when(entityManager).persist(newItemCode);

        TrackItemCode result = trackItemCodeRepository.addTrackItemCode(newItemCode);

        assertNotNull(result);
        assertEquals(newItemCode, result);
        verify(entityManager, times(1)).persist(newItemCode);
    }

    @Test
    void testUpdateTrackItemCode() {
        TrackItemCode existingMockItemCode = new TrackItemCode(1, "N", "Not Tracked", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"),"QKDEV");

        when(jpaStreamer.stream(TrackItemCode.class)).thenReturn(Stream.of(existingMockItemCode));
        when(entityManager.merge(existingMockItemCode)).thenReturn(existingMockItemCode);

        TrackItemCode result = trackItemCodeRepository.updateTrackItemCode(existingMockItemCode);

        assertNotNull(result);
        assertEquals(existingMockItemCode, result);
        verify(entityManager, times(1)).merge(existingMockItemCode);
    }

    @Test
    void testDeleteTrackItemCode() {
        TrackItemCode mockItemCode = new TrackItemCode(1, "N", "Not Tracked", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"),"QKDEV");

        when(jpaStreamer.stream(TrackItemCode.class)).thenReturn(Stream.of(mockItemCode));
        when(entityManager.find(TrackItemCode.class, 1)).thenReturn(mockItemCode);
        doNothing().when(entityManager).remove(mockItemCode);

        trackItemCodeRepository.deleteTrackItemCode(1);

        verify(entityManager, times(1)).remove(mockItemCode);
    }

    @Test
    void testTrackItemCodeExists() {
        TrackItemCode mockItemCode = new TrackItemCode(1, "N", "Not Tracked", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"),"QKDEV");

        when(jpaStreamer.stream(TrackItemCode.class)).thenReturn(Stream.of(mockItemCode));

        boolean exists = trackItemCodeRepository.validateTrackItemCodeExists(1);

        assertTrue(exists, "Track Item Code with ID 1 should exists.");
        verify(jpaStreamer, times(1)).stream(TrackItemCode.class);
    }

    @Test
    void testTrackItemCodeDuplicate() {
        TrackItemCode mockItemCode = new TrackItemCode(1, "N", "Not Tracked", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"),"QKDEV");

        when(jpaStreamer.stream(TrackItemCode.class)).thenReturn(Stream.of(mockItemCode));

        boolean exists = trackItemCodeRepository.validateTrackItemCodeDuplicate("N");

        assertTrue(exists, "Track Item Code N should exist");
        verify(jpaStreamer, times(1)).stream(TrackItemCode.class);
    }
}
