package by.vorobyov.travelagency.domain;

import java.util.Arrays;
import java.util.Optional;

public enum TourType {

    WEEKEND(1, "WEEKEND"),

    FAMILY(2, "FAMILY"),

    BEER(3, "BEER");

    private long id;
    private String title;

    TourType(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public static TourType findById(long id) {
        Optional<TourType> tourType = Arrays.stream(TourType.values())
                .filter(element -> element.getId() == id)
                .findFirst();
        if (tourType.isPresent()) {
            return tourType.get();
        } else {
            throw new RuntimeException(); //TODO
        }
    }

    @Override
    public String toString() {
        return title;
    }
}
