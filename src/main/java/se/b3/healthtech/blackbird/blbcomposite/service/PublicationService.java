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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublicationService {

    private static final String DELIMITER = "#";

    private final PublicationDbHandler publicationDbHandler;
    private final ContainerDbHandler containerDbHandler;
    private final ContainerObjectDbHandler containerObjectDbHandler;

    public String createPublication(CreatePublicationRequest request) {

        log.info("in the composition service class: init");

        List<Publication> publicationList = createPublicationList(request.publication());
        List<Container> containersList = createContainersList(request.containerList(), request.publication().getId());
        List<ContainerObject> containerObjectsList = createContainerObjectsList(request.containerObjectList(), request.publication().getId());

        publicationDbHandler.insertPublications(publicationList);
        containerDbHandler.insertContainers(containersList);
        containerObjectDbHandler.insertContainerObjects(containerObjectsList);

        return null;
    }

    List<Publication> createPublicationList(Publication publication){

        List<Publication> publicationList = new ArrayList<>();
        publication.setId(publication.getUuid());
        publication.setVersionKey(CompositionType.COMPOSITION + DELIMITER + publication.getUuid() + DELIMITER +"V" + publication.getVersionNumber() + DELIMITER + "C" + publication.getCommitNumber());
        publicationList.add(publication);

        return publicationList;
    }

    List<Container> createContainersList(List<Container> containerList, String partititonKey){

        for (int i = 0; i < containerList.size(); i++) {
            containerList.get(i).setId(partititonKey);
            containerList.get(i).setVersionKey(
                    CompositionType.CONTAINER + DELIMITER + containerList.get(i).getUuid() + DELIMITER + "V" +containerList.get(i).getVersionNumber() +
                            DELIMITER + "C" + containerList.get(i).getCommitNumber()
            );
        }

        return containerList;
    }

    List<ContainerObject> createContainerObjectsList(List<ContainerObject> containerObjectList, String partititonKey){
        for (int i = 0; i < containerObjectList.size(); i++) {
            containerObjectList.get(i).setId(partititonKey);
            containerObjectList.get(i).setVersionKey(
                    CompositionType.CONTAINER_OBJECT + DELIMITER + containerObjectList.get(i).getUuid() + DELIMITER + "V" +containerObjectList.get(i).getVersionNumber() +
                            DELIMITER + "C" + containerObjectList.get(i).getCommitNumber()
            );
        }

        return containerObjectList;
    }




        // sätta partitions nyckel --> uuid i publication-object, den är samma för hela compositionen

        // sätta versionsnyckel
        // COMPOSITION#<guid>#V<versionsnummer>#C<CommitNr>
        // CONTAINER#<guid>#V<versionsnummer>#C<CommitNr>
        // CONTAINEROBJECT#<guid>#V<versionsnummer>#C<CommitNr>

        // skriv data till databasen
        // (3 anrop)

        // returnera uuid på partitionen


}

