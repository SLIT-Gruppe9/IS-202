package no.uia.slit.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import no.uia.slit.entity.News;
import no.uia.slit.web.Page;
/**
 *
 * @author evenal
 */
@Stateless
public class NewsPersister extends AbstractPersister<News>{

    @PersistenceContext
    EntityManager em;


    public NewsPersister() {
        super(News.class);
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public News createNews(String title, String value) {
        News n = new News();
        em.persist(n);
        n.setTitle(title);
        n.setValue(value);
        return n;
    }
    
     public List<News> findAll() {
       System.out.println("Fetching News..");
      TypedQuery<News> q = em.createQuery("select n from News n",
              News.class);
      List<News> newsList = q.getResultList();
      return newsList;
   }
    
    //Save news item
    public Page save(News newsItem){
        
        em.persist(newsItem);
        return Page.newslist;
    }

}
