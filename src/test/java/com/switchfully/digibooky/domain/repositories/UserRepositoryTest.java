package com.switchfully.digibooky.domain.repositories;

import com.switchfully.digibooky.domain.City;
import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.domain.User;
import com.switchfully.digibooky.domain.security.Role;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UserRepositoryTest {

    private UserRepository userRepository = new UserRepository();
    private Member memberToSave;
    private User userToSave;
    private Map<String, User> testUserMap;

    @Nested
    @DisplayName("Member tests")
    public class MemberTests {
        @BeforeEach
        void initTestData(){
            memberToSave = new Member("Test", "Tester", "test@gmail.com", Role.MEMBER, "pwd",  "90.03.01-997-04", "Kurtstraaat", "25", new City("2000", "Antwerpen"));
        }

        @Test
        void whenCreateNewUser_userIsAddedToRepository() {
            //GIVEN WHEN
            Member savedMember = userRepository.save(memberToSave);

            //THEN
            Assertions.assertEquals(memberToSave, savedMember);
        }

        @Test
        void whenANewUserThatAllreadyExists_thenExceptionThrowsIlligalArgument() {
            //GIVEN WHEN
            Member savedMember = userRepository.save(memberToSave);

            //THEN
            Throwable exception = assertThrows(IllegalArgumentException.class, ()->userRepository.save(memberToSave));
            Assertions.assertEquals(savedMember.getName()+ " " + savedMember.getSurname() + " already exists.", exception.getMessage());
        }

        @Test
        void whenAEmailAllreadyExists_thenExceptionThrowsIlligalArgument() {
            //GIVEN WHEN
            Member savedMember = userRepository.save(memberToSave);
            Member memberEmail = new Member("Emailnaam", "Tester", "test@gmail.com", Role.MEMBER, "pwd",  "90.03.01-997-05", "Kurtstraaat", "25", new City("2000", "Antwerpen"));

            //THEN
            Throwable exception = assertThrows(IllegalArgumentException.class, ()->userRepository.save(memberEmail));
            Assertions.assertEquals("This email adress already exists.", exception.getMessage());
        }

        @Test
        void whenAInssAllreadyExists_thenExceptionThrowsIlligalArgument() {
            //GIVEN WHEN
            Member savedMember = userRepository.save(memberToSave);
            Member memberInss = new Member("Emailnaam", "Tester", "Emailnaam@gmail.com", Role.MEMBER, "pwd",  "90.03.01-997-04", "Kurtstraaat", "25", new City("2000", "Antwerpen"));

            //THEN
            Throwable exception = assertThrows(IllegalArgumentException.class, ()->userRepository.save(memberInss));
            Assertions.assertEquals("This inss already exists.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("User tests")
    public class UserTests {
        @BeforeEach
        void initTestData(){
            userToSave = new User("Test", "Tester", "test@gmail.com", Role.MEMBER, "pwd");
        }
    }

}