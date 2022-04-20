package se.b3.healthtech.blackbird.blbcomposite.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.b3.healthtech.blackbird.blbcomposite.api.request.CreatePublicationRequest;
import se.b3.healthtech.blackbird.blbcomposite.domain.Publication;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublicationService {

    public Object createPublication(CreatePublicationRequest request) {
        
        // get partitions nyckel
        // get versionsnyckel
        // hämta data från databasen
        log.info("in the service class");
        return null; 
    }

}

