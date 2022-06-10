package se.b3.healthtech.blackbird.blbcomposite.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.b3.healthtech.blackbird.blbcomposite.domain.Container;
import se.b3.healthtech.blackbird.blbcomposite.enums.CompositionType;
import se.b3.healthtech.blackbird.blbcomposite.persistence.service.ContainerDbHandler;

import java.util.List;

@Slf4j
@Service
public class ContainerService {

    private static final String DELIMITER = "#";
    private static final String LATEST_KEY = "LATEST";

    private final ContainerDbHandler containerDbHandler;

    public ContainerService(ContainerDbHandler containerDbHandler) {
        this.containerDbHandler = containerDbHandler;
    }

    // man får in partitionKey, skapar versionKey och anropar databasen
    public List<Container> getLatestContainers(String key) {
        String versionKey = CompositionType.CONTAINER.name() + DELIMITER + LATEST_KEY;
        return containerDbHandler.getContainers(key, versionKey);
    }
}
