package se.b3.healthtech.blackbird.blbcomposite.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.b3.healthtech.blackbird.blbcomposite.domain.Publication;
import se.b3.healthtech.blackbird.blbcomposite.enums.CompositionType;
import se.b3.healthtech.blackbird.blbcomposite.persistence.service.ContainerDbHandler;
import se.b3.healthtech.blackbird.blbcomposite.persistence.service.ContainerObjectDbHandler;
import se.b3.healthtech.blackbird.blbcomposite.persistence.service.PublicationDbHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublicationService {

    private static final String DELIMITER = "#";
    private static final String LATEST = "LATEST";

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
        clonePublication.setVersionKey(CompositionType.COMPOSITION + DELIMITER + LATEST + DELIMITER + publication.getUuid());
        publicationList.add(clonePublication);

        return publicationList;
    }

    public Publication getLatestPublication(String key) {

        String versionKey = CompositionType.COMPOSITION.name() + DELIMITER + LATEST;
        List<Publication> publicationList = publicationDbHandler.getPublications(key, versionKey);
        log.info("Composite - getLatestPublication");
        return publicationList.get(0);
    }


}

