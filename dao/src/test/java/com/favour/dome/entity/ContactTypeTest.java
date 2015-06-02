package com.favour.dome.entity;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

/**
 * Created by fernando on 03/06/15.
 */
public class ContactTypeTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    private static final String EXISTING_TYPE_NAME= "ExistingType";
    private static final String TYPE_TO_UPDATE_NAME= "TypeToUpdate";
    private static final String TYPE_TO_DELETE_NAME= "TypeToDelete";

    private static final int INEXISTENT_TYPE_ID = 0;
    private static final int EXISTING_TYPE_ID = 1;
    private static final int TYPE_TO_UPDATE_ID= 2;
    private static final int TYPE_TO_DELETE_ID= 3;

    @BeforeClass
    public static void initialize() {
        emf = Persistence.createEntityManagerFactory("favourhub-test");
        em=emf.createEntityManager();
    }

    @Test
    public void testContactTypeLookupContactFound(){

        EntityTransaction trx = em.getTransaction();
        trx.begin();
        try {
            ContactType testContactType = em.find(ContactType.class, EXISTING_TYPE_ID);
            trx.commit();

            assertNotNull(testContactType);
            assertEquals(EXISTING_TYPE_NAME,testContactType.getType());
        }
        catch (Exception e) {
            trx.rollback();
            assertTrue("Unexpected exception " + e,false);
        }
    }

    @Test
    public void testContactTypeLookupContactNotFound(){

        EntityTransaction trx = em.getTransaction();
        trx.begin();
        try {
            ContactType testContactType = em.find(ContactType.class, INEXISTENT_TYPE_ID);
            trx.commit();

            assertNull(testContactType);
        }
        catch (Exception e) {
            trx.rollback();
            assertTrue("Unexpected exception " + e,false);
        }
    }

    @Test
    public void testContactTypeCreationOk(){

        EntityTransaction trx = em.getTransaction();
        trx.begin();
        try {
            ContactType testContactType = new ContactType();
            testContactType.setType("NewType");
            em.persist(testContactType);
            trx.commit();

            assertNotNull(testContactType.getId());
            em.detach(testContactType);

            trx.begin();
            ContactType lookupContactType = em.find(ContactType.class,testContactType.getId());
            assertEquals(testContactType,lookupContactType);
            trx.commit();
        }
        catch (Exception e) {
            trx.rollback();
            assertTrue("Unexpected exception " + e,false);
        }
    }

    @Test
    public void testContactTypeUpdateOk(){


        EntityTransaction trx = em.getTransaction();
        trx.begin();
        try {
            ContactType testContactType = em.find(ContactType.class, TYPE_TO_UPDATE_ID);
            assertNotNull(testContactType);

            testContactType.setType("UpdatedContactType");
            trx.commit();

            em.detach(testContactType);

            trx.begin();
            ContactType lookupContactType = em.find(ContactType.class,TYPE_TO_UPDATE_ID);
            trx.commit();

            assertEquals("UpdatedContactType",lookupContactType.getType());
        }
        catch (Exception e) {
            trx.rollback();
            assertTrue("Unexpected exception " + e,false);
        }

    }

    @Test
    public void testContactTypeDeleteOk(){

        EntityTransaction trx = em.getTransaction();
        trx.begin();
        try {
            ContactType testContactType = em.find(ContactType.class, TYPE_TO_DELETE_ID);
            assertNotNull(testContactType);
            assertEquals(TYPE_TO_DELETE_NAME,testContactType.getType());

            em.remove(testContactType);
            trx.commit();


            trx.begin();
            ContactType lookupContactType = em.find(ContactType.class,TYPE_TO_DELETE_ID);
            trx.commit();

            assertNull(lookupContactType);
        }
        catch (Exception e) {
            trx.rollback();
            assertTrue("Unexpected exception " + e,false);
        }
    }


    @AfterClass
    public static void teardown() {
        em.close();
        emf.close();
    }
}
