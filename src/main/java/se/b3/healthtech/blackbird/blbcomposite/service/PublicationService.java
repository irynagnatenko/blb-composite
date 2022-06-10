package se.b3.healthtech.blackbird.blbcomposite.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.b3.healthtech.blackbird.blbcomposite.api.request.CreatePublicationRequest;
import se.b3.healthtech.blackbird.blbcomposite.domain.Container;
import se.b3.healthtech.blackbird.blbcomposite.domain.ContainerObject;
import se.b3.healthtech.blackbird.blbcomposite.domain.Publication;
import se.b3.healthtech.blackbird.blbcomposite.enums.CompositionType;
import se.b3.healthtech.blackbird.blbcomposite.persistence.service.ContainerDbHandler;
import se.b3.healthtech.blackbird.blbcomposite.persistence.service.ContainerObjectDbHandler;
import se.b3.healthtech.blackbird.blbcomposite.persistence.service.PublicationDbHandler;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublicationService {

    private static final String DELIMITER = "#";
    private static final String LATEST = "LATEST";

    private final PublicationDbHandler publicationDbHandler;
    private final ContainerDbHandler containerDbHandler;
    private final ContainerObjectDbHandler containerObjectDbHandler;

    public String createPublication(CreatePublicationRequest request) throws CloneNotSupportedException {

        log.info("in the composition service class: init");

        List<Publication> publicationList = createPublicationList(request.publication());
        List<Container> containersList = createContainersList(request.containerList(), request.publication().getId());
        List<ContainerObject> containerObjectsList = createContainerObjectsList(request.containerObjectList(), request.publication().getId());

        publicationDbHandler.insertPublications(publicationList);
        containerDbHandler.insertContainers(containersList);
        containerObjectDbHandler.insertContainerObjects(containerObjectsList);

        return null;
    }

    List<Publication> createPublicationList(Publication publication) throws CloneNotSupportedException {

        List<Publication> publicationList = new ArrayList<>();
        publication.setId(publication.getUuid());
        publication.setVersionKey(CompositionType.COMPOSITION + DELIMITER + publication.getUuid() + DELIMITER +"V" + publication.getVersionNumber() + DELIMITER + "C" + publication.getCommitNumber());
        publicationList.add(publication);

        Publication clonePublication = (Publication) publication.clone();
        clonePublication.setVersionKey(CompositionType.COMPOSITION + DELIMITER + LATEST + DELIMITER + publication.getUuid());
        publicationList.add(clonePublication);

        return publicationList;
    }

    public List<Container> createContainersList(List<Container> containerList, String partitionKey) throws CloneNotSupportedException {
        List<Container> containersList = new ArrayList<>();
        log.info("1. " + containerList.size() + " " + containerList);

        for (Container container : containerList) {
            container.setId(partitionKey);
            getVersionKey(container);
        }
        containersList.addAll(containerList);
        log.info("2. " + containersList.size() + " " + containersList);
        List<Container> clonedContainersList = cloneContainersList(containerList);
        getLatestVersionKey(clonedContainersList);

        containersList.addAll(clonedContainersList);
        log.info("3. " + containersList.size() + " " + containersList);
        return containersList;
    }

    private void getVersionKey(Container container) {
        container.setVersionKey(
                CompositionType.CONTAINER + DELIMITER + container.getUuid() + DELIMITER +
                        "V" + container.getVersionNumber() +
                        DELIMITER + "C" + container.getCommitNumber()
        );
    }

    private void getLatestVersionKey(List<Container> containerList) {
        for (Container container : containerList) {
            container.setVersionKey(CompositionType.CONTAINER + DELIMITER + LATEST + DELIMITER + container.getUuid());
        }
    }

    public List<Container> cloneContainersList(@NotNull List<Container> containerList) throws CloneNotSupportedException {
        List<Container> clonedContainersList = new ArrayList<>();
        for (Container container : containerList) {
            Container clonedContainer = (Container) container.clone();
            clonedContainersList.add(clonedContainer);
        }
        return clonedContainersList;
    }

    List<ContainerObject> createContainerObjectsList(List<ContainerObject> containerObjectList, String partititonKey) throws CloneNotSupportedException {
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
                cloneContainerObject.setVersionKey(CompositionType.CONTAINER_OBJECT + DELIMITER + LATEST + DELIMITER + containerObject.getUuid());
                containerObjectsList.add(cloneContainerObject);
            }
            log.info("4. " + containerObjectsList.size() + " " + containerObjectsList);

        return containerObjectsList;
    }

    public Publication getLatestPublication(String key) {

        String versionKey = CompositionType.COMPOSITION.name() + DELIMITER + LATEST;
        List<Publication> publicationList = publicationDbHandler.getPublications(key, versionKey);
        log.info("Composite - getLatestPublication");
        return publicationList.get(0);
    }


}

