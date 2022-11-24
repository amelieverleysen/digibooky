package com.switchfully.digibooky.domain.repositories;

import com.switchfully.digibooky.domain.LendItem;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class LendingRepository {
    private Map<String, LendItem> lendingMap = new HashMap<>();

    public LendingRepository() {
        this.lendingMap.put("1", new LendItem("1", "2", "1"));
        this.lendingMap.put("2", new LendItem("2", "3", "1"));
    }

    public LendItem save(LendItem lendItem) {
        lendingMap.put(lendItem.getId(), lendItem);
        return lendItem;
    }
}
