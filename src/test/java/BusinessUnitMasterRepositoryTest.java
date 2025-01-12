import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.persistence.EntityManager;
import org.coresync.app.model.BusinessUnitMaster;
import org.coresync.app.repository.inventory.BusinessUnitMasterRepository;
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

public class BusinessUnitMasterRepositoryTest {

    @InjectMocks
    private BusinessUnitMasterRepository businessUnitRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private JPAStreamer jpaStreamer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    BusinessUnitMaster mockBusinessUnit = new BusinessUnitMaster();

    {
        mockBusinessUnit.setId(1);
        mockBusinessUnit.setBusinessUnitCode("BU001");
        mockBusinessUnit.setBusinessUnitName("Test Business Unit");
        mockBusinessUnit.setAddress("123 Business St");
        mockBusinessUnit.setContactNumber("123-456-7890");
        mockBusinessUnit.setEmail("test@business.com");
        mockBusinessUnit.setTaxIdentificationNumber("TIN12345");
        mockBusinessUnit.setJurisdictionId(100);
        mockBusinessUnit.setCreationDate(OffsetDateTime.now());
        mockBusinessUnit.setCreatedByUser("TestUser");
        mockBusinessUnit.setLastUpdateDate(OffsetDateTime.now());
        mockBusinessUnit.setLastUpdatedByUser("TestUser");
    }

    @Test
    void testGetBusinessUnits() {
        List<BusinessUnitMaster> mockUnits = List.of(
                mockBusinessUnit,
                new BusinessUnitMaster() {{
                    setId(2);
                    setBusinessUnitCode("BU002");
                    setBusinessUnitName("Another Business Unit");
                    setAddress("456 Another St");
                    setContactNumber("987-654-3210");
                    setEmail("another@business.com");
                    setTaxIdentificationNumber("TIN67890");
                    setJurisdictionId(200);
                    setCreationDate(OffsetDateTime.now());
                    setCreatedByUser("User2");
                    setLastUpdateDate(OffsetDateTime.now());
                    setLastUpdatedByUser("User2");
                }}
        );

        when(jpaStreamer.stream(BusinessUnitMaster.class)).thenReturn(mockUnits.stream());

        List<BusinessUnitMaster> result = businessUnitRepository.getBusinessUnits();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jpaStreamer, times(1)).stream(BusinessUnitMaster.class);
    }

    @Test
    void testGetBusinessUnitDetail() {
        when(jpaStreamer.stream(BusinessUnitMaster.class)).thenReturn(Stream.of(mockBusinessUnit));

        Optional<BusinessUnitMaster> result = businessUnitRepository.getBusinessUnitDetail(1);

        assertTrue(result.isPresent());
        assertEquals(mockBusinessUnit, result.get());
        verify(jpaStreamer, times(1)).stream(BusinessUnitMaster.class);
    }

    @Test
    void testCreateBusinessUnit() {
        doNothing().when(entityManager).persist(mockBusinessUnit);

        BusinessUnitMaster result = businessUnitRepository.createBusinessUnit(mockBusinessUnit);

        assertNotNull(result);
        assertEquals(mockBusinessUnit, result);
        verify(entityManager, times(1)).persist(mockBusinessUnit);
    }

    @Test
    void testUpdateBusinessUnit() {
        when(jpaStreamer.stream(BusinessUnitMaster.class)).thenReturn(Stream.of(mockBusinessUnit));
        when(entityManager.merge(mockBusinessUnit)).thenReturn(mockBusinessUnit);

        BusinessUnitMaster result = businessUnitRepository.updateBusinessUnit(mockBusinessUnit);

        assertNotNull(result);
        assertEquals(mockBusinessUnit, result);
        verify(entityManager, times(1)).merge(mockBusinessUnit);
    }

    @Test
    void testDeleteBusinessUnit() {
        when(jpaStreamer.stream(BusinessUnitMaster.class)).thenReturn(Stream.of(mockBusinessUnit));
        when(entityManager.find(BusinessUnitMaster.class, 1)).thenReturn(mockBusinessUnit);

        doNothing().when(entityManager).remove(mockBusinessUnit);

        businessUnitRepository.deleteBusinessUnit(1);

        verify(entityManager, times(1)).remove(mockBusinessUnit);
    }

    @Test
    void testValidateBusinessUnitExists() {
        when(jpaStreamer.stream(BusinessUnitMaster.class)).thenReturn(Stream.of(mockBusinessUnit));

        boolean exists = businessUnitRepository.validateBusinessUnitExists(1);

        assertTrue(exists, "Business Unit with ID 1 exists.");
        verify(jpaStreamer, times(1)).stream(BusinessUnitMaster.class);
    }

    @Test
    void testValidateBusinessUnitDuplicate() {
        when(jpaStreamer.stream(BusinessUnitMaster.class)).thenReturn(Stream.of(mockBusinessUnit));

        boolean exists = businessUnitRepository.validateBusinessUnitDuplicate("BU001");

        assertTrue(exists, "Business Unit with Code 'BU001' exists.");
        verify(jpaStreamer, times(1)).stream(BusinessUnitMaster.class);
    }
}
