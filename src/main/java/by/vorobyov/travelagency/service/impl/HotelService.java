package by.vorobyov.travelagency.service.impl;

import by.vorobyov.travelagency.domain.Hotel;
import by.vorobyov.travelagency.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService implements by.vorobyov.travelagency.service.HotelService {

    private HotelRepository hotelRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<Hotel> readAll() {
        return hotelRepository.readAll();
    }

    @Override
    public boolean add(Hotel entity) {
        return hotelRepository.add(entity);
    }

    @Override
    public Optional<Hotel> getEntity(Integer entityId) {
        return hotelRepository.getEntity(entityId);
    }

    @Override
    public boolean update(Hotel entity) {
        return hotelRepository.update(entity);
    }

    @Override
    public boolean delete(Integer entityId) {
        return hotelRepository.delete(entityId);
    }
}
