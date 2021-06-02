package sample.JPA;

import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.service.spi.ServiceException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

public class CategoriesDAO {


    public static void insert(Categories categories) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        entityManager.persist(categories);

        entityManager.getTransaction().commit();
        entityManager.close();
    }


    public static List<Categories> searchByName(String name) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Categories> query = entityManager.createQuery("Select e From Categories e WHERE e.name = ?1", Categories.class);
        List<Categories> categories = query.setParameter(1, name).getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return categories;
    }


    public static void deleteById(int id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Categories categories = entityManager.find(Categories.class, id);
        entityManager.remove(categories);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static void updateById(Categories categories, int id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Categories categories1 = entityManager.find(Categories.class, id);
        categories1.setName(categories.getName());
        categories1.setlft(categories.getlft());
        categories1.setrght(categories.getrght());

        entityManager.getTransaction().commit();
        entityManager.close();
    }


    public static List<Categories> selectCategory(String name) {

        EntityManager entityManager;
        EntityTransaction entityTransaction;
        TypedQuery<Categories> query;
        List<Categories> categories = null;

        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            query = entityManager.createQuery("SELECT e FROM Categories e" +
                    " JOIN Categories a  ON (e.lft >= a.lft AND e.rght <= a.rght) " +
                    "WHERE a.name = ?1 ORDER BY e.lft", Categories.class);
            categories = query.setParameter(1, name).getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (IllegalStateException e) {
            JPAUtil.infoBox("NEPAVYKO PRISIJUNGTI PRIE DUOMENŲ BAZĖS", "IllegalStateException");
        } catch (JDBCConnectionException e) {
            JPAUtil.infoBox("NEPAVYKO PRISIJUNGTI PRIE DUOMENŲ BAZĖS", "JDBCConnectionException");
        } catch (ServiceException e) {
            JPAUtil.infoBox("NEPAVYKO PRISIJUNGTI PRIE DUOMENŲ BAZĖS", "ServiceException");
        } catch (PersistenceException e) {
            JPAUtil.infoBox("NEPAVYKO PRISIJUNGTI PRIE DUOMENŲ BAZĖS", "PersistenceException");
        }

        return categories;

    }


    public static List<Categories> displayAllCategories(){
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Categories> query = entityManager.createQuery("Select e From Categories e", Categories.class);
        List<Categories> categories = query.getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return categories;
    }

    public static List<String> selectAllCategoryNames() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<String> query = entityManager.createQuery("SELECT CONCAT(REPEAT('|', count(parent.name) - 2), CASE WHEN (count(parent.name) - 2 <= 0) THEN node.name ELSE CONCAT('- ', node.name) END) AS categoryNames FROM Categories as node INNER JOIN Categories as parent ON ( parent.lft <= node.lft AND parent.rght >= node.lft ) GROUP BY node.id ORDER BY node.lft", String.class);
        List<String> categories = query.getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return categories;
    }

}

