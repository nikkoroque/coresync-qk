import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.persistence.EntityManager;
import org.coresync.app.model.SectionCode;
import org.coresync.app.repository.inventory.SectionCodeRepository;
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

public class SectionCodeRepositoryTest {
    @InjectMocks
    private SectionCodeRepository sectionCodeRepository;
    @Mock
    private EntityManager entityManager;
    @Mock
    private JPAStreamer jpaStreamer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    SectionCode mockSectionCode = new SectionCode(1, "T99", "TEST99", OffsetDateTime.now(), "QKDEV",OffsetDateTime.now(), "QKDEV");

    @Test
    void testGetSectionCodes() {
        List<SectionCode> mockSectionCodes = List.of(new SectionCode(1, "T99", "TEST99", OffsetDateTime.now(), "QKDEV",OffsetDateTime.now(), "QKDEV"), new SectionCode(2, "T100", "TEST100", OffsetDateTime.now(), "QKDEV",OffsetDateTime.now(), "QKDEV"));

        when(jpaStreamer.stream(SectionCode.class)).thenReturn(mockSectionCodes.stream());
        List<SectionCode> result = sectionCodeRepository.getSectionCodes();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jpaStreamer, times(1)).stream(SectionCode.class);
    }

    @Test
    void testGetSectionCodeDetail() {
        when(jpaStreamer.stream(SectionCode.class)).thenReturn(Stream.of(mockSectionCode));

        Optional<SectionCode> result = sectionCodeRepository.getSectionCodeDetail(1);

        assertTrue(result.isPresent());
        assertEquals(mockSectionCode, result.get());
        verify(jpaStreamer, times(1)).stream(SectionCode.class);
    }

    @Test
    void testCreateSectionCode() {
        doNothing().when(entityManager).persist(mockSectionCode);

        SectionCode result = sectionCodeRepository.createSectionCode(mockSectionCode);

        assertNotNull(result);
        assertEquals(mockSectionCode, result);
        verify(entityManager, times(1)).persist(mockSectionCode);
    }

    @Test
    void testUpdateSectionCode() {
        when(jpaStreamer.stream(SectionCode.class)).thenReturn(Stream.of(mockSectionCode));
        when(entityManager.merge(mockSectionCode)).thenReturn(mockSectionCode);

        SectionCode result = sectionCodeRepository.updateSectionCode(mockSectionCode);

        assertNotNull(result);
        assertEquals(mockSectionCode, result);
        verify(entityManager, times(1)).merge(mockSectionCode);
    }

    @Test
    void testDeleteSectionCode() {
        when(jpaStreamer.stream(SectionCode.class)).thenReturn(Stream.of(mockSectionCode));
        when(entityManager.find(SectionCode.class, 1)).thenReturn(mockSectionCode);

        doNothing().when(entityManager).remove(mockSectionCode);
    }

    @Test
    void testValidateSectionCodeExists() {
        when(jpaStreamer.stream(SectionCode.class)).thenReturn(Stream.of(mockSectionCode));

        boolean exists = sectionCodeRepository.validateSectionCodeExists(1);

        assertTrue(exists, "Section code with ID 1 exists.");
        verify(jpaStreamer, times(1)).stream(SectionCode.class);
    }

    @Test
    void testValidateSectionCodeDuplicate() {
        when(jpaStreamer.stream(SectionCode.class)).thenReturn(Stream.of(mockSectionCode));

        boolean exists = sectionCodeRepository.validateSectionCodeDuplicate("T99");

        assertTrue(exists, "Status Code T99 exists.");
        verify(jpaStreamer, times(1)).stream(SectionCode.class);
    }

}
