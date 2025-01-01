import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.persistence.EntityManager;
import org.coresync.app.model.SupplierMaster;
import org.coresync.app.repository.inventory.SupplierMasterRepository;
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

public class SupplierMasterRepositoryTest {
    @InjectMocks
    private SupplierMasterRepository supplierMasterRepository;
    @Mock
    private EntityManager entityManager;
    @Mock
    private JPAStreamer jpaStreamer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    SupplierMaster mockSupplier = new SupplierMaster(1, "111", "Kraft Corp", "Suite 305", "Gardena", "CA", "90234-09829", "USA", "Y", "18009584665", "email@email.com", "458658245SC", "ACTIVE", 10, 5, 10, "15496875461231", OffsetDateTime.now(), "QKDEV",OffsetDateTime.now(), "QKDEV");

    @Test
    void testGetAllSuppliers() {
        List<SupplierMaster> mockSuppliers = List.of(new SupplierMaster(1, "111", "Kraft Corp", "Gardena CA", "Gardena", "CA", "90234-09829", "USA", "Y", "18009584665", "email@email.com", "458658245SC", "ACTIVE", 10, 5, 10, "15496875461231", OffsetDateTime.now(), "QKDEV",OffsetDateTime.now(), "QKDEV"),new SupplierMaster(2, "222", "Mars Corp", "Gardena CA", "Gardena", "CA", "90234-09829", "USA", "Y", "18009584665", "email@email.com", "458658245SC", "ACTIVE", 10, 5, 10, "15496875461231", OffsetDateTime.now(), "QKDEV",OffsetDateTime.now(), "QKDEV"));

        when(jpaStreamer.stream(SupplierMaster.class)).thenReturn(mockSuppliers.stream());
        List<SupplierMaster> result = supplierMasterRepository.getAllSuppliers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jpaStreamer, times(1)).stream(SupplierMaster.class);
    }

    @Test
    void testGetSupplierDetail() {
        when(jpaStreamer.stream(SupplierMaster.class)).thenReturn(Stream.of(mockSupplier));

        Optional<SupplierMaster> result = supplierMasterRepository.getSupplierDetail(1);

        assertTrue(result.isPresent());
        assertEquals(mockSupplier, result.get());
        verify(jpaStreamer, times(1)).stream(SupplierMaster.class);
    }

    @Test
    void testAddSupplier() {
        doNothing().when(entityManager).persist(mockSupplier);

        SupplierMaster result = supplierMasterRepository.addSupplier(mockSupplier);

        assertNotNull(result);
        assertEquals(mockSupplier, result);
        verify(entityManager, times(1)).persist(mockSupplier);
    }

    @Test
    void testUpdateSupplier() {
        when(jpaStreamer.stream(SupplierMaster.class)).thenReturn(Stream.of(mockSupplier));
        when(entityManager.merge(mockSupplier)).thenReturn(mockSupplier);

        SupplierMaster result = supplierMasterRepository.updateSupplier(mockSupplier);

        assertNotNull(result);
        assertEquals(mockSupplier, result);
        verify(entityManager, times(1)).merge(mockSupplier);
    }

    @Test
    void testDeleteSupplier() {
        when(jpaStreamer.stream(SupplierMaster.class)).thenReturn(Stream.of(mockSupplier));
        when(entityManager.find(SupplierMaster.class, 1)).thenReturn(mockSupplier);

        doNothing().when(entityManager).remove(mockSupplier);

        supplierMasterRepository.deleteSupplier(1);
        verify(entityManager, times(1)).remove(mockSupplier);
    }

    @Test
    void testSupplierExists() {
        when(jpaStreamer.stream(SupplierMaster.class)).thenReturn(Stream.of(mockSupplier));

        boolean exists = supplierMasterRepository.validateSupplierExists(1);

        assertTrue(exists, "Supplier with ID 1 should exist.");
        verify(jpaStreamer, times(1)).stream(SupplierMaster.class);
    }

    @Test
    void testSupplierCodeDuplicate() {
        when(jpaStreamer.stream(SupplierMaster.class)).thenReturn(Stream.of(mockSupplier));

        boolean exists = supplierMasterRepository.validateSupplierDuplicate("111");

        assertTrue(exists, "Supplier Code 111 should exists.");
        verify(jpaStreamer, times(1)).stream(SupplierMaster.class);
    }
}
