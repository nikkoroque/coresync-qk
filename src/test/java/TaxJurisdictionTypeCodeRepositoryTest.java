import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.persistence.EntityManager;
import org.coresync.app.model.TaxJurisdictionTypeCode;
import org.coresync.app.repository.inventory.TaxJurisdictionTypeCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TaxJurisdictionTypeCodeRepositoryTest {
    @InjectMocks
    private TaxJurisdictionTypeCodeRepository taxJurisdictionTypeCodeRepository;
    @Mock
    private EntityManager entityManager;
    @Mock
    private JPAStreamer jpaStreamer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTaxJurisdictionTypeCodes() {
        List<TaxJurisdictionTypeCode> mockTypeCodes = List.of(new TaxJurisdictionTypeCode(1, "CIGARETTES", "Cigarette Jurisdiction Type", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV"), new TaxJurisdictionTypeCode(2, "CMS",  "CMS Jurisdiction Type",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV"));

        when(jpaStreamer.stream(TaxJurisdictionTypeCode.class)).thenReturn(mockTypeCodes.stream());
        List<TaxJurisdictionTypeCode> result = taxJurisdictionTypeCodeRepository.getAllTaxJurisdictionTypeCodes();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jpaStreamer, times(1)).stream(TaxJurisdictionTypeCode.class);
    }

    @Test
    void testGetTaxJurisdictionTypeCodeDetail() {
        TaxJurisdictionTypeCode mockTypeCode = new TaxJurisdictionTypeCode(1, "CIGARETTES", "Cigarette Jurisdiction Type", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"),"QKDEV");

        when(jpaStreamer.stream(TaxJurisdictionTypeCode.class)).thenReturn(Stream.of(mockTypeCode));

        Optional<TaxJurisdictionTypeCode> result = taxJurisdictionTypeCodeRepository.getTaxJurisdictionTypeCodeDetail(1);

        assertTrue(result.isPresent());
        assertEquals(mockTypeCode, result.get());
        verify(jpaStreamer, times(1)).stream(TaxJurisdictionTypeCode.class);
    }

    @Test
    void testAddTaxJurisdictionTypeCode() {
        TaxJurisdictionTypeCode newTypeCode = new TaxJurisdictionTypeCode(1, "CIGARETTES", "Cigarette Jurisdiction Type", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"),"QKDEV");

        doNothing().when(entityManager).persist(newTypeCode);

        TaxJurisdictionTypeCode result = taxJurisdictionTypeCodeRepository.addTaxJurisdictionTypeCode(newTypeCode);

        assertNotNull(result);
        assertEquals(newTypeCode, result);
        verify(entityManager, times(1)).persist(newTypeCode);
    }

    @Test
    void testUpdateTaxJurisdictionTypeCode() {
        TaxJurisdictionTypeCode existingMockTypeCode = new TaxJurisdictionTypeCode(1, "CIGARETTES", "Cigarette Jurisdiction Type", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"),"QKDEV");

        when(jpaStreamer.stream(TaxJurisdictionTypeCode.class)).thenReturn(Stream.of(existingMockTypeCode));
        when(entityManager.merge(existingMockTypeCode)).thenReturn(existingMockTypeCode);

        TaxJurisdictionTypeCode result = taxJurisdictionTypeCodeRepository.updateTaxJurisdictionTypeCode(existingMockTypeCode);

        assertNotNull(result);
        assertEquals(existingMockTypeCode, result);
        verify(entityManager, times(1)).merge(existingMockTypeCode);
    }

    @Test
    void testDeleteTaxJurisdictionTypeCode() {
        TaxJurisdictionTypeCode mockTypeCode = new TaxJurisdictionTypeCode(1, "CIGARETTES", "Cigarette Jurisdiction Type", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"),"QKDEV");

        when(jpaStreamer.stream(TaxJurisdictionTypeCode.class)).thenReturn(Stream.of(mockTypeCode));
        when(entityManager.find(TaxJurisdictionTypeCode.class, 1)).thenReturn(mockTypeCode);
        doNothing().when(entityManager).remove(mockTypeCode);

        taxJurisdictionTypeCodeRepository.deleteTaxJurisdictionTypeCode(1);
        verify(entityManager,times(1)).remove(mockTypeCode);
    }

    @Test
    void testTaxJurisdictionTypeCodeExists() {
        TaxJurisdictionTypeCode mockTypeCode = new TaxJurisdictionTypeCode(1, "CIGARETTES", "Cigarette Jurisdiction Type", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"),"QKDEV");

        when(jpaStreamer.stream(TaxJurisdictionTypeCode.class)).thenReturn(Stream.of(mockTypeCode));

        boolean exists = taxJurisdictionTypeCodeRepository.validateTaxJurisdictionTypeCodeExists(1);

        assertTrue(exists, "Tax Jurisdiction Type Code with ID 1 should exist.");
        verify(jpaStreamer, times(1)).stream(TaxJurisdictionTypeCode.class);
    }

    @Test
    void testTaxJurisdictionTypeCodeDuplicate() {
        TaxJurisdictionTypeCode mockTypeCode = new TaxJurisdictionTypeCode(1, "CIGARETTES", "Cigarette Jurisdiction Type", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"),"QKDEV");

        when(jpaStreamer.stream(TaxJurisdictionTypeCode.class)).thenReturn(Stream.of(mockTypeCode));

        boolean exists = taxJurisdictionTypeCodeRepository.validateTaxJurisdictionTypeCodeDuplicate("CIGARETTES");

        assertTrue(exists, "Tax Jurisdiction Type Code CIGARETTES should exists.");
        verify(jpaStreamer, times(1)).stream(TaxJurisdictionTypeCode.class);
    }
}
