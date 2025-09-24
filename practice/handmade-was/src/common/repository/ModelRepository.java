package common.repository;

import java.util.List;

public class ModelRepository {

    private final List<ModelInformation> models = List.of(
            new ModelInformation(Store.APPLE, "iPhone 16 Pro", 1550000),
            new ModelInformation(Store.APPLE, "iMac", 1990000),
            new ModelInformation(Store.APPLE, "MacBook Pro 14", 2390000),
            new ModelInformation(Store.APPLE, "AirPods 4", 199000),
            new ModelInformation(Store.SAMSUNG, "Galaxy Z Flip6", 1314000),
            new ModelInformation(Store.SAMSUNG, "Galaxy S24 Ultra", 1919000),
            new ModelInformation(Store.SAMSUNG, "QLED 4K", 2390000),
            new ModelInformation(Store.SAMSUNG, "Galaxy Book5 Pro 360", 2476000)
    );

    public List<ModelInformation> findByStore(Store store) {
        return models.stream()
                .filter(model -> model.store().equals(store))
                .toList();
    }

    public List<ModelInformation> findByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }
        return models.stream()
                .filter(model -> {
                    String upperModelName = model.name().toUpperCase();
                    return upperModelName.contains(keyword.toUpperCase());
                })
                .toList();
    }
}
