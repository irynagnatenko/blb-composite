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

    @Test
    public void createPublicationListTest(){

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

    @Test
    public void createContainerListTest() {

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

        List<Container> resultList = publicationService.createContainersList(expectedList);

        assertEquals(expectedList.size(),resultList.size());

        Container actual = resultList.get(0);
        assertNotNull(actual.getId());
        assertNotNull(actual.getVersionKey());
        assertEquals(c1.getUuid(), actual.getUuid());
        assertTrue(actual.getVersionKey().contains("CONTAINER"));
        assertTrue(actual.getVersionKey().contains(c1.getUuid()));
        assertThat(actual.getVersionKey(), containsString(String.valueOf(c1.getVersionNumber())));
        assertThat(actual.getVersionKey(), containsString(String.valueOf(c1.getCommitNumber())));

    }


    @Test
    public void createContainerObjectListTest() {

        ContainerObject c1 = new ContainerObject();
        c1.setUuid("uuid");
        c1.setCreatedBy("iryna");
        c1.setCreated(1000L);
        c1.setOrdinal(1);

        assertNull(c1.getId());
        assertNull(c1.getVersionKey());

        List<ContainerObject> expectedList = new ArrayList<>();
        expectedList.add(c1);

        List<ContainerObject> resultList = publicationService.createContainerObjectsList(expectedList);

        assertEquals(expectedList.size(),resultList.size());
        assertEquals(expectedList, resultList);

        ContainerObject actual = resultList.get(0);
        assertNotNull(actual.getId());
        assertNotNull(actual.getVersionKey());
        assertEquals(c1.getUuid(), actual.getUuid());
        assertTrue(actual.getVersionKey().contains("CONTAINER_OBJECT"));

        assertTrue(resultList.contains(c1));

    }


}