package by.vorobyov.travelagency.utill;

import by.vorobyov.travelagency.domain.TourType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TourTypeConverter implements AttributeConverter<TourType, Long> {

    @Override
    public Long convertToDatabaseColumn(TourType tourType) {
        if (tourType == null) {
            return null;
        } else {
            return tourType.getId();
        }
    }

    @Override
    public TourType convertToEntityAttribute(Long value) {
        if (value == null) {
            return null;
        } else {
            return TourType.findById(value);
        }
    }
}
