package by.vorobyov.travelagency.repository.impl.jpa;

import by.vorobyov.travelagency.configuration.TestConfiguration;
import by.vorobyov.travelagency.domain.User;
import by.vorobyov.travelagency.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class})
@ActiveProfiles("jpa")
public class UserRepositoryImplTest {

    @Autowired
    private UserRepository userRepository;
    private User testUser;

    @Before
    public  void init() {
        testUser = new User(1,"None","None","Test user","None");
    }

    @Test
    public void readAll() {
        assertFalse(userRepository.readAll().isEmpty());
    }

    @Test
    public void add() {
        userRepository.add(testUser);
        Optional<User> actualUser = userRepository.readAll()
                .stream()
                .filter((o) -> o.getName().equals(testUser.getName()))
                .findAny();

        assertTrue(actualUser.isPresent());
    }

    @Test
    public void getEntity() {
        userRepository.add(testUser);
        Optional<User> actualUser = userRepository.readAll()
                .stream()
                .filter((o) -> o.getName().equals(testUser.getName()))
                .findAny();
        Optional<User> addedUser = userRepository.getEntity(actualUser.get().getId());

        assertTrue(addedUser.isPresent());
    }

    @Test
    public void update() {
        userRepository.add(testUser);
        Optional<User> actualUser = userRepository.readAll()
                .stream()
                .filter((o) -> o.getName().equals(testUser.getName()))
                .findAny();
        if (!actualUser.isPresent()) {
            fail();
        } else {
            actualUser.get().setName("Updated user");
            userRepository.update(actualUser.get());

            Optional<User> resultCountry = userRepository.readAll()
                    .stream()
                    .filter((o) -> o.getName().equals(actualUser.get().getName()))
                    .findAny();

            assertTrue(resultCountry.isPresent());
        }
    }

    @Test
    public void delete() {
        userRepository.add(testUser);
        Optional<User> actualUser = userRepository.readAll()
                .stream()
                .filter((o) -> o.getName().equals(testUser.getName()))
                .findAny();
        if (!actualUser.isPresent()) {
            fail();
        } else {
            userRepository.delete(actualUser.get().getId());
            actualUser = userRepository.getEntity(actualUser.get().getId());

            assertFalse(actualUser.isPresent());
        }
    }

}