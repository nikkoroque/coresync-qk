import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.persistence.EntityManager;
import org.coresync.app.model.JurisdictionClassTypeCode;
import org.coresync.app.model.JurisdictionClassTypeCodeDTO;
import org.coresync.app.repository.inventory.JurisdictionClassTypeCodeRepository;
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

public class JurisdictionClassTypeCodeRepositoryTest {
    @InjectMocks
    private JurisdictionClassTypeCodeRepository jurisdictionClassTypeCodeRepository;
    @Mock
    private EntityManager entityManager;
    @Mock
    private JPAStreamer jpaStreamer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    JurisdictionClassTypeCode mockJurisdictionClassTypeCode = new JurisdictionClassTypeCode(1, "BARANGAY", "Barangay Jurisdiction", OffsetDateTime.now(), "QKDEV", OffsetDateTime.now(), "QKDEV");

    List<JurisdictionClassTypeCode> mockJurisdictionClassTypeCodes = List.of(new JurisdictionClassTypeCode(1, "BARANGAY", "Barangay Jurisdiction", OffsetDateTime.now(), "QKDEV", OffsetDateTime.now(), "QKDEV"), new JurisdictionClassTypeCode(2, "PROVINCE", "Province Jurisdiction", OffsetDateTime.now(), "QKDEV", OffsetDateTime.now(), "QKDEV"));

    @Test
    void testGetJurisdictionClassTypeCodes() {

        when(jpaStreamer.stream(JurisdictionClassTypeCode.class)).thenReturn(mockJurisdictionClassTypeCodes.stream());
        List<JurisdictionClassTypeCode> result = jurisdictionClassTypeCodeRepository.getJurisdictionClassTypeCodes();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jpaStreamer, times(1)).stream(JurisdictionClassTypeCode.class);
    }

    @Test
    void testGetJurisdictionClassTypeCodeMetaData() {
        when(jpaStreamer.stream(JurisdictionClassTypeCode.class)).thenReturn(mockJurisdictionClassTypeCodes.stream());

        List<JurisdictionClassTypeCodeDTO> result = jurisdictionClassTypeCodeRepository.getJurisdictionClassTypeCodeDTO();

        assertNotNull(result, "Result should not be null");
        assertEquals(2, result.size());

        verify(jpaStreamer, times(1)).stream(JurisdictionClassTypeCode.class);
    }

    @Test
    void testGetJurisdictionClassTypeCodeDetail() {
        when(jpaStreamer.stream(JurisdictionClassTypeCode.class)).thenReturn(Stream.of(mockJurisdictionClassTypeCode));

        Optional<JurisdictionClassTypeCode> result = jurisdictionClassTypeCodeRepository.getJurisdictionClassTypeCodeDetail(1);

        assertTrue(result.isPresent());
        assertEquals(mockJurisdictionClassTypeCode, result.get());
        verify(jpaStreamer, times(1)).stream(JurisdictionClassTypeCode.class);
    }

    @Test
    void testCreateJurisdictionClassTypeCode() {
        doNothing().when(entityManager).persist(mockJurisdictionClassTypeCode);

        JurisdictionClassTypeCode result = jurisdictionClassTypeCodeRepository.createJurisdictionClassTypeCode(mockJurisdictionClassTypeCode);

        assertNotNull(result);
        assertEquals(mockJurisdictionClassTypeCode, result);
        verify(entityManager, times(1)).persist(mockJurisdictionClassTypeCode);
    }

    @Test
    void testUpdateJurisdictionClassTypeCode() {
     when(jpaStreamer.stream(JurisdictionClassTypeCode.class)).thenReturn(Stream.of(mockJurisdictionClassTypeCode));
    when(entityManager.merge(mockJurisdictionClassTypeCode)).thenReturn(mockJurisdictionClassTypeCode);

    JurisdictionClassTypeCode result = jurisdictionClassTypeCodeRepository.updateClassTypeCode(mockJurisdictionClassTypeCode);

    assertNotNull(result);
    assertEquals(mockJurisdictionClassTypeCode, result);
    verify(entityManager, times(1)).merge(mockJurisdictionClassTypeCode);
    }

    @Test
    void testDeleteJurisdictionClassTypeCode() {
        when(jpaStreamer.stream(JurisdictionClassTypeCode.class)).thenReturn(Stream.of(mockJurisdictionClassTypeCode));

        when(entityManager.find(JurisdictionClassTypeCode.class, 1)).thenReturn(mockJurisdictionClassTypeCode);

        doNothing().when(entityManager).remove(mockJurisdictionClassTypeCode);
    }

    @Test
    void testValidateJurisdictionClassTypeCodeExists() {
        when(jpaStreamer.stream(JurisdictionClassTypeCode.class)).thenReturn(Stream.of(mockJurisdictionClassTypeCode));

        boolean exists = jurisdictionClassTypeCodeRepository.validateJurisdictionClassTypeCodeExists(1);

        assertTrue(exists, "Jurisdiction Class Type Code with ID 1 exists.");
        verify(jpaStreamer, times(1)).stream(JurisdictionClassTypeCode.class);
    }

    @Test
    void testValidateJurisdictionClassTypeDuplicate() {
        when(jpaStreamer.stream(JurisdictionClassTypeCode.class)).thenReturn(Stream.of(mockJurisdictionClassTypeCode));

        boolean exists = jurisdictionClassTypeCodeRepository.validateJurisdictionClassTypeCodeDuplicate("BARANGAY");

        assertTrue(exists, "Jurisdiction Class Type Code BARANGAY exists.");
        verify(jpaStreamer, times(1)).stream(JurisdictionClassTypeCode.class);
    }
}
