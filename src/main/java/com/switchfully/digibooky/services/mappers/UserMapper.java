package com.switchfully.digibooky.services.mappers;

import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.api.dtos.UserDto;
import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public MemberDto toDTO(Member member) {
        return new MemberDto(member.getId(), member.getName(), member.getSurname(), member.getEmail(), member.getInss(), member.getStreet(), member.getHousenumber(), member.getCity());
    }

    public UserDto toDTO(User user) {
        return new UserDto(user.getId(), user.getName(), user.getSurname(), user.getEmail());
    }
}
