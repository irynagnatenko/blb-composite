package se.b3.healthtech.blackbird.blbcomposite.response;

import se.b3.healthtech.blackbird.blbcomposite.domain.Publication;

import java.util.List;

public class ExternalComposition {
    private String id;
    private Publication compositionResponse;
    private List<ContainerResponse> containers;
    private List<ContainerObjectResponse> containerObjects;

}
