package com.switchfully.digibooky.api.dtos;

import java.time.LocalDate;

public record LendItemOverdueDto(String id, String title, LocalDate dueDate){
}
