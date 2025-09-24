package common.repository;

import java.util.Objects;

public record ModelInformation(Store store, String name, int price) {

    public ModelInformation {
        Objects.requireNonNull(store);
        Objects.requireNonNull(name);
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }
}
