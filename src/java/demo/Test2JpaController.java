/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import demo.exceptions.NonexistentEntityException;
import demo.exceptions.PreexistingEntityException;
import demo.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author thinh
 */
@ManagedBean(name = "contrl", eager = true)

public class Test2JpaController implements Serializable {

    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;
    private String word;

    public Test2JpaController(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
    
    public String getMean(){
        List<Test2> list = Test2.getMean(word);
        
        if (list.size() > 0) {
            StringBuilder  sb = new StringBuilder();
            
            for (Test2 test2 : list) {
                sb.append(test2.getWord());
                sb.append(" : ");
                sb.append(test2.getMean());
            }
            return sb.toString();
        }
        
        return "Can not find the word!";   
    }
    

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Test2 test2;
            try {
                test2 = em.getReference(Test2.class, id);
                test2.getWord();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException(" id " + id + "  exists.", enfe);
            }
            em.remove(test2);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException(".", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Test2 findTest2(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Test2.class, id);
        } finally {
            em.close();
        }
    }
    
}
