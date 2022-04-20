package se.b3.healthtech.blackbird.blbcomposite.api.request;

import se.b3.healthtech.blackbird.blbcomposite.domain.Container;
import se.b3.healthtech.blackbird.blbcomposite.domain.ContainerObject;
import se.b3.healthtech.blackbird.blbcomposite.domain.Publication;

import java.util.List;

public record CreatePublicationRequest(Publication publication,
                                       List<Container> containerList,
                                       List<ContainerObject> containerObjectList) {
}
