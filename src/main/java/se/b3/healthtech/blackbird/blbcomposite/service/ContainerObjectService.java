package se.b3.healthtech.blackbird.blbcomposite.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.b3.healthtech.blackbird.blbcomposite.domain.ContainerObject;
import se.b3.healthtech.blackbird.blbcomposite.enums.CompositionType;
import se.b3.healthtech.blackbird.blbcomposite.persistence.service.ContainerObjectDbHandler;

import java.util.List;

@Slf4j
@Service
public class ContainerObjectService {
    private static final String DELIMITER = "#";
    private static final String LATEST_KEY = "LATEST";

    private final ContainerObjectDbHandler containerDbHandler;

    public ContainerObjectService(ContainerObjectDbHandler containerDbHandler) {
        this.containerDbHandler = containerDbHandler;
    }

        public List<ContainerObject> getLatestContainerObjects(String key) {
        String versionKey = CompositionType.CONTAINER_OBJECT.name() + DELIMITER + LATEST_KEY;
        return containerDbHandler.getContainerObjects(key, versionKey);
    }


}


