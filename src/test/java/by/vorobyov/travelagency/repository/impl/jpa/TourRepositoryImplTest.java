package by.vorobyov.travelagency.repository.impl.jpa;

import by.vorobyov.travelagency.configuration.TestConfiguration;
import by.vorobyov.travelagency.domain.Tour;
import by.vorobyov.travelagency.repository.TourRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Optional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class})
@ActiveProfiles("jpa")
public class TourRepositoryImplTest {

    @Autowired
    private TourRepository tourRepository;
    private Tour testTour;

    @Before
    public void init() {
        testTour = new Tour(1,"None",new Date(2).toLocalDate(),1,null,null,null,"Test tour",1.0);
    }

    @Test
    public void readAll() {
        assertFalse(tourRepository.readAll().isEmpty());
    }

    @Test
    public void add() {
        tourRepository.add(testTour);
        Optional<Tour> actualCountry = tourRepository.readAll()
                .stream()
                .filter((o) -> o.getDescription().equals(testTour.getDescription()))
                .findAny();

        assertTrue(actualCountry.isPresent());
    }

    @Test
    public void getEntity() {
        tourRepository.add(testTour);
        Optional<Tour> actualCountry = tourRepository.readAll()
                .stream()
                .filter((o) -> o.getDescription().equals(testTour.getDescription()))
                .findAny();
        Optional<Tour> addedTour = tourRepository.getEntity(actualCountry.get().getId());

        assertTrue(addedTour.isPresent());
    }

    @Test
    public void update() {
        tourRepository.add(testTour);
        Optional<Tour> actualTour = tourRepository.readAll()
                .stream()
                .filter((o) -> o.getDescription().equals(testTour.getDescription()))
                .findAny();
        if (!actualTour.isPresent()) {
            fail();
        } else {
            actualTour.get().setDescription("Updated tour");
            tourRepository.update(actualTour.get());

            Optional<Tour> resultCountry = tourRepository.readAll()
                    .stream()
                    .filter((o) -> o.getDescription().equals(actualTour.get().getDescription()))
                    .findAny();

            assertTrue(resultCountry.isPresent());
        }
    }

    @Test
    public void delete() {
        tourRepository.add(testTour);
        Optional<Tour> actualTour = tourRepository.readAll()
                .stream()
                .filter((o) -> o.getDescription().equals(testTour.getDescription()))
                .findAny();
        if (!actualTour.isPresent()) {
            fail();
        } else {
            tourRepository.delete(actualTour.get().getId());
            actualTour = tourRepository.getEntity(actualTour.get().getId());

            assertFalse(actualTour.isPresent());
        }
    }

}