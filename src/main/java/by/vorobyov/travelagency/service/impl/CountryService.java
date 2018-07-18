package by.vorobyov.travelagency.service.impl;

import by.vorobyov.travelagency.domain.Country;
import by.vorobyov.travelagency.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService implements by.vorobyov.travelagency.service.CountryService {

    private CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> readAll() {
        return countryRepository.readAll();
    }

    @Override
    public boolean add(Country entity) {
        return countryRepository.add(entity);
    }

    @Override
    public Optional<Country> getEntity(Integer entityId) {
        return countryRepository.getEntity(entityId);
    }

    @Override
    public boolean update(Country entity) {
        return countryRepository.update(entity);
    }

    @Override
    public boolean delete(Integer entityId) {
        return countryRepository.delete(entityId);
    }
}
