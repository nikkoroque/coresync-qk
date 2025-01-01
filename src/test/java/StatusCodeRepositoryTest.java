import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.persistence.EntityManager;
import org.coresync.app.model.StatusCode;
import org.coresync.app.repository.inventory.StatusCodeRepository;
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

public class StatusCodeRepositoryTest {
    @InjectMocks
    private StatusCodeRepository statusCodeRepository;
    @Mock
    private EntityManager entityManager;
    @Mock
    JPAStreamer jpaStreamer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    StatusCode mockStatusCode = new StatusCode(1, "TESTSTAT", OffsetDateTime.now(), "QKDEV",OffsetDateTime.now(), "QKDEV");

    @Test
    void testGetAllStatusCodes() {
        List<StatusCode> mockStatusCodes = List.of(new StatusCode(1, "TESTSTAT", OffsetDateTime.now(), "QKDEV",OffsetDateTime.now(), "QKDEV"), new StatusCode(2, "TESTSTAT2", OffsetDateTime.now(), "QKDEV",OffsetDateTime.now(), "QKDEV"));

        when(jpaStreamer.stream(StatusCode.class)).thenReturn(mockStatusCodes.stream());

        List<StatusCode> result = statusCodeRepository.getAllStatusCodes();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jpaStreamer, times(1)).stream(StatusCode.class);
    }

    @Test
    void testGetStatusCodeDetail() {
        when(jpaStreamer.stream(StatusCode.class)).thenReturn(Stream.of(mockStatusCode));

        Optional<StatusCode> result = statusCodeRepository.getStatusCodeDetail(1);

        assertTrue(result.isPresent());
        assertEquals(mockStatusCode, result.get());
        verify(jpaStreamer, times(1)).stream(StatusCode.class);
    }

    @Test
    void testAddStatusCode() {
        doNothing().when(entityManager).persist(mockStatusCode);

        StatusCode result = statusCodeRepository.addStatusCode(mockStatusCode);

        assertNotNull(result);
        assertEquals(mockStatusCode, result);
        verify(entityManager, times(1)).persist(mockStatusCode);
    }

    @Test
    void testUpdateStatusCode() {
        when(jpaStreamer.stream(StatusCode.class)).thenReturn(Stream.of(mockStatusCode));
        when(entityManager.merge(mockStatusCode)).thenReturn(mockStatusCode);

        StatusCode result = statusCodeRepository.updateStatusCode(mockStatusCode);

        assertNotNull(result);
        assertEquals(mockStatusCode, result);
        verify(entityManager, times(1)).merge(mockStatusCode);
    }

    @Test
    void testDeleteStatusCode() {
        when(jpaStreamer.stream(StatusCode.class)).thenReturn(Stream.of(mockStatusCode));
        when(entityManager.find(StatusCode.class, 1)).thenReturn(mockStatusCode);

        doNothing().when(entityManager).remove(mockStatusCode);

        statusCodeRepository.deleteStatusCode(1);
        verify(entityManager, times(1)).remove(mockStatusCode);
    }

    @Test
    void testValidateStatusCodeExists() {
        when(jpaStreamer.stream(StatusCode.class)).thenReturn(Stream.of(mockStatusCode));

        boolean exists = statusCodeRepository.validateStatusCodeExists(1);

        assertTrue(exists, "Status code with ID 1 exist.");
        verify(jpaStreamer, times(1)).stream(StatusCode.class);
    }

    @Test
    void testValidateStatusCodeDuplicate() {
        when(jpaStreamer.stream(StatusCode.class)).thenReturn(Stream.of(mockStatusCode));

        boolean exists = statusCodeRepository.validateStatusCodeDuplicate("TESTSTAT");

        assertTrue(exists, "Status Code TESTSTAT exists.");
        verify(jpaStreamer, times(1)).stream(StatusCode.class);
    }

}
