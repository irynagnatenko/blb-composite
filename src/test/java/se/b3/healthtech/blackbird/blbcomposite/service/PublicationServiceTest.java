package se.b3.healthtech.blackbird.blbcomposite.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import se.b3.healthtech.blackbird.blbcomposite.persistence.service.PublicationDbHandler;

class PublicationServiceTest {

    PublicationService publicationService;

    PublicationDbHandler publicationDbHandlerMock;


    @BeforeEach
    public void setUp(){
        publicationDbHandlerMock = Mockito.mock(PublicationDbHandler.class);
        publicationService = new PublicationService(publicationDbHandlerMock);

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





}