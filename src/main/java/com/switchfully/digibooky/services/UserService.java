package com.switchfully.digibooky.services;

import com.switchfully.digibooky.api.dtos.CreateMemberDto;
import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.domain.repositories.UserRepository;
import com.switchfully.digibooky.domain.security.Role;
import com.switchfully.digibooky.services.mappers.UserMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    private UserService x;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public MemberDto createMember(CreateMemberDto createMemberDto) throws IllegalArgumentException{
        String error = validateInput(createMemberDto);
        if (!error.isEmpty()) throw new IllegalArgumentException("Following fields are invalid: " + error);

        Member member = new Member(createMemberDto.getName(), createMemberDto.getSurname(), createMemberDto.getEmail(), Role.MEMBER, createMemberDto.getPassword(), createMemberDto.getINSS(), createMemberDto.getStreet(), createMemberDto.getHousenumber(), createMemberDto.getCity());

        return userMapper.toDTO(userRepository.save(member));
    }

    public String validateInput(CreateMemberDto createMemberDto){
        String result="";
        if (createMemberDto.getName().isEmpty()){
            result+= " name ";
        }
        if (createMemberDto.getSurname().isEmpty()){
            result+= " surname ";
        }
        if (!Helper.checkMail(createMemberDto.getEmail())){
            result+= " email ";
        }
        if (createMemberDto.getPassword().isEmpty()){
            result+= " password ";
        }
        if (createMemberDto.getINSS().isEmpty()){
            result+= " INSS ";
        }
        if (createMemberDto.getStreet().isEmpty()){
            result+= " street ";
        }
        if (createMemberDto.getCity() == null){
            result+= " city ";
        }
        return result;
    }
}
