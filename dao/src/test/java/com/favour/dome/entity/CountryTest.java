package com.favour.dome.entity;

import org.junit.*;

import javax.persistence.*;

import static org.junit.Assert.*;

/**
 * Created by fernando on 03/06/15.
 */
public class CountryTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;


    private static final String EXISTING_COUNTRY_NAME= "ExistingCountry";
    private static final String COUNTRY_TO_UPDATE_NAME= "CountryToUpdate";
    private static final String COUNTRY_TO_DELETE_REFERENCED_NAME = "OtherCountry";
    private static final String COUNTRY_TO_DELETE_NAME= "CountryToDelete";

    private static final int INEXISTENT_COUNTRY_ID = 0;
    private static final int EXISTING_COUNTRY_ID = 1;
    private static final int COUNTRY_TO_UPDATE_ID= 2;
    private static final int COUNTRY_TO_DELETE_REFERENCED_ID= 3;
    private static final int COUNTRY_TO_DELETE_ID= 4;

    @BeforeClass
    public static void initialize() {
        emf = Persistence.createEntityManagerFactory("favourhub-test");
    }

    @Before
    public void initializeEntityManager() {
        em = emf.createEntityManager();
    }

    @Test
    public void testCountryLookupCountryFound() {

        EntityTransaction trx = em.getTransaction();

        trx.begin();
        try {
            Country testCountry = em.find(Country.class, EXISTING_COUNTRY_ID);
            assertNotNull(testCountry);
            assertEquals(EXISTING_COUNTRY_NAME, testCountry.getCountry());
            trx.commit();
        }
        catch (Exception e) {
            trx.rollback();
            assertTrue("Unexpected exception " + e,true);
        }
    }

    @Test
    public void testCountryLookupCountryNotFound() {

        EntityTransaction trx = em.getTransaction();

        trx.begin();
        try {
            Country testCountry = em.find(Country.class, INEXISTENT_COUNTRY_ID);
            assertNull(testCountry);
            trx.commit();
        }
        catch (Exception e) {
            trx.rollback();
            assertTrue("Unexpected exception " + e,true);
        }
    }

    @Test
    public void testCountryCreationOK() {

        EntityTransaction trx = em.getTransaction();

        trx.begin();
        try {
            Country testCountry = new Country();
            testCountry.setCountry("NewCountry");
            em.persist(testCountry);
            trx.commit();

            assertNotNull(testCountry.getId());
            em.detach(testCountry);

            trx.begin();
            Country lookupCountry = em.find(Country.class, testCountry.getId());
            trx.commit();

            assertEquals(testCountry, lookupCountry);
        }
        catch (Exception e) {
            trx.rollback();
            assertTrue("Unexpected exception " + e,true);
        }

    }


    @Test
    public void testCountryUpdateOk() {

        EntityTransaction trx = em.getTransaction();

        trx.begin();
        try {
            Country testCountry = em.find(Country.class, COUNTRY_TO_UPDATE_ID);
            assertNotNull(testCountry);
            assertEquals(COUNTRY_TO_UPDATE_NAME, testCountry.getCountry());

            testCountry.setCountry("UpdatedCountry");
            trx.commit();
            em.detach(testCountry);

            trx.begin();
            Country updatedCountry= em.find(Country.class, COUNTRY_TO_UPDATE_ID);
            trx.commit();

            assertNotNull(testCountry);
            assertEquals("UpdatedCountry", testCountry.getCountry());

        }
        catch (Exception e) {
            trx.rollback();
            assertTrue("Unexpected exception " + e,true);
        }

    }

    @Test
    public void testCountryDeleteOk() {

        EntityTransaction trx = em.getTransaction();

        trx.begin();
        try {
            Country testCountry = em.find(Country.class, COUNTRY_TO_DELETE_ID);
            assertNotNull(testCountry);
            assertEquals(COUNTRY_TO_DELETE_NAME, testCountry.getCountry());

            em.remove(testCountry);
            trx.commit();;

            trx.begin();
            Country updatedCountry= em.find(Country.class, COUNTRY_TO_DELETE_ID);
            trx.commit();

            assertNull(updatedCountry);

        }
        catch (Exception e) {
            trx.rollback();
            assertTrue("Unexpected exception",true);
        }

    }

    @Test
    public void testCountryDeleteTypeStillReferencedOk() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Country testCountry = em.find(Country.class,COUNTRY_TO_DELETE_REFERENCED_ID);
            assertNotNull(testCountry);
            assertEquals(COUNTRY_TO_DELETE_REFERENCED_NAME,testCountry.getCountry());

            em.remove(testCountry);
            tx.commit();

            assertTrue("The delete shouldn't have happened due to existent references to the country", false);

        }
        catch (Exception e) {
            if (!(e instanceof PersistenceException)) {
                tx.rollback();
                assertTrue("Unexpected exception " + e, false);
            }

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
}
