package se.b3.healthtech.blackbird.blbcomposite.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import se.b3.healthtech.blackbird.blbcomposite.domain.Publication;
import se.b3.healthtech.blackbird.blbcomposite.enums.CompositionType;
import se.b3.healthtech.blackbird.blbcomposite.persistence.service.PublicationDbHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublicationService {

    private static final String DELIMITER = "#";
    private static final String LATEST_KEY = "LATEST";
    private static final String DELETED_KEY = "DELETED";

    private final PublicationDbHandler publicationDbHandler;

    public String createPublication(Publication publication) throws CloneNotSupportedException {

        log.info("in the composition service class: init");

        List<Publication> publicationList = createPublicationList(publication);

        publicationDbHandler.insertPublications(publicationList);

        return null;
    }

    List<Publication> createPublicationList(Publication publication) throws CloneNotSupportedException {

        List<Publication> publicationList = new ArrayList<>();
        publication.setId(publication.getUuid());
        publication.setVersionNumber(1);
        publication.setCommitNumber(1);
        publication.setVersionKey(CompositionType.COMPOSITION + DELIMITER + publication.getUuid() + DELIMITER +"V" + publication.getVersionNumber() + DELIMITER + "C" + publication.getCommitNumber());
        publicationList.add(publication);

        Publication clonePublication = (Publication) publication.clone();
        clonePublication.setVersionKey(CompositionType.COMPOSITION + DELIMITER + LATEST_KEY+ DELIMITER + publication.getUuid());
        publicationList.add(clonePublication);

        return publicationList;
    }

    public Publication getLatestPublication(String key) {

        String versionKey = CompositionType.COMPOSITION.name() + DELIMITER + LATEST_KEY;
        List<Publication> publicationList = publicationDbHandler.getPublications(key, versionKey);
        log.info("Composite - getLatestPublication");
        return publicationList.get(0);
    }


    public void deletePublication(String userName, String publicationId, List<Publication> publicationList) {
        log.info("in the publicationService : deletePublication");
        publicationToDelete(publicationId, publicationList);
        publicationDbHandler.deletePublications(publicationList);
        markAsDeleted(publicationList, userName);
        publicationDbHandler.insertPublications(publicationList);
    }

    private void publicationToDelete (String publicationId, List<Publication> publicationList) {
        log.info("in the publicationService : publicationToDelete");

        for (Publication publication : publicationList) {
            setPartitionKey(publication, publicationId);
            setLatestVersionKey(publication);
            log.info(publication.getVersionKey());
        }
    }

    private void markAsDeleted(List<Publication> publicationList, String userName) {
        log.info("in the publicationService : markAsDeleted");
        for (Publication publication : publicationList) {
            setDeletedKey(publication);
            publication.setCreated(ServiceUtil.setCreatedTime());
            publication.setCreatedBy(userName);
            log.info(publication.getVersionKey());
        }
    }

    private void setPartitionKey(Publication publication, String partitionKey) {
        log.info("in the publicationService : setPartitionKey");
        publication.setId(partitionKey);
        publication.setCommitNumber(1);
        publication.setVersionNumber(1);
    }

    private void setLatestVersionKey(Publication publication) {
        publication.setVersionKey(CompositionType.CONTAINER + DELIMITER + LATEST_KEY + DELIMITER + publication.getUuid());
    }
    private void setDeletedKey(Publication publication) {
        publication.setVersionKey(CompositionType.COMPOSITION.name() + DELIMITER + DELETED_KEY + DELIMITER + publication.getUuid());
    }
}

