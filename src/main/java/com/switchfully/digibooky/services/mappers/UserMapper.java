package com.switchfully.digibooky.services.mappers;

import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.api.dtos.UserDto;
import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.domain.User;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public MemberDto toDTO(Member member) {
        return new MemberDto(member.getId(), member.getName(), member.getSurname(), member.getEmail(), member.getInss(), member.getStreet(), member.getHousenumber(), member.getCity());
    }

    public UserDto toDTO(User user) {
        return new UserDto(user.getId(), user.getName(), user.getSurname(), user.getEmail());
    }

    private <S> Object toDTO(S user) {
        if (user instanceof Member memberType){
            return toDTO(memberType);
        }
        if (user instanceof User userType){
            return toDTO(userType);
        }
        throw new IllegalArgumentException("Type error");
    }

    public <S> List<Object> toDTO(List<S> users) {
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }

}
