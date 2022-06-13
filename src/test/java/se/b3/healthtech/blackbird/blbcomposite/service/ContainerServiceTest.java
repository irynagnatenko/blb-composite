package se.b3.healthtech.blackbird.blbcomposite.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import se.b3.healthtech.blackbird.blbcomposite.domain.Container;
import se.b3.healthtech.blackbird.blbcomposite.persistence.service.ContainerDbHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContainerServiceTest {

    ContainerService containerService;

    ContainerDbHandler containerDbHandlerMock;

    @BeforeEach
    public void setUp(){
        containerDbHandlerMock = Mockito.mock(ContainerDbHandler.class);
        containerService = new ContainerService(containerDbHandlerMock);

    }

    @Test
    public void checkCloneContainersList() throws CloneNotSupportedException {
        List<Container> originalList = new ArrayList<>();

        Container c1 = new Container();
        c1.setId("1");
        c1.setUuid("uuid1");
        c1.setCreatedBy("iryna");
        c1.setCreated(1000L);
        c1.setOrdinal(1);
        c1.setVersionNumber(1);
        c1.setCommitNumber(1);
        c1.setContainerObjectsList(Arrays.asList("1", "2", "3"));

        Container c2 = new Container();
        c2.setId("2");
        c2.setUuid("uuid2");
        c2.setCreatedBy("anna");
        c2.setCreated(1000L);
        c2.setOrdinal(2);
        c2.setVersionNumber(1);
        c2.setCommitNumber(1);
        c2.setContainerObjectsList(Arrays.asList("1", "2", "3"));

        Container c3 = new Container();
        c3.setId("3");
        c3.setUuid("uuid3");
        c3.setCreatedBy("nils");
        c3.setCreated(1000L);
        c3.setOrdinal(1);
        c3.setVersionNumber(1);
        c3.setCommitNumber(1);

        originalList.add(c1);
        originalList.add(c2);
        originalList.add(c3);

        List<Container> resultList = containerService.createContainersList(originalList, c1.getUuid());

        assertEquals(6,resultList.size());

    }

    @Test
    public void createContainerListTest() throws CloneNotSupportedException {

        Container c1 = new Container();
        c1.setUuid("uuid");
        c1.setCreatedBy("iryna");
        c1.setCreated(1000L);
        c1.setOrdinal(1);
        c1.setVersionNumber(1);
        c1.setCommitNumber(1);

        // lägga till ett objekt till
        // kan även testa varje attribut på container

        assertNull(c1.getId());
        assertNull(c1.getVersionKey());

        List<Container> expectedList = new ArrayList<>();
        expectedList.add(c1);

        List<Container> resultList = containerService.createContainersList(expectedList, c1.getUuid());

        assertEquals(expectedList.size(),resultList.size());

        Container actual = resultList.get(0);
        assertNotNull(actual.getId());
        assertNotNull(actual.getVersionKey());
        assertEquals(c1.getUuid(), actual.getUuid());
        assertEquals(c1.getUuid(), actual.getId());
        assertTrue(actual.getVersionKey().contains("CONTAINER"));
        assertTrue(actual.getVersionKey().contains(c1.getUuid()));
        assertThat(actual.getVersionKey(), containsString(String.valueOf(c1.getVersionNumber())));
        assertThat(actual.getVersionKey(), containsString(String.valueOf(c1.getCommitNumber())));

    }

}
