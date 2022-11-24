package com.switchfully.digibooky.services;

import com.switchfully.digibooky.api.dtos.CreateMemberDto;
import com.switchfully.digibooky.api.dtos.CreateUserDto;
import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.api.dtos.UserDto;
import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.domain.User;
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
        String error = validateUserInput(new CreateUserDto(createMemberDto.name(), createMemberDto.surname(), createMemberDto.email(), createMemberDto.password()));
        error += validateMemberInput(createMemberDto);
        if (!error.isEmpty()) throw new IllegalArgumentException("Following fields are invalid: " + error);

        Member member = new Member(createMemberDto.name(), createMemberDto.surname(), createMemberDto.email(), Role.MEMBER, createMemberDto.password(), createMemberDto.inss(), createMemberDto.street(), createMemberDto.housenumber(), createMemberDto.city());

        return userMapper.toDTO(userRepository.save(member));
    }

    public UserDto createLibrarian(CreateUserDto createUserDto) {
        String error = validateUserInput(createUserDto);
        if (!error.isEmpty()) throw new IllegalArgumentException("Following fields are invalid: " + error);
        User user = new User(createUserDto.name(), createUserDto.surname(), createUserDto.email(), Role.LIBRARIAN, createUserDto.password());

        return userMapper.toDTO(userRepository.save(user));
    }

    public String validateUserInput(CreateUserDto createUserDto){
        String result="";
        if (createUserDto.name().isEmpty()){
            result+= " name ";
        }
        if (createUserDto.surname().isEmpty()){
            result+= " surname ";
        }
        if (!Helper.checkMail(createUserDto.email())){
            result+= " email ";
        }
        if (createUserDto.password().isEmpty()){
            result+= " password ";
        }
        return result;
    }


    public String validateMemberInput(CreateMemberDto createMemberDto){
        String result = "";
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
}
