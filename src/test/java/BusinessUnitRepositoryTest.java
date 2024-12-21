import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.persistence.EntityManager;
import org.coresync.app.model.BusinessUnit;
import org.coresync.app.repository.BusinessUnitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BusinessUnitRepositoryTest {

    @InjectMocks
    private BusinessUnitRepository businessUnitRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private JPAStreamer jpaStreamer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBusinessUnits() {
        List<BusinessUnit> mockUnits = List.of(
                new BusinessUnit(1, "Unit 1", "GLN1", "City1", "State1", "Zip1", "Country1"),
                new BusinessUnit(2, "Unit 2", "GLN2", "City2", "State2", "Zip2", "Country2")
        );

        when(jpaStreamer.stream(BusinessUnit.class)).thenReturn(mockUnits.stream());

        List<BusinessUnit> result = businessUnitRepository.getAllBusinessUnits();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jpaStreamer, times(1)).stream(BusinessUnit.class);
    }

    @Test
    void testGetPaginatedBusinessUnit() {
        List<BusinessUnit> mockUnits = List.of(
                new BusinessUnit(1, "Unit 1", "GLN1", "City1", "State1", "Zip1", "Country1"),
                new BusinessUnit(2, "Unit 2", "GLN2", "City2", "State2", "Zip2", "Country2"),
                new BusinessUnit(3, "Unit 3", "GLN3", "City3", "State3", "Zip3", "Country3")
        );

        when(jpaStreamer.stream(BusinessUnit.class)).thenReturn(mockUnits.stream());

        Stream<BusinessUnit> result = businessUnitRepository.getPaginatedBusinessUnit(1, "buid", "asc");

        assertNotNull(result);
        assertEquals(3, result.count());
        verify(jpaStreamer, times(1)).stream(BusinessUnit.class);
    }

    @Test
    void testFilterBusinessUnits() {
        List<BusinessUnit> mockUnits = List.of(
                new BusinessUnit(1, "Unit 1", "GLN1", "City1", "State1", "Zip1", "Country1"),
                new BusinessUnit(2, "Unit 2", "GLN2", "City2", "State2", "Zip2", "Country2")
        );

        when(jpaStreamer.stream(BusinessUnit.class)).thenReturn(mockUnits.stream());

        Stream<BusinessUnit> result = businessUnitRepository.filterBusinessUnits(
                null, "Unit", null, null, null, null, null);

        assertNotNull(result);
        assertEquals(2, result.count());
        verify(jpaStreamer, times(1)).stream(BusinessUnit.class);
    }

    @Test
    void testGetBusinessUnitDetail() {
        BusinessUnit mockUnit = new BusinessUnit(1, "Unit 1", "GLN1", "City1", "State1", "Zip1", "Country1");

        when(jpaStreamer.stream(BusinessUnit.class)).thenReturn(Stream.of(mockUnit));

        Optional<BusinessUnit> result = businessUnitRepository.getBusinessUnitDetail(1);

        assertTrue(result.isPresent());
        assertEquals(mockUnit, result.get());
        verify(jpaStreamer, times(1)).stream(BusinessUnit.class);
    }

    @Test
    void testAddBusinessUnit() {
        BusinessUnit newUnit = new BusinessUnit(1, "Unit 1", "GLN1", "City1", "State1", "Zip1", "Country1");

        doNothing().when(entityManager).persist(newUnit);

        BusinessUnit result = businessUnitRepository.addBusinessUnit(newUnit);

        assertNotNull(result);
        assertEquals(newUnit, result);
        verify(entityManager, times(1)).persist(newUnit);
    }

    @Test
    void testUpdateBusinessUnit() {
        BusinessUnit existingUnit = new BusinessUnit(1, "Unit 1", "GLN1", "City1", "State1", "Zip1", "Country1");

        when(entityManager.find(BusinessUnit.class, 1)).thenReturn(existingUnit);
        when(entityManager.merge(existingUnit)).thenReturn(existingUnit);

        BusinessUnit result = businessUnitRepository.updateBusinessUnit(existingUnit);

        assertNotNull(result);
        assertEquals(existingUnit, result);
        verify(entityManager, times(1)).merge(existingUnit);
    }

    @Test
    void testDeleteBusinessUnit() {
        BusinessUnit mockUnit = new BusinessUnit(1, "Unit 1", "GLN1", "City1", "State1", "Zip1", "Country1");

        when(entityManager.find(BusinessUnit.class, 1)).thenReturn(mockUnit);
        doNothing().when(entityManager).remove(mockUnit);

        businessUnitRepository.deleteBusinessUnit(1);

        verify(entityManager, times(1)).remove(mockUnit);
    }

    @Test
    void testBusinessUnitExists() {
        BusinessUnit mockUnit = new BusinessUnit(1, "Unit 1", "GLN1", "City1", "State1", "Zip1", "Country1");

        when(entityManager.find(BusinessUnit.class, 1)).thenReturn(mockUnit);

        boolean exists = businessUnitRepository.businessUnitExists(1);

        assertTrue(exists);
        verify(entityManager, times(1)).find(BusinessUnit.class, 1);
    }
}
