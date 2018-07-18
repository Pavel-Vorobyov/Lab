package by.vorobyov.travelagency.repository.impl.jpa;

import by.vorobyov.travelagency.configuration.TestConfiguration;
import by.vorobyov.travelagency.domain.Hotel;
import by.vorobyov.travelagency.repository.HotelRepository;
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
public class HotelRepositoryImplTest {
    @Autowired
    private HotelRepository hotelRepository;
    private Hotel testHotel;

    @Before
    public  void init() {
        testHotel = new Hotel(1, "Test hotel", "None", null, 1);
    }

    @Test
    public void readAll() {
        assertFalse(hotelRepository.readAll().isEmpty());
    }

    @Test
    public void add() {
        hotelRepository.add(testHotel);
        Optional<Hotel> actualHotel = hotelRepository.readAll()
                .stream()
                .filter((o) -> o.getName().equals(testHotel.getName()))
                .findAny();

        assertTrue(actualHotel.isPresent());
    }

    @Test
    public void getEntity() {
        hotelRepository.add(testHotel);
        Optional<Hotel> actualHotel = hotelRepository.readAll()
                .stream()
                .filter((o) -> o.getName().equals(testHotel.getName()))
                .findAny();
        Optional<Hotel> addedHotel = hotelRepository.getEntity(actualHotel.get().getId());

        assertTrue(addedHotel.isPresent());
    }

    @Test
    public void update() {
        hotelRepository.add(testHotel);
        Optional<Hotel> actualHotel = hotelRepository.readAll()
                .stream()
                .filter((o) -> o.getName().equals(testHotel.getName()))
                .findAny();
        if (!actualHotel.isPresent()) {
            fail();
        } else {
            actualHotel.get().setName("Updated hotel");
            hotelRepository.update(actualHotel.get());

            Optional<Hotel> resultHotel = hotelRepository.readAll()
                    .stream()
                    .filter((o) -> o.getName().equals(actualHotel.get().getName()))
                    .findAny();

            assertTrue(!resultHotel.isPresent());
        }
    }

    @Test
    public void delete() {
        hotelRepository.add(testHotel);
        Optional<Hotel> actualHotel = hotelRepository.readAll()
                .stream()
                .filter((o) -> o.getName().equals(testHotel.getName()))
                .findAny();
        if (!actualHotel.isPresent()) {
            fail();
        } else {
            hotelRepository.delete(actualHotel.get().getId());
            actualHotel = hotelRepository.getEntity(actualHotel.get().getId());

            assertFalse(actualHotel.isPresent());
        }
    }

}