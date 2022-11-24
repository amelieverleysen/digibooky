package com.switchfully.digibooky.services.mappers;

import com.switchfully.digibooky.api.dtos.LendItemDto;
import com.switchfully.digibooky.domain.LendItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LendingMapper {
    public LendItemDto toDTO(LendItem lendItem) {
        return new LendItemDto(lendItem.getId(), lendItem.getItemId(), lendItem.getMemberId(), lendItem.getDueDate());
    }

    public List<LendItemDto> toDTO(List<LendItem> lendItems) {
        return lendItems.stream().map(this::toDTO).collect(Collectors.toList());
    }

}
