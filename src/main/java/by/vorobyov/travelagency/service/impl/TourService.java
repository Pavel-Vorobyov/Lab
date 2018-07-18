package by.vorobyov.travelagency.service.impl;

import by.vorobyov.travelagency.domain.Tour;
import by.vorobyov.travelagency.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourService implements by.vorobyov.travelagency.service.TourService {

    private TourRepository tourRepository;

    @Autowired
    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @Override
    public List<Tour> readAll() {
        return tourRepository.readAll();
    }

    @Override
    public boolean add(Tour entity) {
        return tourRepository.add(entity);
    }

    @Override
    public Optional<Tour> getEntity(Integer entityId) {
        return tourRepository.getEntity(entityId);
    }

    @Override
    public boolean update(Tour entity) {
        return tourRepository.update(entity);
    }

    @Override
    public boolean delete(Integer entityId) {
        return tourRepository.delete(entityId);
    }
}
