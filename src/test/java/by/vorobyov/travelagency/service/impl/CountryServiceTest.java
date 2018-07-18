package by.vorobyov.travelagency.service.impl;

import by.vorobyov.travelagency.domain.Country;
import by.vorobyov.travelagency.repository.CountryRepository;
import by.vorobyov.travelagency.service.CountryService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;

public class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;

    private Country testCountry;
    private CountryService countryService;

    @Before
    public void setUp() {
        countryRepository = Mockito.mock(by.vorobyov.travelagency.repository.CountryRepository.class);
        countryService = new by.vorobyov.travelagency.service.impl.CountryService(countryRepository);
        testCountry = new Country(1, "Test country");

        Mockito.when(countryRepository.readAll()).thenReturn(Collections.EMPTY_LIST);
        Mockito.when(countryRepository.add(Mockito.any(Country.class))).thenReturn(true);
        Mockito.when(countryRepository.getEntity(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(countryRepository.update(Mockito.any(Country.class))).thenReturn(true);
        Mockito.when(countryRepository.delete(Mockito.anyLong())).thenReturn(true);
    }

    @After
    public void tearDown() {
        countryRepository = null;
        countryService = null;
        testCountry = null;
    }

    @Test
    public void readAll() {
        Assert.assertTrue(countryService.readAll() == Collections.EMPTY_LIST);
    }

    @Test
    public void add() {
        Assert.assertTrue(countryService.add(testCountry));
    }

    @Test
    public void getEntity() {
        Assert.assertTrue(countryService.getEntity(1).equals(Optional.empty()));
    }

    @Test
    public void update() {
        Assert.assertTrue(countryService.update(testCountry));
    }

    @Test
    public void delete() {
        Assert.assertTrue(countryService.delete(1));
    }
}