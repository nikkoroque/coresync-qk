package org.coresync.app.repository.inventory;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.coresync.app.model.ItemMaster;
import org.coresync.app.model.ItemMaster$;
import org.coresync.app.model.ItemMasterDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ItemMasterRepository {
    @Inject
    JPAStreamer jpaStreamer;

    @Inject
    EntityManager entityManager;

    public List<ItemMasterDTO> getItems() {
        return jpaStreamer.stream(ItemMaster.class)
                .map(item -> new ItemMasterDTO(
                        item.getItemId(),
                        item.getItemDescr(),
                        item.getBuId(),
                        item.getInvClsCd(),
                        item.getStatCd(),
                        item.getPricingCost(),
                        item.getCwtSw(),
                        item.getSplrId()
                ))
                .collect(Collectors.toList());
    }

    public List<ItemMasterDTO> getItemDTO() {
        return jpaStreamer.stream(ItemMaster.class)
                .map(item -> new ItemMasterDTO(
                        item.getItemId(),
                        item.getItemDescr()
                ))
                .collect(Collectors.toList());
    }

    public Optional<ItemMaster> getItemDetail(int id) {
        return jpaStreamer.stream(ItemMaster.class)
                .filter(ItemMaster$.itemId.equal(id))
                .findFirst();
    }

    @Transactional
    public ItemMaster createItem(ItemMaster item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        entityManager.persist(item);
        return item;
    }

    public boolean validateItemExists(int id) {
        return jpaStreamer.stream(ItemMaster.class)
                .anyMatch(ItemMaster$.itemId.equal(id));
    }

    @Transactional
    public ItemMaster updateItem(ItemMaster item) {
        int id = item.getItemId();

        if (!validateItemExists(id)) {
            throw new IllegalArgumentException("Item ID " + id + " does not exist");
        }

        return entityManager.merge(item);
    }

    @Transactional
    public void deleteItem(int id) {
        ItemMaster item = entityManager.find(ItemMaster.class, id);

        if (item == null || !validateItemExists(id)) {
            throw new IllegalArgumentException("Item ID " + id + " does not exist");
        }

        entityManager.remove(item);
    }
}
