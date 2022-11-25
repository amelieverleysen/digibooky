package com.switchfully.digibooky.domain.repositories;

import com.switchfully.digibooky.domain.LendItem;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class LendingRepository {
    private final Map<String, LendItem> lendingMap = new HashMap<>();

    public LendingRepository() {
        this.lendingMap.put("1", new LendItem("1", "2", "1"));
        this.lendingMap.put("2", new LendItem("2", "3", "1"));
    }

    public LendItem save(LendItem lendItem) {
        lendingMap.put(lendItem.getId(), lendItem);
        return lendItem;
    }

    public boolean removeLendItem(LendItem lendItem){
        lendingMap.remove(lendItem.getId());
        return true;
    }

    public Optional<LendItem> getLendItemById(String returnId) {
        return Optional.ofNullable(lendingMap.get(returnId));
    }
}
