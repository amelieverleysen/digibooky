package com.switchfully.digibooky.services.mappers;

import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.domain.Member;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public MemberDto toDTO(Member member) {
        return new MemberDto()
                .setId(member.getId())
                .setName(member.getName())
                .setSurname(member.getSurname())
                .setEmail(member.getEmail())
                .setStreet(member.getStreet())
                .setHousenumber(member.getHousenumber())
                .setCity(member.getCity());
    }

    public List<MemberDto> toDTO(List<Member> professors) {
        return professors.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
