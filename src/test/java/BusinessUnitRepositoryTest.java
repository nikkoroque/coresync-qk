import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.coresync.app.model.BusinessUnit;
import org.coresync.app.repository.BusinessUnitRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class BusinessUnitRepositoryTest {
    @Inject
    BusinessUnitRepository businessUnitRepository;

    @Test
    public void test() {
        Optional<BusinessUnit> businessUnit = businessUnitRepository.getBusinessUnitDetail( 345);
        assertTrue(businessUnit.isPresent());
        assertEquals("Tejon", businessUnit.get().getBuDesc());
    }
}
