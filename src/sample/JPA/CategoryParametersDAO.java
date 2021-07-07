package sample.JPA;

import org.hibernate.HibernateException;
import org.hibernate.exception.JDBCConnectionException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class CategoryParametersDAO {

    /*public static List<CategoryParameters> searchByCatalogNo(String catalogNo) {

        EntityManager entityManager;
        EntityTransaction entityTransaction;
        List<CategoryParameters> categoryParameters = null;
        TypedQuery<CategoryParameters> query;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            query = entityManager.createQuery("", CategoryParameters.class);
            categoryParameters = query.setParameter(1, catalogNo).getResultList();


            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (NullPointerException e ) {
            System.out.println("ProductDescriptionDAO.searchByCatalogNo() NullPointerExecption");
        } catch (RuntimeException e) {
//            JPAUtil.infoBox("NEPAVYKO PRISIJUNGTI PRIE DUOMENŲ BAZĖS", "RuntimeException");
//            JPAUtil.infoBox();

        }
        return categoryParameters;
    }*/

    //Paduodamas Categories objektas iš jo ištraukiamas category_parameter_id ir grąžinamas CategoryParameters objektas su kategorijų boolean'ais
    //Pastaba: Grąžinamas objektas gali būti null, jeigu nebus rastas atitikmuo DB
    public static CategoryParameters getParametersByCategoryId(Categories category) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        CategoryParameters categoryParameters = null;

        try {
            TypedQuery<CategoryParameters> query = entityManager.createQuery("Select e From CategoryParameters e WHERE e.id = ?1", CategoryParameters.class);
            categoryParameters = query.setParameter(1, category.getCategory_parameter_id()).getSingleResult();

            entityManager.getTransaction().commit();
            entityManager.close();

        } catch (JDBCConnectionException e) {
            System.out.println("CategoryParametersDAO.getParametersByCategoryId() JDBCConnectionException");
        } catch (HibernateException e) {
            System.out.println("CategoryParametersDAO.getParametersByCategoryId() HibernateException");
        }
        return categoryParameters;
    }

    public static void createNewCategoryParametersField(CategoryParameters categoryParameters) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {
            entityManager.persist(categoryParameters);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (JDBCConnectionException e) {
            System.out.println("CategoryParametersDAO.createNewCategoryParametersField() JDBCConnectionException");
        } catch (HibernateException e) {
            System.out.println("CategoryParametersDAO.createNewCategoryParametersField() HibernateException");
        }
    }

}
