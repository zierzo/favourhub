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
 * Created by fernando on 08/06/15.
 */
public class FavourTypeTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    private static final String EXISTING_FAVOUR_TYPE_NAME= "ExistingType";
    private static final String FAVOUR_TYPE_TO_UPDATE_NAME= "TypeToUpdate";
    private static final String FAVOUR_TYPE_TO_DELETE_NAME= "TypeToDelete";

    private static final int INEXISTENT_FAVOUR_TYPE_ID = 0;
    private static final int EXISTING_FAVOUR_TYPE_ID = 1;
    private static final int FAVOUR_TYPE_TO_UPDATE_ID= 2;
    private static final int FAVOUR_TYPE_TO_DELETE_ID= 3;

    @BeforeClass
    public static void initialize() {
        emf = Persistence.createEntityManagerFactory("favourhub-test");
        em = emf.createEntityManager();
    }

    @Test
    public void testFavourTypeLookupTypeFound() {

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            FavourType type = em.find(FavourType.class,EXISTING_FAVOUR_TYPE_ID);
            assertNotNull(type);
            assertEquals(EXISTING_FAVOUR_TYPE_NAME,type.getType());
            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
            assertTrue("Unexpected exception " + e, false);
        }
    }

    @Test
    public void testFavourTypeLookupTypeNotFound() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            FavourType type = em.find(FavourType.class,INEXISTENT_FAVOUR_TYPE_ID);
            assertNull(type);
            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
            assertTrue("Unexpected exception " + e, false);
        }
    }

    @Test
    public void testFavourTypeCreationOk() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            FavourType testType = new FavourType();
            testType.setType("NewType");
            em.persist(testType);
            tx.commit();

            assertNotNull(testType.getId());
            em.detach(testType);

            tx.begin();
            FavourType lookupType = em.find(FavourType.class,testType.getId());
            tx.commit();

            assertEquals(testType,lookupType);

        }
        catch (Exception e) {
            tx.rollback();
            assertTrue("Unexpected exception " + e, false);
        }
    }

    @Test
    public void testFavourTypeUpdateOk() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            FavourType testFavour = em.find(FavourType.class,FAVOUR_TYPE_TO_UPDATE_ID);
            assertNotNull(testFavour);
            assertEquals(FAVOUR_TYPE_TO_UPDATE_NAME,testFavour.getType());

            testFavour.setType("UpdatedType");
            tx.commit();

            tx.begin();
            FavourType lookupType=em.find(FavourType.class,FAVOUR_TYPE_TO_UPDATE_ID);
            tx.commit();

            assertNotNull(lookupType);
            assertEquals("UpdatedType",lookupType.getType());
        }
        catch (Exception e) {
            tx.rollback();
            assertTrue("Unexpected exception " + e, false);
        }
    }

    @Test
    public void testFavourTypeDeleteOk() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            FavourType testFavour = em.find(FavourType.class,FAVOUR_TYPE_TO_DELETE_ID);
            assertNotNull(testFavour);
            assertEquals(FAVOUR_TYPE_TO_DELETE_NAME,testFavour.getType());

            em.remove(testFavour);
            tx.commit();

            tx.begin();
            FavourType lookupType=em.find(FavourType.class,FAVOUR_TYPE_TO_UPDATE_ID);
            tx.commit();

            assertNull(lookupType);

        }
        catch (Exception e) {
            tx.rollback();
            assertTrue("Unexpected exception " + e, false);
        }
    }

    @AfterClass
    public static void tearDown() {
        em.close();
        emf.close();
    }
}
