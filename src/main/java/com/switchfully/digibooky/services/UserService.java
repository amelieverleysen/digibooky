package com.switchfully.digibooky.services;

import com.switchfully.digibooky.api.dtos.CreateMemberDto;
import com.switchfully.digibooky.api.dtos.CreateUserDto;
import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.api.dtos.UserDto;
import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.domain.repositories.UserRepository;
import com.switchfully.digibooky.domain.security.Role;
import com.switchfully.digibooky.services.mappers.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public MemberDto createMember(CreateMemberDto createMemberDto) throws IllegalArgumentException{
        String error = validateMemberInput(createMemberDto);
        if (!error.isEmpty()) throw new IllegalArgumentException("Following fields are invalid: " + error);

        Member member = new Member(createMemberDto.name(), createMemberDto.surname(), createMemberDto.email(), Role.MEMBER, createMemberDto.password(), createMemberDto.inss(), createMemberDto.street(), createMemberDto.housenumber(), createMemberDto.city());

        return userMapper.toDTO(userRepository.save(member));
    }

    public UserDto createUser(CreateUserDto createUserDto) {
        return null;
    }

    public String validateMemberInput(CreateMemberDto createMemberDto){
        String result="";
        if (createMemberDto.name().isEmpty()){
            result+= " name ";
        }
        if (createMemberDto.surname().isEmpty()){
            result+= " surname ";
        }
        if (!Helper.checkMail(createMemberDto.email())){
            result+= " email ";
        }
        if (createMemberDto.password().isEmpty()){
            result+= " password ";
        }
        if (createMemberDto.inss().isEmpty()){
            result+= " INSS ";
        }
        if (createMemberDto.street().isEmpty()){
            result+= " street ";
        }
        if (createMemberDto.city() == null){
            result+= " city ";
        }
        return result;
    }

    public String validateuserrInput(CreateUserDto createUserDto){
        String result="";
        if (createUserDto.getName().isEmpty()){
            result+= " name ";
        }
        if (createUserDto.getSurname().isEmpty()){
            result+= " surname ";
        }
        if (!Helper.checkMail(createUserDto.getEmail())){
            result+= " email ";
        }
        if (createUserDto.getPassword().isEmpty()){
            result+= " password ";
        }
        return result;
    }
}
