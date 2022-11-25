package com.switchfully.digibooky.services.mappers;

import com.switchfully.digibooky.api.dtos.ReturnLibraryItemDto;
import com.switchfully.digibooky.domain.LendItem;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReturnItemMapper {

    public ReturnLibraryItemDto toDTO(LendItem lendItem) {
        if (LocalDate.now().isAfter(lendItem.getDueDate())){
            return new ReturnLibraryItemDto(lendItem.getId(), false, "Returned passed duedate.");
        }else{
            return new ReturnLibraryItemDto(lendItem.getId(), true, "Return ok.");
        }

    }

}
