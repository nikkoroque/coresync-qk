import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.persistence.EntityManager;
import org.coresync.app.model.PaginatedResult;
import org.coresync.app.model.UnitOfMeasure;
import org.coresync.app.repository.inventory.UomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Unit Tests
public class UomRepositoryTest {

    @InjectMocks
    private UomRepository uomRepository;
    @Mock
    private EntityManager entityManager;
    @Mock
    private JPAStreamer jpaStreamer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUomCodes() {
        List<UnitOfMeasure> mockCodes = List.of(
                new UnitOfMeasure(1, "EA", "Each", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV"), new UnitOfMeasure(2, "BAG", "Bag", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV")
        );

        when(jpaStreamer.stream(UnitOfMeasure.class)).thenReturn(mockCodes.stream());

        List<UnitOfMeasure> result = uomRepository.getAllUomCodes();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jpaStreamer, times(1)).stream(UnitOfMeasure.class);
    }

    @Test
    void testGetUomCodeDetail() {
        UnitOfMeasure mockCode =
                new UnitOfMeasure(1, "EA", "Each", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV");

        when(jpaStreamer.stream(UnitOfMeasure.class)).thenReturn(Stream.of(mockCode));

        Optional<UnitOfMeasure> result = uomRepository.getUomCodeDetail(1);

        assertTrue(result.isPresent());
        assertEquals(mockCode, result.get());
        verify(jpaStreamer, times(1)).stream(UnitOfMeasure.class);
    }

    @Test
    void testAddUomCode() {
        UnitOfMeasure newMockCode =
                new UnitOfMeasure(1, "EA", "Each", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV");

        doNothing().when(entityManager).persist(newMockCode);

        UnitOfMeasure result = uomRepository.addUomCode(newMockCode);

        assertNotNull(result);
        assertEquals(newMockCode, result);
        verify(entityManager, times(1)).persist(newMockCode);
    }

    @Test
    void testUpdateUomCode() {
        UnitOfMeasure existingMockCode =
                new UnitOfMeasure(1, "EA", "Each", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV");

        when(jpaStreamer.stream(UnitOfMeasure.class)).thenReturn(Stream.of(existingMockCode));
        when(entityManager.merge(existingMockCode)).thenReturn(existingMockCode);

        UnitOfMeasure result = uomRepository.updateUomCode(existingMockCode);

        assertNotNull(result);
        assertEquals(existingMockCode, result);
        verify(entityManager, times(1)).merge(existingMockCode);
    }

    @Test
    void testDeleteUomCode() {
        UnitOfMeasure mockCode =
                new UnitOfMeasure(1, "EA", "Each", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV");

        when(jpaStreamer.stream(UnitOfMeasure.class)).thenReturn(Stream.of(mockCode));
        when(entityManager.find(UnitOfMeasure.class, 1)).thenReturn(mockCode);
        doNothing().when(entityManager).remove(mockCode);

        uomRepository.deleteUomCode(1);

        verify(entityManager, times(1)).remove(mockCode);
    }

    @Test
    void testUomCodeExists() {
        UnitOfMeasure mockCode =
                new UnitOfMeasure(1, "EA", "Each", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV");

        when(jpaStreamer.stream(UnitOfMeasure.class)).thenReturn(Stream.of(mockCode));

        boolean exists = uomRepository.validateUomCodeExists(1);

        assertTrue(exists, "UOM code with ID 1 should exist.");
        verify(jpaStreamer, times(1)).stream(UnitOfMeasure.class);
    }

    @Test
    void testUomCodeDuplicate() {
        UnitOfMeasure mockCode =
                new UnitOfMeasure(1, "EA", "Each", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV");

        when(jpaStreamer.stream(UnitOfMeasure.class)).thenReturn(Stream.of(mockCode));

        boolean exists = uomRepository.validateUomCodeDuplicate("EA");

        assertTrue(exists, "UOM code EA should exists");
        verify(jpaStreamer, times(1)).stream(UnitOfMeasure.class);
    }

    // TODO: Future release pagination
    @Test
    void testPaginatedUomCodes() {
        List<UnitOfMeasure> mockData = List.of(
                new UnitOfMeasure(1, "EA", "Each", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV"),
                new UnitOfMeasure(2, "BAG", "Bag", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV")
        );

        Supplier<Stream<UnitOfMeasure>> mockStreamSupplier = () -> mockData.stream();
        when(jpaStreamer.stream(UnitOfMeasure.class)).thenAnswer(invocation -> mockStreamSupplier.get());

        PaginatedResult<UnitOfMeasure> result = uomRepository.getPaginatedUomCodes(1, "code", "desc");

        assertNotNull(result, "Result should not be null");
        assertEquals(2, result.getData().size(), "Result data size should match the mock data size");
        assertEquals(2, result.getTotalItems(), "Total items should match the mock data size");
        assertEquals("EA", result.getData().get(0).getCode(), "First UOM code should match 'EA'");
        assertEquals("BAG", result.getData().get(1).getCode(), "Second UOM code should match 'BAG'");
    }

    @Test
    void testFilterUomCodes() {
        List<UnitOfMeasure> mockData = List.of(
                new UnitOfMeasure(1, "EA", "Each",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV"),
                new UnitOfMeasure(2, "BAG", "Bag", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV")
        );

        when(jpaStreamer.stream(UnitOfMeasure.class)).thenReturn(mockData.stream());

        Stream<UnitOfMeasure> result = uomRepository.filterUomCodes("EA", "Each", null, null, null, null);

        assertNotNull(result);
        assertEquals(1, result.count());
        verify(jpaStreamer, times(1)).stream(UnitOfMeasure.class);
    }

}