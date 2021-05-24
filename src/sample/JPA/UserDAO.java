package sample.JPA;

import sample.JPAUtil;
import sample.controller.DashboardController;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class UserDAO {
    public static void insert(User user) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        entityManager.persist(user);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static User searchUserByEmail(String email) {
        try {


            EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            Query query = entityManager.createQuery(
                    "SELECT a FROM User a WHERE a.email = :email2")
                    .setParameter("email2", email);

            query.setMaxResults(1);
            User user = (User) query.getSingleResult();
            entityManager.getTransaction().commit();
            entityManager.close();

            return user;

        } catch (NoResultException nre) {

            return null;
        }

    }


    public static boolean compareEmailForValidation(String email) {
        try {
            EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();


            Query query = entityManager.createQuery(
                    "SELECT a FROM User a WHERE a.email = :email2")
                    .setParameter("email2", email);

            query.setMaxResults(1);
            User user = (User) query.getSingleResult();

            user.getEmail();


            entityManager.getTransaction().commit();
            entityManager.close();
            return true;

        } catch (NoResultException nre) {
            return false;
        }
    }


}
