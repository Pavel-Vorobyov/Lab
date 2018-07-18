package by.vorobyov.travelagency.repository.impl.jpa;

import by.vorobyov.travelagency.configuration.TestConfiguration;
import by.vorobyov.travelagency.domain.Country;
import by.vorobyov.travelagency.repository.CountryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Access;

import java.util.Optional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class})
@ActiveProfiles("jpa")
public class CountryRepositoryImplTest {

    @Autowired
    CountryRepository countryRepository;

    private static Country testCountry;

    @BeforeClass
    public static void initAppContext() {
        testCountry = new Country(1, "Test country");
    }

    @Test
    public void readAll() {
        Assert.assertFalse(countryRepository.readAll().isEmpty());
    }

    @Test
    public void add() {
        countryRepository.add(testCountry);
        Optional<Country> actualCountry = countryRepository.readAll()
                .stream()
                .filter((o) -> o.getName().equals(testCountry.getName()))
                .findAny();

        assertTrue(actualCountry.isPresent());

    }

    @Test
    public void getEntity() {
        countryRepository.add(testCountry);
        Optional<Country> actualCountry = countryRepository.readAll()
                .stream()
                .filter((o) -> o.getName().equals(testCountry.getName()))
                .findAny();
        Optional<Country> addedCountry = countryRepository.getEntity(actualCountry.get().getId());

        assertTrue(addedCountry.isPresent());
    }

    @Test
    public void update() {
        countryRepository.add(testCountry);
        Optional<Country> actualCountry = countryRepository.readAll()
                .stream()
                .filter((o) -> o.getName().equals(testCountry.getName()))
                .findAny();
        if (!actualCountry.isPresent()) {
            fail();
        } else {
            actualCountry.get().setName("Updated country");
            countryRepository.update(actualCountry.get());

            Optional<Country> resultCountry = countryRepository.readAll()
                    .stream()
                    .filter((o) -> o.getName().equals(actualCountry.get().getName()))
                    .findAny();

            assertTrue(resultCountry.isPresent());
        }
    }

    @Test
    public void delete() {
        countryRepository.add(testCountry);
        Optional<Country> actualCountry = countryRepository.readAll()
                .stream()
                .filter((o) -> o.getName().equals(testCountry.getName()))
                .findAny();
        if (!actualCountry.isPresent()) {
            fail();
        } else {
            countryRepository.delete(actualCountry.get().getId());
            actualCountry = countryRepository.getEntity(actualCountry.get().getId());

            assertFalse(actualCountry.isPresent());
        }
    }
}