package no.uia.slit.ejb;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import no.uia.slit.entity.DownloadableFile;
import no.uia.slit.entity.Assessment;


/**
 *
 * @author eirik
 */
@Stateless
public class AssessmentPersister {

   @PersistenceContext
   private EntityManager em;

      @EJB
    DownloadableFilePersister fileEJB;
   
   
   /** Retrieve the module with the specified id from the database */
   public Assessment find(long id) {
       Assessment a = em.find(Assessment.class, id);
       return a;
   }

   /** Return a list of all modules defined in the database */
   public List<Assessment> findAll() {
       System.out.println("Fetching Assessments..");
      TypedQuery<Assessment> q = em.createQuery("select a from Assessment a",
              Assessment.class);
      List<Assessment> AssessmentList = q.getResultList();
      return AssessmentList;
   }


   public void save(Assessment a, DownloadableFile file) {
       System.out.println("Saving Assessment from student: "+a.getStudent() + " module: "+a.getModule().getName());
       
        /*  If a file resource was included with the Assessment, store the Downloadablefile object.
       *
       */
              
       if(file != null){
           System.out.println("With resource: "+file.getFilename());
        file = fileEJB.insert(file);    //stores file in db
        a.setFile(file);                //sets reference to file in module.
       }
       em.merge(a);                     //Merge to db
   }

   /** Remove Assessment from the database */
   public void remove(Assessment a) {
       em.remove(a);
   }
}
