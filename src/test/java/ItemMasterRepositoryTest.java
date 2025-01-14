import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.persistence.EntityManager;
import org.coresync.app.model.ItemMaster;
import org.coresync.app.model.ItemMasterDTO;
import org.coresync.app.repository.inventory.ItemMasterRepository;
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

public class ItemMasterRepositoryTest {

    @InjectMocks
    private ItemMasterRepository itemMasterRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private JPAStreamer jpaStreamer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private final ItemMaster mockItemMaster = new ItemMaster(
            0, "X02", "K001", "111", "A", 2, 2, 4, 1, "N", "EA", 2, 1234567890,
            "G123456789", 1234567890, "Test1", "http://asdqowe.coasd.image.link",
            "Y", 9.99, "Eaches", 10, 1, 1.5, 1.5, 1.0, "N", 0, "Y", 5, 5, 1, 20, 20,
            20, 20, 20, 20, OffsetDateTime.now(), "QKDEV", OffsetDateTime.now(), "QKDEV"
    );

    @Test
    void testGetItems() {
        when(jpaStreamer.stream(ItemMaster.class)).thenReturn(Stream.of(mockItemMaster));

        List<ItemMasterDTO> result = itemMasterRepository.getItems();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test1", result.get(0).getItemDesc());
        verify(jpaStreamer, times(1)).stream(ItemMaster.class);
    }

    @Test
    void testGetItemDTO() {
        when(jpaStreamer.stream(ItemMaster.class)).thenReturn(Stream.of(mockItemMaster));

        List<ItemMasterDTO> result = itemMasterRepository.getItemDTO();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test1", result.get(0).getItemDesc());
        assertNull(result.get(0).getBuId());
        verify(jpaStreamer, times(1)).stream(ItemMaster.class);
    }

    @Test
    void testCreateItem() {
        doNothing().when(entityManager).persist(mockItemMaster);

        ItemMaster result = itemMasterRepository.createItem(mockItemMaster);

        assertNotNull(result);
        assertEquals(mockItemMaster, result);
        verify(entityManager, times(1)).persist(mockItemMaster);
    }

    @Test
    void testUpdateItem() {
        when(jpaStreamer.stream(ItemMaster.class)).thenReturn(Stream.of(mockItemMaster));
        when(entityManager.merge(mockItemMaster)).thenReturn(mockItemMaster);

        ItemMaster result = itemMasterRepository.updateItem(mockItemMaster);

        assertNotNull(result);
        assertEquals(mockItemMaster, result);
        verify(entityManager, times(1)).merge(mockItemMaster);
    }

    @Test
    void testDeleteItem() {
        when(jpaStreamer.stream(ItemMaster.class)).thenReturn(Stream.of(mockItemMaster));
        when(entityManager.find(ItemMaster.class, 0)).thenReturn(mockItemMaster);

        doNothing().when(entityManager).remove(mockItemMaster);

        itemMasterRepository.deleteItem(0);

        verify(entityManager, times(1)).remove(mockItemMaster);
    }

    @Test
    void testValidateItemExists() {
        when(jpaStreamer.stream(ItemMaster.class)).thenReturn(Stream.of(mockItemMaster));

        boolean exists = itemMasterRepository.validateItemExists(0);

        assertTrue(exists, "Item with ID 0 exists.");
        verify(jpaStreamer, times(1)).stream(ItemMaster.class);
    }

}
