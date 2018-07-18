package by.vorobyov.travelagency.service.impl;

import by.vorobyov.travelagency.domain.Tour;
import by.vorobyov.travelagency.repository.TourRepository;
import by.vorobyov.travelagency.service.TourService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;

public class TourServiceTest {

    @Mock
    private TourRepository tourRepository;

    private Tour testTour;
    private TourService tourService;

    @Before
    public void setUp() {
        tourRepository = Mockito.mock(by.vorobyov.travelagency.repository.TourRepository.class);
        tourService = new by.vorobyov.travelagency.service.impl.TourService(tourRepository);
        testTour = new Tour(1,"None",null,
                1,null,null,null,"None",Double.valueOf(0));

        Mockito.when(tourRepository.readAll()).thenReturn(Collections.EMPTY_LIST);
        Mockito.when(tourRepository.add(Mockito.any(Tour.class))).thenReturn(true);
        Mockito.when(tourRepository.getEntity(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(tourRepository.update(Mockito.any(Tour.class))).thenReturn(true);
        Mockito.when(tourRepository.delete(Mockito.anyLong())).thenReturn(true);
    }

    @After
    public void tearDown() {
        tourRepository = null;
        tourService = null;
        testTour = null;
    }

    @Test
    public void readAll() {
        Assert.assertTrue(tourService.readAll() == Collections.EMPTY_LIST);
    }

    @Test
    public void add() {
        Assert.assertTrue(tourService.add(testTour));
    }

    @Test
    public void getEntity() {
        Assert.assertTrue(tourService.getEntity(1).equals(Optional.empty()));
    }

    @Test
    public void update() {
        Assert.assertTrue(tourService.update(testTour));
    }

    @Test
    public void delete() {
        Assert.assertTrue(tourService.delete(1));
    }
}