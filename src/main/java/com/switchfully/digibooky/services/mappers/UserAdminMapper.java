package com.switchfully.digibooky.services.mappers;

import com.switchfully.digibooky.api.dtos.MemberAdminDto;
import com.switchfully.digibooky.domain.Member;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserAdminMapper {
    public MemberAdminDto toDTO(Member member) {
        return new MemberAdminDto(member.getId(), member.getName(), member.getSurname(), member.getEmail(), member.getStreet(), member.getHousenumber(), member.getCity());
    }

    public List<MemberAdminDto> toDTO(List<Member> users) {
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
