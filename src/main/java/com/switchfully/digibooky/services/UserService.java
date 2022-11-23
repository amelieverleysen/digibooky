package com.switchfully.digibooky.services;

import com.switchfully.digibooky.api.dtos.CreateMemberDto;
import com.switchfully.digibooky.api.dtos.MemberDto;
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
        Member member = new Member(createMemberDto.getName(), createMemberDto.getSurname(), createMemberDto.getEmail(), Role.MEMBER, createMemberDto.getPassword(), createMemberDto.getINSS(), createMemberDto.getStreet(), createMemberDto.getHousenumber(), createMemberDto.getCity());
        return userMapper.toDto(member);
    }
}
