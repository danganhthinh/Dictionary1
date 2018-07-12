/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author thinh
 */
@Entity
@Table(name = "test2", catalog = "test", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Test2.findAll", query = "SELECT t FROM Test2 t")
    , @NamedQuery(name = "Test2.findByWord", query = "SELECT t FROM Test2 t WHERE t.word = :word")
    , @NamedQuery(name = "Test2.findByMean", query = "SELECT t FROM Test2 t WHERE t.mean = :mean")})

public class Test2 implements Serializable {

    private static final long serialVersionUID = 1L;
    private static EntityManagerFactory em = Persistence.createEntityManagerFactory("");
    public static EntityManager entityManager = em.createEntityManager();

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "word")
    private String word;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "mean")
    private String mean;

    public Test2() {
    }

    public Test2(String word) {
        this.word = word;
    }

    public Test2(String word, String mean) {
        this.word = word;
        this.mean = mean;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (word != null ? word.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Test2)) {
            return false;
        }
        Test2 other = (Test2) object;
        if ((this.word == null && other.word != null) || (this.word != null && !this.word.equals(other.word))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "demo.Test2[ word=" + word + " ]";
    }

    public Test2 saveWordMean() {

        Test2.entityManager.getTransaction().begin();
        Test2.entityManager.persist(this);
        Test2.entityManager.getTransaction().commit();

        return null;
    }

    public static List<Test2> getMean(String w) {
        Query q = entityManager.createQuery("SELECT t FROM TEST2 t where t.word").setParameter(1, w);
        List<Test2> list = q.getResultList();
        return list;
    }
}
