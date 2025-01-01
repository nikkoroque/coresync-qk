import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.persistence.EntityManager;
import org.coresync.app.model.ShelfLifeTypeCode;
import org.coresync.app.model.ShelfLifeTypeCodeDTO;
import org.coresync.app.repository.inventory.ShelfLifeTypeCodeRepository;
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

public class ShelfLifeTypeCodeRepositoryTest {
    @InjectMocks
    private ShelfLifeTypeCodeRepository shelfLifeTypeCodeRepository;
    @Mock
    private EntityManager entityManager;
    @Mock
    private JPAStreamer jpaStreamer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    ShelfLifeTypeCode mockShelfLifeTypeCode = new ShelfLifeTypeCode(1, "T1", "Test", OffsetDateTime.now(), "QKDEV",
            OffsetDateTime.now(), "QKDEV");

    @Test
    void testGetShelfLifeTypeCodes() {
        List<ShelfLifeTypeCode> mockShelfLifeTypeCodes = List.of(new ShelfLifeTypeCode(1, "T1", "Test",
                OffsetDateTime.now(), "QKDEV",OffsetDateTime.now(), "QKDEV"),
                new ShelfLifeTypeCode(2, "T2", "Test", OffsetDateTime.now(), "QKDEV",OffsetDateTime.now(), "QKDEV"));

        when(jpaStreamer.stream(ShelfLifeTypeCode.class)).thenReturn(mockShelfLifeTypeCodes.stream());
        List<ShelfLifeTypeCode> result = shelfLifeTypeCodeRepository.getShelfLifeTypeCodes();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jpaStreamer, times(1)).stream(ShelfLifeTypeCode.class);
    }

    @Test
    void testGetShelfLifeTypeCodeMetaData() {
        List<ShelfLifeTypeCode> mockShelfLifeTypeCodes = List.of(
                new ShelfLifeTypeCode(1, "T1", "Test", OffsetDateTime.now(), "QKDEV", OffsetDateTime.now(), "QKDEV"),
                new ShelfLifeTypeCode(2, "T2", "Test", OffsetDateTime.now(), "QKDEV", OffsetDateTime.now(), "QKDEV")
        );

        when(jpaStreamer.stream(ShelfLifeTypeCode.class)).thenReturn(mockShelfLifeTypeCodes.stream());

        List<ShelfLifeTypeCodeDTO> result = shelfLifeTypeCodeRepository.getShelfLifeTypeCodeNames();

        assertNotNull(result, "Result should not be null");
        assertEquals(2, result.size(), "Result size should be 2");

        verify(jpaStreamer, times(1)).stream(ShelfLifeTypeCode.class);
    }


    @Test
    void testGetShelfLifeTypeCodeDetail() {
        when(jpaStreamer.stream(ShelfLifeTypeCode.class)).thenReturn(Stream.of(mockShelfLifeTypeCode));

        Optional<ShelfLifeTypeCode> result = shelfLifeTypeCodeRepository.getShelfLifeTypeCodeDetail(1);

        assertTrue(result.isPresent());
        assertEquals(mockShelfLifeTypeCode, result.get());
        verify(jpaStreamer, times(1)).stream(ShelfLifeTypeCode.class);
    }

    @Test
    void testCreateShelfLifeTypeCode() {
        doNothing().when(entityManager).persist(mockShelfLifeTypeCode);

        ShelfLifeTypeCode result = shelfLifeTypeCodeRepository.createShelfLifeTypeCode(mockShelfLifeTypeCode);

        assertNotNull(result);
        assertEquals(mockShelfLifeTypeCode, result);
        verify(entityManager, times(1)).persist(mockShelfLifeTypeCode);
    }

    @Test
    void testUpdateShelfLifeTypeCode() {
        when(jpaStreamer.stream(ShelfLifeTypeCode.class)).thenReturn(Stream.of(mockShelfLifeTypeCode));
        when(entityManager.merge(mockShelfLifeTypeCode)).thenReturn(mockShelfLifeTypeCode);

        ShelfLifeTypeCode result = shelfLifeTypeCodeRepository.updateShelfLifeTypeCode(mockShelfLifeTypeCode);

        assertNotNull(result);
        assertEquals(mockShelfLifeTypeCode, result);
        verify(entityManager, times(1)).merge(mockShelfLifeTypeCode);
    }

    @Test
    void testDeleteShelfLifeTypeCode() {
        when(jpaStreamer.stream(ShelfLifeTypeCode.class)).thenReturn(Stream.of(mockShelfLifeTypeCode));

        when(entityManager.find(ShelfLifeTypeCode.class,1)).thenReturn(mockShelfLifeTypeCode);

        doNothing().when(entityManager).remove(mockShelfLifeTypeCode);
    }

    @Test
    void testValidateShelfLifeTypeCodeExist() {
        when(jpaStreamer.stream(ShelfLifeTypeCode.class)).thenReturn(Stream.of(mockShelfLifeTypeCode));

        boolean exists = shelfLifeTypeCodeRepository.validateShelfLifeTypeCodeExists(1);

        assertTrue(exists, "Shelf Life Type Code with ID 1 exists.");
        verify(jpaStreamer, times(1)).stream(ShelfLifeTypeCode.class);
    }

    @Test
    void testValidateShelfLifeTypeCodeDuplicate() {
        when(jpaStreamer.stream(ShelfLifeTypeCode.class)).thenReturn(Stream.of(mockShelfLifeTypeCode));

        boolean exists = shelfLifeTypeCodeRepository.validateShelfLifeTypeCodeDuplicate("T1");

        assertTrue(exists, "Shelf Life Type Code T1 exists.");
        verify(jpaStreamer, times(1)).stream(ShelfLifeTypeCode.class);
    }
}
