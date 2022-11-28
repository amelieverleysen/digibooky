package com.switchfully.digibooky.domain.repositories;

import com.switchfully.digibooky.domain.LendItem;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class LendingRepository {
    private final Map<String, LendItem> lendingMap = new HashMap<>();

    public LendingRepository() {
        this.lendingMap.put("1", new LendItem("1", "2", "1",  LocalDate.of(2022, 6, 20)));
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

    public List<LendItem> getAllLendItems() {return new ArrayList<>(lendingMap.values());}

    public Optional<LendItem> getLendItemById(String returnId) {
        return Optional.ofNullable(lendingMap.get(returnId));
    }

    public Map<String, LendItem> getLendingMap() {
        return lendingMap;
    }
}
