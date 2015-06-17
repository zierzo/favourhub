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
    private static final String COLLABORATOR_TO_DELETE_EMAIL= "EmailToDelete";

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

            Collaborator testCollaborator = em.find(Collaborator.class,EXISTING_COLLABORATOR_ID);
            assertNotNull(testCollaborator);

            Collaborator refCollaborator = getReferenceCollaborator();

            assertEquals(refCollaborator,testCollaborator);
            assertEquals(refCollaborator.getAddress(), testCollaborator.getAddress());

            assertEquals(refCollaborator.getContactDetails().size(), testCollaborator.getContactDetails().size());
            refCollaborator.getContactDetails().forEach(dr -> assertTrue("Contact " + dr + " not found",
                    testCollaborator.getContactDetails().stream()
                            .anyMatch(dr::equals)));

            assertEquals(refCollaborator.getOfferedFavours().size(), testCollaborator.getOfferedFavours().size());
            refCollaborator.getOfferedFavours().forEach(fr -> assertTrue("Favour " + fr + " not found",
                    testCollaborator.getOfferedFavours().stream()
                            .anyMatch(fr::equals)));


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
            Collaborator testCollaborator = em.find(Collaborator.class,INEXISTENT_COLLABORATOR_ID);
            assertNull(testCollaborator);
            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
            assertTrue("Unexpected exception " + e, false);
        }
    }

    @Test
    public void testCollaboratorCreationOk() {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Collaborator testCollaborator=getNewCollaborator();
            em.persist(testCollaborator);

            tx.commit();

            assertNotNull(testCollaborator.getId());
            assertNotNull(testCollaborator.getAddress().getId());

            testCollaborator.getContactDetails().forEach(cd->assertNotNull(cd.getId()));
            testCollaborator.getOfferedFavours().forEach(f -> assertNotNull(f.getId()));

            em.detach(testCollaborator);

            tx.begin();
            Collaborator lookupCollaborator=em.find(Collaborator.class,testCollaborator.getId());
            tx.commit();

            assertEquals(testCollaborator,lookupCollaborator);

        }
        catch (Exception e) {
            tx.rollback();
            assertTrue("Unexpected exception " + e, false);
        }

    }

    @Test
    public void testCollaboratorUpdateOk() {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Collaborator testCollaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            assertNotNull(testCollaborator);
            assertEquals(COLLABORATOR_TO_UPDATE_EMAIL, testCollaborator.getEmail());

            testCollaborator.setFirstName("UpdatedFirstName");
            testCollaborator.setLastName("UpdatedLastName");
            testCollaborator.setNickName("UpdatedNickName");
            testCollaborator.setPassword("UpdatedPassword");

            tx.commit();

            em.detach(testCollaborator);

            tx.begin();
            Collaborator lookupCollaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            tx.commit();

            assertNotNull(testCollaborator);
            assertEquals("UpdatedFirstName", lookupCollaborator.getFirstName());
            assertEquals("UpdatedLastName", lookupCollaborator.getLastName());
            assertEquals("UpdatedNickName", lookupCollaborator.getNickName());
            assertEquals("UpdatedPassword", lookupCollaborator.getPassword());

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

            Collaborator testCollaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            assertNotNull(testCollaborator);
            assertEquals(COLLABORATOR_TO_UPDATE_EMAIL, testCollaborator.getEmail());

            testCollaborator.setEmail(EXISTING_COLLABORATOR_EMAIL);
            tx.commit();

            assertTrue("The update shouldn't have happened due to email duplicity", false);

        }
        catch (Exception e) {
            if (!(e instanceof PersistenceException)) {
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

            Collaborator testCollaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            assertNotNull(testCollaborator);
            assertEquals(COLLABORATOR_TO_UPDATE_EMAIL, testCollaborator.getEmail());

            Address address = testCollaborator.getAddress();
            address.setAddress("Updated Address");
            address.setZipCode("Updated Zipcode");
            address.setCity("Updated City");
            address.getCountry().setId(2);

            tx.commit();

            em.detach(testCollaborator);

            tx.begin();
            Collaborator lookupCollaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            Address lookupAddress = lookupCollaborator.getAddress();
            Country lookupCountry=lookupAddress.getCountry();
            tx.commit();

            assertNotNull(testCollaborator);
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

    @Test
    public void testCollaboratorUpdateContactOK() {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Collaborator testCollaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            assertNotNull(testCollaborator);
            assertEquals(COLLABORATOR_TO_UPDATE_EMAIL, testCollaborator.getEmail());

            List<ContactDetail> contactDetails=testCollaborator.getContactDetails();
            ContactDetail contactDetail=contactDetails.get(0);

            ContactType updatedContactType=new ContactType();
            updatedContactType.setId(2);
            updatedContactType.setType("TypeToUpdate");

            contactDetail.setContact("UpdatedContact");
            contactDetail.setActive(!contactDetail.isActive());
            contactDetail.setPreferred(!contactDetail.isPreferred());
            contactDetail.setType(updatedContactType);

            tx.commit();

            em.detach(testCollaborator);

            tx.begin();
            Collaborator lookupCollaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            List<ContactDetail> lookupContactDetails=lookupCollaborator.getContactDetails();
            tx.commit();

            ContactDetail updatedContactDetail=lookupContactDetails.stream()
                                                .filter(cd->cd.getId().intValue()==contactDetail.getId().intValue())
                                                 .findFirst().get();
            assertEquals(contactDetail,updatedContactDetail);

        }
        catch (Exception e) {
            tx.rollback();
            assertTrue("Unexpected exception " + e, false);
        }
    }

    @Test
    public void testCollaboratorAddContactOK() {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Collaborator testCollaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            assertNotNull(testCollaborator);
            assertEquals(COLLABORATOR_TO_UPDATE_EMAIL, testCollaborator.getEmail());

            List<ContactDetail> contactDetails=testCollaborator.getContactDetails();

            ContactType contactType=new ContactType();
            contactType.setId(2);
            contactType.setType("TypeToUpdate");

            ContactDetail newContactDetail= new ContactDetail();
            newContactDetail.setContact("newContact");
            newContactDetail.setActive(false);
            newContactDetail.setPreferred(false);
            newContactDetail.setType(contactType);

            contactDetails.add(newContactDetail);

            tx.commit();

            assertNotNull(newContactDetail.getId());
            em.detach(testCollaborator);

            tx.begin();
            Collaborator lookupCollaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            List<ContactDetail> lookupContactDetails=lookupCollaborator.getContactDetails();
            tx.commit();

            ContactDetail newLookupContactDetail=lookupContactDetails.stream()
                                                .filter(cd -> cd.getId().intValue() == newContactDetail.getId().intValue())
                                                .findFirst().get();
            assertEquals(newContactDetail,newLookupContactDetail);

        }
        catch (Exception e) {
            tx.rollback();
            assertTrue("Unexpected exception " + e, false);
        }

    }

    @Test
    public void testCollaboratorAddContactInexistendContactTypeNOK() {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Collaborator testCollaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            assertNotNull(testCollaborator);
            assertEquals(COLLABORATOR_TO_UPDATE_EMAIL, testCollaborator.getEmail());

            List<ContactDetail> contactDetails=testCollaborator.getContactDetails();

            ContactType contactType=new ContactType();
            contactType.setId(200);
            contactType.setType("TypeToUpdate");

            ContactDetail newContactDetail= new ContactDetail();
            newContactDetail.setContact("newContact");
            newContactDetail.setActive(false);
            newContactDetail.setPreferred(false);
            newContactDetail.setType(contactType);

            contactDetails.add(newContactDetail);

            tx.commit();

            assertTrue("The insertion shouldn't have happened due to inexistent contact type", false);

        }
        catch (Exception e) {
            if (!(e instanceof PersistenceException)) {
                tx.rollback();
                assertTrue("Unexpected exception " + e, false);
            }

        }

    }

    @Test
    public void testCollaboratorDeleteContactOK() {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Collaborator testCollaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            assertNotNull(testCollaborator);
            assertEquals(COLLABORATOR_TO_UPDATE_EMAIL, testCollaborator.getEmail());

            List<ContactDetail> contactDetails=testCollaborator.getContactDetails();
            ContactDetail contactDetailToDelete = contactDetails.get(0);
            contactDetails.remove(contactDetailToDelete);

            tx.commit();

            em.detach(testCollaborator);

            tx.begin();
            Collaborator lookupCollaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            List<ContactDetail> lookupContactDetails=lookupCollaborator.getContactDetails();
            tx.commit();

            boolean deletedContactFound=lookupContactDetails.stream()
                    .filter(cd -> cd.getId().intValue() == contactDetailToDelete.getId().intValue())
                    .findFirst().isPresent();
            assertFalse("Contact detail " + contactDetailToDelete + " shouldn't be present", deletedContactFound);

        }
        catch (Exception e) {
            tx.rollback();
            assertTrue("Unexpected exception " + e, false);
        }

    }

    @Test
    public void testCollaboratorUpdateFavourOK() {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Collaborator testCollaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            assertNotNull(testCollaborator);
            assertEquals(COLLABORATOR_TO_UPDATE_EMAIL, testCollaborator.getEmail());

            List<OfferedFavour> offeredFavours=testCollaborator.getOfferedFavours();
            OfferedFavour offeredFavour=offeredFavours.get(0);
            offeredFavour.setFavour("UpdatedFavour");

            tx.commit();

            em.detach(testCollaborator);

            tx.begin();
            Collaborator lookupCollaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            List<OfferedFavour> lookupFavours=lookupCollaborator.getOfferedFavours();
            tx.commit();

            OfferedFavour updatedFavour=lookupFavours.stream()
                    .filter(f->f.getId().intValue()==offeredFavour.getId().intValue())
                    .findFirst().get();
            assertEquals(offeredFavour,updatedFavour);

        }
        catch (Exception e) {
            tx.rollback();
            assertTrue("Unexpected exception " + e, false);
        }
    }

    @Test
    public void testCollaboratorAddFavourOK() {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Collaborator testCollaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            assertNotNull(testCollaborator);
            assertEquals(COLLABORATOR_TO_UPDATE_EMAIL, testCollaborator.getEmail());

            List<OfferedFavour> offeredFavours=testCollaborator.getOfferedFavours();
            OfferedFavour newFavour=new OfferedFavour();
            newFavour.setFavour("newFavour");
            offeredFavours.add(newFavour);

            tx.commit();

            assertNotNull(newFavour.getId());
            em.detach(testCollaborator);

            tx.begin();
            Collaborator lookupCollaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            List<OfferedFavour> lookupOfferedFavours=lookupCollaborator.getOfferedFavours();
            tx.commit();

            OfferedFavour newLookupOfferedFavour=lookupOfferedFavours.stream()
                    .filter(f -> f.getId().intValue() == newFavour.getId().intValue())
                    .findFirst().get();
            assertEquals(newFavour,newLookupOfferedFavour);

        }
        catch (Exception e) {
            tx.rollback();
            assertTrue("Unexpected exception " + e, false);
        }

    }



    @Test
    public void testCollaboratorDeleteFavourOK() {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Collaborator testCollaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            assertNotNull(testCollaborator);
            assertEquals(COLLABORATOR_TO_UPDATE_EMAIL, testCollaborator.getEmail());

            List<OfferedFavour> offeredFavours=testCollaborator.getOfferedFavours();
            OfferedFavour favourToDelete = offeredFavours.get(0);
            offeredFavours.remove(favourToDelete);

            tx.commit();

            em.detach(testCollaborator);

            tx.begin();
            Collaborator lookupCollaborator = em.find(Collaborator.class,COLLABORATOR_TO_UPDATE_ID);
            List<OfferedFavour> lookupOfferedFavours=lookupCollaborator.getOfferedFavours();
            tx.commit();

            boolean deletedFavourFound=lookupOfferedFavours.stream()
                    .filter(f -> f.getId().intValue() == favourToDelete.getId().intValue())
                    .findFirst().isPresent();
            assertFalse("Favour " + favourToDelete + " shouldn't be present", deletedFavourFound);

        }
        catch (Exception e) {
            tx.rollback();
            assertTrue("Unexpected exception " + e, false);
        }

    }

    @Test
    public void deleteCollaboratorOK() {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Collaborator testCollaborator=em.find(Collaborator.class,COLLABORATOR_TO_DELETE_ID);
            assertNotNull(testCollaborator);
            assertEquals(COLLABORATOR_TO_DELETE_EMAIL, testCollaborator.getEmail());

            em.remove(testCollaborator);

            tx.commit();

            tx.begin();
            Collaborator lookupCollaborator=em.find(Collaborator.class,COLLABORATOR_TO_DELETE_ID);
            tx.commit();

            assertNull(lookupCollaborator);

        }
        catch (Exception e) {
            tx.rollback();
            assertTrue("Unexpected exception " + e, false);
        }
    }


    @After
    public void destroyEntityManager() {
        em.close();
    }

    @AfterClass
    public static void tearDown() {
        emf.close();
    }

    private Collaborator getReferenceCollaborator() {

        Collaborator referenceCollaborator = new Collaborator();

        referenceCollaborator.setId(EXISTING_COLLABORATOR_ID);
        referenceCollaborator.setEmail("ExistingEmail");
        referenceCollaborator.setPassword("ExistingPassword");
        referenceCollaborator.setFirstName("ExistingFirstName");
        referenceCollaborator.setLastName("ExistingLastName");
        referenceCollaborator.setNickName("ExistingNickName");
        referenceCollaborator.setCreationDate(new Date(new GregorianCalendar(2015, 5, 8, 9, 0, 15)
                .getTimeInMillis()));
        referenceCollaborator.setLastModifiedAt(null);
        referenceCollaborator.setActive(true);

        referenceCollaborator.setAddress(getReferenceAddress(EXISTING_COLLABORATOR_ID));
        referenceCollaborator.setContactDetails(getReferenceContactDetails());
        referenceCollaborator.setOfferedFavours(getReferenceOfferedFavours());

        return referenceCollaborator;

    }

    private Address getReferenceAddress(int collaboratorId) {

        Address existingAddress = new Address();
        existingAddress.setId(collaboratorId);
        existingAddress.setAddress("Existing Address");
        existingAddress.setCity("ExistingCity");
        existingAddress.setCountry(getReferenceCountry());
        existingAddress.setZipCode("Z1");

        return existingAddress;

    }

    private Country getReferenceCountry() {

        Country existingCountry = new Country();
        existingCountry.setId(1);
        existingCountry.setCountry("ExistingCountry");
        return existingCountry;
    }

    private List<ContactDetail> getReferenceContactDetails() {

        ContactDetail referenceContact1, referenceContact2,referenceContact3;

        referenceContact1 = getFirstReferenceContactDetail();
        referenceContact2 = getSecondReferenceContactDetail();
        referenceContact3 = getThirdReferenceContactDetail();

        return Arrays.asList(referenceContact1,referenceContact2,referenceContact3);

    }

    private ContactDetail getFirstReferenceContactDetail() {

        ContactType contactType = new ContactType();
        contactType.setId(1);
        contactType.setType("ExistingType");

        ContactDetail referenceContact = new ContactDetail();
        referenceContact.setId(1);
        referenceContact.setContact("ExistingContact");
        referenceContact.setType(contactType);
        referenceContact.setPreferred(true);
        referenceContact.setActive(true);
        return referenceContact;
    }

    private ContactDetail getThirdReferenceContactDetail() {

        ContactType contactType = new ContactType();
        contactType.setId(3);
        contactType.setType("OtherType");

        ContactDetail referenceContact = new ContactDetail();
        referenceContact.setId(3);
        referenceContact.setContact("ContactToDelete");
        referenceContact.setType(contactType);
        referenceContact.setPreferred(false);
        referenceContact.setActive(true);

        return referenceContact;
    }

    private ContactDetail getSecondReferenceContactDetail() {

        ContactType contactType = new ContactType();
        contactType.setId(2);
        contactType.setType("TypeToUpdate");

        ContactDetail referenceContact = new ContactDetail();
        referenceContact.setId(2);
        referenceContact.setContact("ContactToUpdate");
        referenceContact.setType(contactType);
        referenceContact.setPreferred(false);
        referenceContact.setActive(true);

        return referenceContact;
    }



    private List<OfferedFavour> getReferenceOfferedFavours() {

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



    private Collaborator getNewCollaborator() {

        Collaborator newCollaborator = new Collaborator();

        newCollaborator.setEmail("NewEmail");
        newCollaborator.setPassword("NewPassword");
        newCollaborator.setFirstName("NewFirstName");
        newCollaborator.setLastName("NewLastName");
        newCollaborator.setNickName("NewNickName");
        newCollaborator.setCreationDate(new Date(new GregorianCalendar(2015, 5, 16, 9, 0, 15)
                .getTimeInMillis()));
        newCollaborator.setLastModifiedAt(null);
        newCollaborator.setActive(true);

        newCollaborator.setAddress(getNewAddress());
        newCollaborator.setContactDetails(getNewContactDetails());
        newCollaborator.setOfferedFavours(getNewOfferedFavours());

        return newCollaborator;
    }

    private Address getNewAddress() {

        Address newAddress = new Address();
        newAddress.setAddress("New Address");
        newAddress.setCity("NewCity");
        newAddress.setCountry(getNewCountry());
        newAddress.setZipCode("N1");

        return newAddress;

    }

    private Country getNewCountry() {

        Country existingCountry = new Country();
        existingCountry.setId(1);
        existingCountry.setCountry("ExistingCountry");
        return existingCountry;
    }

    private List<ContactDetail> getNewContactDetails() {

        ContactType contactType = new ContactType();
        contactType.setId(1);
        contactType.setType("ExistingType");

        ContactDetail newContact = new ContactDetail();
        newContact.setContact("NewContact");
        newContact.setType(contactType);
        newContact.setPreferred(true);
        newContact.setActive(true);

        return Arrays.asList(newContact);

    }


    private List<OfferedFavour> getNewOfferedFavours() {

        OfferedFavour offeredFavour1, offeredFavour2;

        offeredFavour1 = new OfferedFavour();
        offeredFavour1.setFavour("NewFavour");

        offeredFavour2 = new OfferedFavour();
        offeredFavour2.setFavour("FavourToUpdate");


        return Arrays.asList(offeredFavour1,offeredFavour2);

    }
}
