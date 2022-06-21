package se.b3.healthtech.blackbird.blbcomposite.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.b3.healthtech.blackbird.blbcomposite.domain.Container;
import se.b3.healthtech.blackbird.blbcomposite.enums.CompositionType;
import se.b3.healthtech.blackbird.blbcomposite.persistence.service.ContainerDbHandler;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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

    public String createContainers(List<Container> containerList, String partitionKey) throws CloneNotSupportedException {

        log.info("in the container service class: init");

        List<Container> containersList = createContainersList(containerList, partitionKey);

        containerDbHandler.insertContainers(containersList);

        return null;
    }
    public List<Container> createContainersList(List<Container> containerList, String partitionKey) throws CloneNotSupportedException {
        List<Container> containersList = new ArrayList<>();
        log.info("1. " + containerList.size() + " " + containerList);

        for (Container container : containerList) {addPartitionKey(container, partitionKey);
            addVersionKey(container);
        }
        containersList.addAll(containerList);
        log.info("2. " + containersList.size() + " " + containersList);

        List<Container> clonedContainersList = cloneContainersList(containerList);
        getLatestVersionKey(clonedContainersList);

        containersList.addAll(clonedContainersList);
        log.info("3. " + containersList.size() + " " + containersList);
        return containersList;
    }


    private void addVersionKey(Container container) {
        container.setVersionKey(
                CompositionType.CONTAINER + DELIMITER + container.getUuid() + DELIMITER +
                        "V" + container.getVersionNumber() +
                        DELIMITER + "C" + container.getCommitNumber()
        );
    }

    // for get-method
    private void getLatestVersionKey(List<Container> containerList) {
        for (Container container : containerList) {
            container.setVersionKey(CompositionType.CONTAINER + DELIMITER + LATEST_KEY + DELIMITER + container.getUuid());
        }
    }

    // for add-method
    private void addPartitionKey(Container container, String partitionKey) {
        container.setId(partitionKey);
        container.setCommitNumber(1);
        container.setVersionNumber(1);
    }
    private void addLatestVersionKey(Container container) {
            container.setVersionKey(CompositionType.CONTAINER + DELIMITER + LATEST_KEY + DELIMITER + container.getUuid());
    }

    public List<Container> cloneContainersList(@NotNull List<Container> containerList) throws CloneNotSupportedException {
        List<Container> clonedContainersList = new ArrayList<>();
        for (Container container : containerList) {
            Container clonedContainer = (Container) container.clone();
            clonedContainersList.add(clonedContainer);
        }
        return clonedContainersList;
    }

    // for add-method
    public Container cloneContainer(@NotNull Container container) throws CloneNotSupportedException {
            Container clonedContainer = (Container) container.clone();
        return clonedContainer;
    }

    // man får in partitionKey, skapar versionKey och anropar databasen
    public List<Container> getLatestContainers(String key) {
        String versionKey = CompositionType.CONTAINER.name() + DELIMITER + LATEST_KEY;
        return containerDbHandler.getContainers(key, versionKey);
    }

    public String addContainer(String key, Container container) throws CloneNotSupportedException {
        log.info("in the ContainerService class: addContainer init");

        List<Container> containersList = new ArrayList<>();
        log.info("1. " + containersList.size() + " " + containersList);

        // sätt partitionKey och versionKey för den nya containern
        addPartitionKey(container, key);
        addVersionKey(container);

        containersList.add(container);
        log.info("2. " + containersList.size() + " " + containersList);

        //clona containerobjekt
        Container clonedContainer = cloneContainer(container);

        // sätt versionKey för Latest-nyckeln
        addLatestVersionKey(clonedContainer);

        containersList.add(clonedContainer);
        log.info("3. " + containersList.size() + " " + containersList);

        // anropa databasen så att båda objekten skapas i databasen. använd befintlig metod
        containerDbHandler.insertContainers(containersList);

        return null;
    }
}
