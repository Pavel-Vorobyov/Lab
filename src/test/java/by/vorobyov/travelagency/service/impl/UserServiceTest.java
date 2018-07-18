package by.vorobyov.travelagency.service.impl;

import by.vorobyov.travelagency.domain.User;
import by.vorobyov.travelagency.repository.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private User testUser;
    private by.vorobyov.travelagency.service.UserService userService;

    @Before
    public void setUp() {
        userRepository = Mockito.mock(by.vorobyov.travelagency.repository.UserRepository.class);
        userService = new by.vorobyov.travelagency.service.impl.UserService(userRepository);
        testUser = new User(1, "None","None","None", "None");

        Mockito.when(userRepository.readAll()).thenReturn(Collections.EMPTY_LIST);
        Mockito.when(userRepository.add(Mockito.any(User.class))).thenReturn(true);
        Mockito.when(userRepository.getEntity(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(userRepository.update(Mockito.any(User.class))).thenReturn(true);
        Mockito.when(userRepository.delete(Mockito.anyLong())).thenReturn(true);
    }

    @After
    public void tearDown() {
        userRepository = null;
        userService = null;
        testUser = null;
    }

    @Test
    public void readAll() {
        Assert.assertTrue(userService.readAll() == Collections.EMPTY_LIST);
    }

    @Test
    public void add() {
        Assert.assertTrue(userService.add(testUser));
    }

    @Test
    public void getEntity() {
        Assert.assertTrue(userService.getEntity(1).equals(Optional.empty()));
    }

    @Test
    public void update() {
        Assert.assertTrue(userService.update(testUser));
    }

    @Test
    public void delete() {
        Assert.assertTrue(userService.delete(1));
    }
}