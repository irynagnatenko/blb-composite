package se.b3.healthtech.blackbird.blbcomposite.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.b3.healthtech.blackbird.blbcomposite.domain.Container;
import se.b3.healthtech.blackbird.blbcomposite.domain.ContainerObject;
import se.b3.healthtech.blackbird.blbcomposite.enums.CompositionType;
import se.b3.healthtech.blackbird.blbcomposite.persistence.service.ContainerObjectDbHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ContainerObjectService {
    private static final String DELIMITER = "#";
    private static final String LATEST_KEY = "LATEST";

    private final ContainerObjectDbHandler containerObjectDbHandler;

    public ContainerObjectService(ContainerObjectDbHandler containerObjectDbHandler) {
        this.containerObjectDbHandler = containerObjectDbHandler;
    }

    public String createContainerObjects(List<ContainerObject> containerObjectsList, String partitionKey) throws CloneNotSupportedException {

        log.info("in the container object service class: init");

        List<ContainerObject> containerObjects = createContainerObjectsList(containerObjectsList, partitionKey);

        containerObjectDbHandler.insertContainerObjects(containerObjects);

        return null;
    }

    private List<ContainerObject> createContainerObjectsList(List<ContainerObject> containerObjectList, String partititonKey) throws CloneNotSupportedException {
        List<ContainerObject> containerObjectsList = new ArrayList<>();
        log.info("1. " + containerObjectList.size() + " " + containerObjectList);

        for (int i = 0; i < containerObjectList.size(); i++) {
            containerObjectList.get(i).setId(partititonKey);
            containerObjectList.get(i).setVersionKey(
                    CompositionType.CONTAINER_OBJECT + DELIMITER + containerObjectList.get(i).getUuid() + DELIMITER + "V" + containerObjectList.get(i).getVersionNumber() +
                            DELIMITER + "C" + containerObjectList.get(i).getCommitNumber()
            );
            log.info("2. " + containerObjectList.size() + " " + containerObjectsList);

        }
        containerObjectsList.addAll(containerObjectList);
        log.info("3. " + containerObjectsList.size() + " " + containerObjectsList);

        for (ContainerObject containerObject : containerObjectList) {
            ContainerObject cloneContainerObject = (ContainerObject) containerObject.clone();
            cloneContainerObject.setVersionKey(CompositionType.CONTAINER_OBJECT + DELIMITER + LATEST_KEY + DELIMITER + containerObject.getUuid());
            containerObjectsList.add(cloneContainerObject);
        }
        log.info("4. " + containerObjectsList.size() + " " + containerObjectsList);

        return containerObjectsList;
    }

        public List<ContainerObject> getLatestContainerObjects(String key) {
        String versionKey = CompositionType.CONTAINER_OBJECT.name() + DELIMITER + LATEST_KEY;
        return containerObjectDbHandler.getContainerObjects(key, versionKey);
    }


}


