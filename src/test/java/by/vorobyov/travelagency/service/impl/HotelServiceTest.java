package by.vorobyov.travelagency.service.impl;

import by.vorobyov.travelagency.domain.Hotel;
import by.vorobyov.travelagency.repository.HotelRepository;
import by.vorobyov.travelagency.service.HotelService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;

public class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    private Hotel testHotel;
    private HotelService hotelService;

    @Before
    public void setUp() {
        hotelRepository = Mockito.mock(by.vorobyov.travelagency.repository.HotelRepository.class);
        hotelService = new by.vorobyov.travelagency.service.impl.HotelService(hotelRepository);
        testHotel = new Hotel(1, "Test hotel", "+111 11 111", null, 1);

        Mockito.when(hotelRepository.readAll()).thenReturn(Collections.EMPTY_LIST);
        Mockito.when(hotelRepository.add(Mockito.any(Hotel.class))).thenReturn(true);
        Mockito.when(hotelRepository.getEntity(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(hotelRepository.update(Mockito.any(Hotel.class))).thenReturn(true);
        Mockito.when(hotelRepository.delete(Mockito.anyLong())).thenReturn(true);
    }

    @After
    public void tearDown() {
        hotelService = null;
        hotelRepository = null;
        testHotel = null;
    }

    @Test
    public void readAll() {
        Assert.assertTrue(hotelService.readAll() == Collections.EMPTY_LIST);
    }

    @Test
    public void add() {
        Assert.assertTrue(hotelService.add(testHotel));
    }

    @Test
    public void getEntity() {
        Assert.assertTrue(hotelService.getEntity(1).equals(Optional.empty()));
    }

    @Test
    public void update() {
        Assert.assertTrue(hotelService.update(testHotel));
    }

    @Test
    public void delete() {
        Assert.assertTrue(hotelService.delete(1));
    }
}