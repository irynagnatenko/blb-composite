package se.b3.healthtech.blackbird.blbcomposite.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
    private static final String DELETED_KEY = "DELETED";


    private final ContainerObjectDbHandler containerObjectDbHandler;

    public ContainerObjectService(ContainerObjectDbHandler containerObjectDbHandler) {
        this.containerObjectDbHandler = containerObjectDbHandler;
    }

    public String createContainerObjects(List<ContainerObject> containerObjectsList, String partitionKey) throws CloneNotSupportedException {

        log.info("in the containerObjectService : init");

        List<ContainerObject> containerObjects = createContainerObjectsList(containerObjectsList, partitionKey);

        containerObjectDbHandler.insertContainerObjects(containerObjects);

        return null;
    }

    public List<ContainerObject> createContainerObjectsList(List<ContainerObject> containerObjectList, String partititonKey) throws CloneNotSupportedException {
        List<ContainerObject> containerObjectsList = new ArrayList<>();
        log.info("1. " + containerObjectList.size() + " " + containerObjectList);

        for (ContainerObject containerObject : containerObjectList) {
            setPartitionKey(containerObject, partititonKey);
            setVersionKey(containerObject);

            log.info("2. " + containerObjectList.size() + " " + containerObjectsList);

        }
        containerObjectsList.addAll(containerObjectList);
        log.info("3. " + containerObjectsList.size() + " " + containerObjectsList);

        for (ContainerObject containerObject : containerObjectList) {
            ContainerObject clonedContainerObject = cloneContainerObject(containerObject);
            setLatestVersionKey(clonedContainerObject);
            containerObjectsList.add(clonedContainerObject);
        }
        log.info("4. " + containerObjectsList.size() + " " + containerObjectsList);

        return containerObjectsList;
    }

    private void setLatestVersionKey(ContainerObject containerObject) {
        containerObject.setVersionKey(CompositionType.CONTAINER_OBJECT + DELIMITER +
                LATEST_KEY + DELIMITER + containerObject.getUuid());
    }

    private ContainerObject cloneContainerObject(ContainerObject containerObject) throws CloneNotSupportedException {
        ContainerObject clonedContainerObject = (ContainerObject) containerObject.clone();
        return clonedContainerObject;
    }

    /*
    private void setVersionKeyList(List<ContainerObject> containerObjectList) {
        for (ContainerObject containerObject : containerObjectList) {
            containerObject.setVersionKey(
                    CompositionType.CONTAINER_OBJECT + DELIMITER + containerObject.getUuid() +
                            DELIMITER + "V" + containerObject.getVersionNumber() +
                            DELIMITER + "C" + containerObject.getCommitNumber()
            );
        }
    }
     */

    private void setVersionKey(ContainerObject containerObject) {
        containerObject.setVersionKey(
                CompositionType.CONTAINER_OBJECT + DELIMITER + containerObject.getUuid() +
                        DELIMITER + "V" + containerObject.getVersionNumber() +
                        DELIMITER + "C" + containerObject.getCommitNumber()
        );
    }

    public List<ContainerObject> getLatestContainerObjects(String key) {
        String versionKey = CompositionType.CONTAINER_OBJECT.name() + DELIMITER + LATEST_KEY;
        return containerObjectDbHandler.getContainerObjects(key, versionKey);
    }

    private void setPartitionKey(ContainerObject containerObject, String partitionKey) {
        containerObject.setId(partitionKey);
        containerObject.setCommitNumber(1);
        containerObject.setVersionNumber(1);
    }

    public void addContainerObject(String key, ContainerObject containerObject) throws CloneNotSupportedException {
        log.info("in the containerObjectService : addContainerObject");

        List<ContainerObject> resultListContainerObject = new ArrayList<>();

        setVersionKey(containerObject);
        setPartitionKey(containerObject, key);

        resultListContainerObject.add(containerObject);

        ContainerObject clonedContainerObject = cloneContainerObject(containerObject);

        setLatestVersionKey(clonedContainerObject);

        resultListContainerObject.add(clonedContainerObject);

        containerObjectDbHandler.insertContainerObjects(resultListContainerObject);

    }

    public ContainerObject getLatestContainerObject(String publicationId, String containerObjectId) {
        log.info("in the containerObjectService : getLatestContainerObject");

        String versionKey = CompositionType.CONTAINER_OBJECT.name() + DELIMITER + LATEST_KEY + DELIMITER + containerObjectId;

        List<ContainerObject> containerObjects = containerObjectDbHandler.getContainerObjects(publicationId, versionKey);

        return containerObjects.get(0);
    }

    // Denna metod ska för varje ContainerObject i listan:
    // sätta id (publicationId), samt versionKey som ska motsvara en LATEST-versionKey med uuid för aktuellt containerObject.
    //Anropa delete-metoden i ContainerObjectDBHandler för varje ContainerObject-objekt i listan
    //Skapa upp en ny versionkey enligt formatet - CompositionType#DELETED#uuid
    //Dessutom ska ett timestamp samt userName sättas på objektet som ska skrivas ner till databasen
    //Anropa write-metoden i ContainerObjectDBHandler för varje ContainerObject-objekt i listan
    public void deleteContainerObject(String userName, String publicationId, List<ContainerObject> containerObjectList) {
        log.info("in the containerObjectService : deleteContainerObject");

        containerObjectsToDelete(publicationId, containerObjectList);
        containerObjectDbHandler.deleteContainerObjects(containerObjectList);
        log.info("containerObjectService : deleteContainerObject - after delete");

        markAsDeleted(containerObjectList, userName);
        containerObjectDbHandler.insertContainerObjects(containerObjectList);
        log.info("containerObjectService : deleteContainerObject - after write");

    }

    private void containerObjectsToDelete(String publicationId, List<ContainerObject> containerObjectList) {

        for (ContainerObject containerObject : containerObjectList) {
            setPartitionKey(containerObject, publicationId);
            setLatestVersionKey(containerObject);
            log.info("id: " + containerObject.getVersionKey());
        }
    }

    private void markAsDeleted(List<ContainerObject> containerObjectList, String userName) {
        for (ContainerObject containerObject : containerObjectList) {
            setDeletedKey(containerObject);
            containerObject.setCreated(ServiceUtil.setCreatedTime());
            containerObject.setCreatedBy(userName);
            log.info("id: " + containerObject.getVersionKey());

        }
    }

    private void setDeletedKey(ContainerObject containerObject) {
        containerObject.setVersionKey(CompositionType.CONTAINER_OBJECT + DELIMITER +
                DELETED_KEY + DELIMITER + containerObject.getUuid());
    }

}


