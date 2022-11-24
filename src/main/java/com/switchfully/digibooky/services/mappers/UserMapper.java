package com.switchfully.digibooky.services.mappers;

import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.domain.Member;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public MemberDto toDTO(Member member) {
        return new MemberDto(member.getId(), member.getName(), member.getSurname(), member.getEmail(), member.getInss(), member.getStreet(), member.getHousenumber(), member.getCity());
    }

    public List<MemberDto> toDTO(List<Member> members) {
        return members.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
