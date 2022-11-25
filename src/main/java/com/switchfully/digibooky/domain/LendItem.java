package com.switchfully.digibooky.domain;

import java.time.LocalDate;
import java.util.UUID;

public class LendItem {
    private final static int WEEKS_BEFORE_DUE = 3;
    private final String id;
    private final String itemId;
    private final String memberId;
    private final LocalDate dueDate;

    public LendItem(String itemId, String memberId) {
        this.id = UUID.randomUUID().toString();
        this.itemId = itemId;
        this.memberId = memberId;
        this.dueDate = LocalDate.now().plusWeeks(WEEKS_BEFORE_DUE);
    }

    public LendItem(String id, String itemId, String memberId) {
        this.id = id;
        this.itemId = itemId;
        this.memberId = memberId;
        this.dueDate = LocalDate.now().plusWeeks(WEEKS_BEFORE_DUE);
    }
    public LendItem(String id, String itemId, String memberId, LocalDate dueDate) {
        this.id = id;
        this.itemId = itemId;
        this.memberId = memberId;
        this.dueDate = dueDate;
    }


    public String getId() {
        return id;
    }

    public String getItemId() {
        return itemId;
    }

    public String getMemberId() {
        return memberId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
