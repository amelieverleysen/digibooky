package com.switchfully.digibooky.api.dtos;

import java.time.LocalDate;

public record LendItemDto(String id, String itemId, String memberId, LocalDate dueDate) {
}
