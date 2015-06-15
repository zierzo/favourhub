package com.favour.dome.entity;

import org.junit.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by fernando on 08/06/15.
 */
public class CollaboratorTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    private static final int INEXISTENT_COLLABORATOR_ID = 0;
    private static final int EXISTING_COLLABORATOR_ID = 1;
    private static final int COLLABORATOR_TO_UPDATE_ID= 2;
    private static final int COLLABORATOR_TO_DELETE_ID= 3;

    private static final String EXISTING_COLLABORATOR_EMAIL="ExistingEmail";
    private static final String COLLABORATOR_TO_UPDATE_EMAIL= "EmailToUpdate";
    private static final String COLLABORATOR_TO_DELETE_NAME= "EmailToDelete";

    public static final String COUNTRY_TO_UPDATE = "CountryToUpdate";

    //TODO: Deatach before looking up again
    //Open/close entity manager in every test to avoid inconsistence pc

    @BeforeClass
    public static void initialize() {
        emf = Persistence.createEntityManagerFactory("favourhub-test");
    }

    @Before
    public void initializeEntityManager() {
        em = emf.createEntityManager();
    }

    @Test
    public void testCollaboratorLookupFound() {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Collaborator collaborator = em.find(Collaborator.class,EXISTING_COLLABORATOR_ID);
            assertNotNull(collaborator);

            Collaborator refCollaborator = getExistingReferenceCollaborator();

            assertEquals(refCollaborator,collaborator);
            assertEquals(refCollaborator.getAddress(),collaborator.getAddress());

            assertEquals(refCollaborator.getContactDetails().size(), collaborator.getContactDetails().size());
            refCollaborator.getContactDetails().forEach(dr -> assertTrue("Contact " + dr + " not found",
                    collaborator.getContactDetails().stream()
                            .anyMatch(di -> dr.equals(di))));

            assertEquals(refCollaborator.getOfferedFavours().size(),collaborator.getOfferedFavours().size());
            refCollaborator.getOfferedFavours().forEach(fr -> assertTrue("Favour " + fr + " not found",
                    collaborator.getOfferedFavours().stream()
                            .anyMatch(fi -> fr.equals(fi))));


            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
            assertTrue("Unexpected exception " + e, false);
        }
    }

    @Test
    public void testCollaboratorLookupNotFound() {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Collaborator collaborator = em.find(Collaborator.class,INEXISTENT_COLLABORATOR_ID);
            assertNull(collaborator);
            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
            assertTrue("Unexpected exception " + e, false);
        }
    }


    public void testCollaboratorCreationOk() {

    }

    @Test
    public void testCollaboratorUpdateOk() {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Collaborator collaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            assertNotNull(collaborator);
            assertEquals(COLLABORATOR_TO_UPDATE_EMAIL,collaborator.getEmail());

            collaborator.setFirstName("UpdatedFirstName");
            collaborator.setLastName("UpdatedLastName");
            collaborator.setNickName("UpdatedNickName");
            collaborator.setPassword("UpdatedPassword");

            tx.commit();

            em.detach(collaborator);

            tx.begin();
            Collaborator lookupCollaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            tx.commit();

            assertNotNull(collaborator);
            assertEquals("UpdatedFirstName",collaborator.getFirstName());
            assertEquals("UpdatedLastName",collaborator.getLastName());
            assertEquals("UpdatedNickName",collaborator.getNickName());
            assertEquals("UpdatedPassword",collaborator.getPassword());

        }
        catch (Exception e) {
            tx.rollback();
            assertTrue("Unexpected exception " + e, false);
        }
    }

    @Test
    public void testCollaboratorUpdateEmailDuplicatedNOk() {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Collaborator collaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            assertNotNull(collaborator);
            assertEquals(COLLABORATOR_TO_UPDATE_EMAIL,collaborator.getEmail());

            collaborator.setEmail(EXISTING_COLLABORATOR_EMAIL);
            tx.commit();
            assertTrue("The update shouldn't have happened due to email duplicity", false);

        }
        catch (Exception e) {
            if ((e instanceof PersistenceException)==false) {
                tx.rollback();
                assertTrue("Unexpected exception " + e, false);
            }

        }

    }

    @Test
    public void testCollaboratorUpdateAddressOK() {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Collaborator collaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            assertNotNull(collaborator);
            assertEquals(COLLABORATOR_TO_UPDATE_EMAIL,collaborator.getEmail());

            Address address = collaborator.getAddress();
            address.setAddress("Updated Address");
            address.setZipCode("Updated Zipcode");
            address.setCity("Updated City");
            address.getCountry().setId(2);

            tx.commit();

            em.detach(collaborator);

            tx.begin();
            Collaborator lookupCollaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            Address lookupAddress = lookupCollaborator.getAddress();
            Country lookupCountry=lookupAddress.getCountry();
            tx.commit();

            assertNotNull(collaborator);
            assertEquals("Updated Address",lookupAddress.getAddress());
            assertEquals("Updated Zipcode",lookupAddress.getZipCode());
            assertEquals("Updated City",lookupAddress.getCity());
            assertEquals(2, lookupCountry.getId().intValue());
            assertEquals(COUNTRY_TO_UPDATE,lookupCountry.getCountry());


        }
        catch (Exception e) {
            tx.rollback();
            assertTrue("Unexpected exception " + e, false);
        }

    }

    public void testCollaboratorUpdateContactOK() {

    }

    public void testCollaboratorAddContactOK() {

    }

    public void testCollaboratorDeleteContactOK() {

    }

    public void testCollaboratorUpdateFavourOK() {

    }

    public void testCollaboratorAddFavourOK() {

    }

    public void testCollaboratorDeleteFavourOK() {

    }

    public void deleteCollaboratorOK() {

    }


    @After
    public void destroyEntityManager() {
        em.close();
    }

    @AfterClass
    public static void tearDown() {
        emf.close();
    }

    private Collaborator getExistingReferenceCollaborator() {
        Collaborator existingCollaborator = new Collaborator();

        existingCollaborator.setId(EXISTING_COLLABORATOR_ID);
        existingCollaborator.setEmail("ExistingEmail");
        existingCollaborator.setPassword("ExistingPassword");
        existingCollaborator.setFirstName("ExistingFirstName");
        existingCollaborator.setLastName("ExistingLastName");
        existingCollaborator.setNickName("ExistingNickName");
        existingCollaborator.setCreationDate(new Date(new GregorianCalendar(2015,5,8,9,0,15)
                                                           .getTimeInMillis()));
        existingCollaborator.setLastModifiedAt(null);
        existingCollaborator.setActive(true);

        existingCollaborator.setAddress(getExistingReferenceAddress(EXISTING_COLLABORATOR_ID));
        existingCollaborator.setContactDetails(getExistingReferenceContactDetails());
        existingCollaborator.setOfferedFavours(getExistingReferenceOfferedFavours());

        return existingCollaborator;
    }

    private Address getExistingReferenceAddress(int collaboratorId) {

        Address existingAddress = new Address();
        existingAddress.setId(collaboratorId);
        existingAddress.setAddress("Existing Address");
        existingAddress.setCity("ExistingCity");
        existingAddress.setCountry(getExistingReferenceCountry());
        existingAddress.setZipCode("Z1");

        return existingAddress;

    }

    private Country getExistingReferenceCountry() {
        Country existingCountry = new Country();
        existingCountry.setId(1);
        existingCountry.setCountry("ExistingCountry");
        return existingCountry;
    }

    private List<ContactDetail> getExistingReferenceContactDetails() {

        ContactDetail existingContact1, existingContact2, existingContact3;
        ContactType contactType1, contactType2, contactType3;

        contactType1 = new ContactType();
        contactType1.setId(1);
        contactType1.setType("ExistingType");

        existingContact1 = new ContactDetail();
        existingContact1.setId(1);
        existingContact1.setContact("ExistingContact");
        existingContact1.setType(contactType1);
        existingContact1.setPreferred(true);
        existingContact1.setActive(true);

        contactType2 = new ContactType();
        contactType2.setId(2);
        contactType2.setType("TypeToUpdate");

        existingContact2 = new ContactDetail();
        existingContact2.setId(2);
        existingContact2.setContact("ContactToUpdate");
        existingContact2.setType(contactType2);
        existingContact2.setPreferred(false);
        existingContact2.setActive(true);

        contactType3 = new ContactType();
        contactType3.setId(3);
        contactType3.setType("TypeToDelete");

        existingContact3 = new ContactDetail();
        existingContact3.setId(3);
        existingContact3.setContact("ContactToDelete");
        existingContact3.setType(contactType3);
        existingContact3.setPreferred(false);
        existingContact3.setActive(true);

        return Arrays.asList(existingContact1,existingContact2,existingContact3);

    }

    private List<OfferedFavour> getExistingReferenceOfferedFavours() {

        OfferedFavour offeredFavour1, offeredFavour2, offeredFavour3;

        offeredFavour1 = new OfferedFavour();
        offeredFavour1.setId(1);
        offeredFavour1.setFavour("ExistingFavour");

        offeredFavour2 = new OfferedFavour();
        offeredFavour2.setId(2);
        offeredFavour2.setFavour("FavourToUpdate");

        offeredFavour3 = new OfferedFavour();
        offeredFavour3.setId(3);
        offeredFavour3.setFavour("FavourToDelete");

        return Arrays.asList(offeredFavour1,offeredFavour2,offeredFavour3);

    }
}
