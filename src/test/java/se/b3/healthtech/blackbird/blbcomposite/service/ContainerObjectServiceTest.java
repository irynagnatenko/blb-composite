package se.b3.healthtech.blackbird.blbcomposite.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import se.b3.healthtech.blackbird.blbcomposite.domain.ContainerObject;
import se.b3.healthtech.blackbird.blbcomposite.persistence.service.ContainerObjectDbHandler;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContainerObjectServiceTest {

    ContainerObjectService containerObjectService;

    ContainerObjectDbHandler containerObjectDbHandlerMock;

    @BeforeEach
    public void setUp(){
        containerObjectDbHandlerMock = Mockito.mock(ContainerObjectDbHandler.class);
        containerObjectService = new ContainerObjectService( containerObjectDbHandlerMock);

    }

    @Test
    public void checkCloneContainerObject() throws CloneNotSupportedException {
        ContainerObject c1 = new ContainerObject();
        c1.setUuid("uuid1");
        c1.setCreatedBy("iryna");
        c1.setCreated(1000L);
        c1.setOrdinal(1);

        ContainerObject c2 = new ContainerObject();
        c2.setUuid("uuid2");
        c2.setCreatedBy("iryna");
        c2.setCreated(1000L);
        c2.setOrdinal(1);

        List<ContainerObject> expectedList = new ArrayList<>();
        expectedList.add(c1);
        expectedList.add(c2);

        List<ContainerObject> resultList = containerObjectService.createContainerObjectsList(expectedList, c1.getUuid());

        assertEquals(4,resultList.size());
    }

}
