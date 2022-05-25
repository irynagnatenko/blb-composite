package se.b3.healthtech.blackbird.blbcomposite.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.util.Assert;
import se.b3.healthtech.blackbird.blbcomposite.domain.Container;
import se.b3.healthtech.blackbird.blbcomposite.domain.ContainerObject;
import se.b3.healthtech.blackbird.blbcomposite.domain.Publication;
import se.b3.healthtech.blackbird.blbcomposite.persistence.service.ContainerDbHandler;
import se.b3.healthtech.blackbird.blbcomposite.persistence.service.ContainerObjectDbHandler;
import se.b3.healthtech.blackbird.blbcomposite.persistence.service.PublicationDbHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;


class PublicationServiceTest {

    PublicationService publicationService;

    PublicationDbHandler publicationDbHandlerMock;
    ContainerDbHandler containerDbHandlerMock;
    ContainerObjectDbHandler containerObjectDbHandlerMock;


    @BeforeEach
    public void setUp(){
        publicationDbHandlerMock = Mockito.mock(PublicationDbHandler.class);
        containerDbHandlerMock = Mockito.mock(ContainerDbHandler.class);
        containerObjectDbHandlerMock = Mockito.mock(ContainerObjectDbHandler.class);

        publicationService = new PublicationService(publicationDbHandlerMock, containerDbHandlerMock, containerObjectDbHandlerMock);

    }
/*
    @Test
    public void createPublicationListTest() throws CloneNotSupportedException {

        Publication publication = new Publication();
        publication.setUuid("uuid");
        publication.setCreatedBy("iryna");
        publication.setCreated(1000L);
        publication.setTitle("Bird");

        assertNull(publication.getId());
        assertNull(publication.getVersionKey());
        List<Publication> resultList = publicationService.createPublicationList(publication);

        assertEquals(1,resultList.size());

        Publication actual = resultList.get(0);
        assertNotNull(actual.getId());
        assertNotNull(actual.getVersionKey());
        assertEquals(publication.getUuid(), actual.getUuid());
        assertTrue(actual.getVersionKey().contains("COMPOSITION"));

    }

 */

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

        List<Container> resultList = publicationService.createContainersList(originalList, c1.getUuid());

        assertEquals(6,resultList.size());

    }

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

        List<Container> resultList = publicationService.createContainersList(expectedList, c1.getUuid());

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

/*
    @Test
    public void createContainerObjectListTest() throws CloneNotSupportedException {

        ContainerObject c1 = new ContainerObject();
        c1.setUuid("uuid");
        c1.setCreatedBy("iryna");
        c1.setCreated(1000L);
        c1.setOrdinal(1);

        assertNull(c1.getId());
        assertNull(c1.getVersionKey());

        List<ContainerObject> expectedList = new ArrayList<>();
        expectedList.add(c1);

        List<ContainerObject> resultList = publicationService.createContainerObjectsList(expectedList, c1.getUuid());

        assertEquals(expectedList.size(),resultList.size());
        assertEquals(expectedList, resultList);

        ContainerObject actual = resultList.get(0);
        assertNotNull(actual.getId());
        assertNotNull(actual.getVersionKey());
        assertEquals(c1.getUuid(), actual.getUuid());
        assertEquals(c1.getUuid(), actual.getId());
        assertTrue(actual.getVersionKey().contains("CONTAINER_OBJECT"));

        assertTrue(resultList.contains(c1));

    }

 */

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

        List<ContainerObject> resultList = publicationService.createContainerObjectsList(expectedList, c1.getUuid());

        assertEquals(4,resultList.size());
    }



}