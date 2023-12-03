package com.ciosmak.automotivepartner.repository;

import com.ciosmak.automotivepartner.user.domain.User;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest
{
    private static int numberOfUsers;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp()
    {
        List<User> users = new ArrayList<>();
        users.add(User.builder().firstName("TestA").lastName("TestQ").email("test.a@example.com").password("Test123_").phoneNumber("123456789").role(Role.DRIVER).build());
        users.add(User.builder().firstName("TestB").lastName("TestW").email("test.b@example.com").password("Test123_").phoneNumber("123456789").role(Role.DRIVER).isBlocked(Boolean.TRUE).build());
        users.add(User.builder().firstName("TestC").lastName("TestE").email("test.c@example.com").password("Test123_").phoneNumber("123456789").role(Role.ADMIN).build());
        users.add(User.builder().firstName("TestD").lastName("TestR").email("test.d@example.com").password("Test123_").phoneNumber("123456789").role(Role.SUPER_ADMIN).build());
        userRepository.saveAll(users);
        numberOfUsers = users.size();
    }

    @Test
    public void shouldSaveUser()
    {
        User savedUser = userRepository.save(User.builder().firstName("TestX").lastName("TestY").email("test.x@example.com").password("Test123_").phoneNumber("123456789").role(Role.DRIVER).build());
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getFirstName()).isEqualTo("TestX");
        Assertions.assertThat(savedUser.getLastName()).isEqualTo("TestY");
        Assertions.assertThat(savedUser.getEmail()).isEqualTo("test.x@example.com");
        Assertions.assertThat(savedUser.getPhoneNumber()).isEqualTo("123456789");
        Assertions.assertThat(savedUser.getRole()).isEqualTo(Role.DRIVER);
    }

    @ParameterizedTest
    @ValueSource(strings = {"test.a@example.com", "test.b@example.com", "test.c@example.com", "test.d@example.com"})
    public void shouldFindUserByEmailWhenUserWithThisEmailIsInDatabase(String email)
    {
        Optional<User> foundUser = userRepository.findByEmail(email);

        Assertions.assertThat(foundUser).isNotEmpty();
    }

    @Test
    public void shouldNotFindUserByEmailWhenUserWithThisEmailIsNotInDatabase()
    {
        Optional<User> foundUser = userRepository.findByEmail("nonexisting@example.com");

        Assertions.assertThat(foundUser).isEmpty();
    }

    @Test
    public void shouldFindAllUsersWhenUsersAreInDatabase()
    {
        List<User> foundUsers = userRepository.findAll();

        Assertions.assertThat(foundUsers).isNotEmpty();
        Assertions.assertThat(foundUsers.size()).isEqualTo(4);
    }

    @Test
    public void shouldFindZeroUsersWhenUsersAreNotInDatabase()
    {
        userRepository.deleteAll();

        List<User> foundUsers = userRepository.findAll();

        Assertions.assertThat(foundUsers).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({
            "true, 1",
            "false, 3"
    })
    public void shouldFindAllUsersByIsBlockedWhenThoseUsersAreInDatabase(boolean isBlocked, int amount)
    {
        List<User> foundUsers = userRepository.findAllByIsBlocked(isBlocked);

        Assertions.assertThat(foundUsers.size()).isEqualTo(amount);
    }

    @Test
    public void shouldFindZeroUsersByIsBlockedTrueWhenThoseUsersAreNotInDatabase()
    {
        userRepository.deleteAll();

        loadUnblockedUsers();

        List<User> foundUsers = userRepository.findAllByIsBlocked(true);

        Assertions.assertThat(foundUsers).isEmpty();
    }

    private void loadUnblockedUsers()
    {
        List<User> users = new ArrayList<>();
        users.add(User.builder().firstName("TestX").lastName("TestP").email("test.x@example.com").password("Test123_").phoneNumber("123456789").role(Role.DRIVER).build());
        users.add(User.builder().firstName("TestY").lastName("TestQ").email("test.y@example.com").password("Test123_").phoneNumber("123456789").role(Role.SUPER_ADMIN).build());
        userRepository.saveAll(users);
    }

    @Test
    public void shouldFindZeroUsersByIsBlockedFalseWhenThoseUsersAreNotInDatabase()
    {
        userRepository.deleteAll();

        loadBlockedUsers();

        List<User> foundUsers = userRepository.findAllByIsBlocked(false);

        Assertions.assertThat(foundUsers).isEmpty();
    }

    private void loadBlockedUsers()
    {
        List<User> users = new ArrayList<>();
        users.add(User.builder().firstName("TestX").lastName("TestP").email("test.x@example.com").password("Test123_").phoneNumber("123456789").role(Role.DRIVER).isBlocked(Boolean.TRUE).build());
        users.add(User.builder().firstName("TestY").lastName("TestQ").email("test.y@example.com").password("Test123_").phoneNumber("123456789").role(Role.SUPER_ADMIN).isBlocked(Boolean.TRUE).build());
        userRepository.saveAll(users);
    }

    @ParameterizedTest
    @CsvSource({
            "DRIVER, 2",
            "ADMIN, 1",
            "SUPER_ADMIN, 1"
    })
    public void shouldFindAllUsersByRoleWhenUsersWithThatRoleIsInDatabase(Role role, int expectedAmountOfUser)
    {
        List<User> foundUsers = userRepository.findAllByRole(role);

        Assertions.assertThat(foundUsers.size()).isEqualTo(expectedAmountOfUser);
    }

    @Test
    public void shouldReturnIsUserBlocked()
    {
        List<User> users = userRepository.findAll();

        for (var user : users)
        {
            boolean isUserBlocked = userRepository.isBlocked(user.getId());

            Assertions.assertThat(isUserBlocked).isEqualTo(user.getIsBlocked());
        }
    }

    @Test
    public void shouldReturnUserRole()
    {
        List<User> users = userRepository.findAll();

        for (var user : users)
        {
            Role userRole = userRepository.getRole(user.getId());

            Assertions.assertThat(userRole).isEqualTo(user.getRole());
        }
    }

    @Test
    public void shouldFindUserByIdWhenUserWithThisIdIsInDatabase()
    {
        long firstId = userRepository.findAll().get(0).getId();
        long lastId = firstId + numberOfUsers;
        for (long i = firstId; i < lastId; ++i)
        {
            Optional<User> foundSettlement = userRepository.findById(i);

            Assertions.assertThat(foundSettlement).isNotEmpty();
        }
    }

    @Test
    public void shouldNotFindUserByIdWhenUserWithThisIdIsNotInDatabase()
    {
        for (long i = 999L; i < numberOfUsers; ++i)
        {
            Optional<User> foundSettlement = userRepository.findById(i);
            Assertions.assertThat(foundSettlement).isEmpty();
        }
    }

    @Test
    public void shouldDeleteUserByIdWhenUserWithThisIdIsInDatabase()
    {
        long firstId = userRepository.findAll().get(0).getId();
        long lastId = firstId + numberOfUsers;
        for (long i = firstId; i < lastId; ++i)
        {
            userRepository.deleteById(i);
            Optional<User> foundEmail = userRepository.findById(i);

            Assertions.assertThat(foundEmail).isEmpty();
        }
    }
}
