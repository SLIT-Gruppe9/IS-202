package no.uia.slit.ejb;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import no.uia.slit.entity.DownloadableFile;
import no.uia.slit.entity.Module;


/**
 *
 * @author even
 */
@Stateless // La til Stateless - eirik
public class ModulePersistenceService {

   @PersistenceContext
   private EntityManager em;

      @EJB
    DownloadableFilePersister fileEJB;
   
   
   /** Retrieve the module with the specified id from the database */
   public Module find(long id) {
       Module m = em.find(Module.class, id);
       return m;
   }

   /** Return a list of all modules defined in the database */
   public List<Module> findAll() {
       System.out.println("Fetching modules..");
      TypedQuery<Module> q = em.createQuery("select m from Module m",
              Module.class);
      List<Module> modList = q.getResultList();
      return modList;
   }

   /** Save a module in the database */
   /*
   *    Måtte slette tabellen module, og la tomcat re-create den for at det skulle fungere.
        Kan være fordi jeg hadde manuelt lagt inn modul i mysql, og da funker ikke autogenerate id?.
   */
   public void save(Module m, DownloadableFile file) {
       System.out.println("Saving Module: "+m.getName());
       
        /*  If a file resource was included with the module, store the Downloadablefile object.
       *
       */
              
       if(file != null){
           System.out.println("With resource: "+file.getFilename());
        file = fileEJB.insert(file);    //stores file in db
       // System.out.println("Insert file");
        m.setFile(file);                //sets reference to file in module.
        
       }
       em.merge(m);                     //Merge to db
       
   }

   /** Remove a module from the database */
   public void remove(Module m) {
       em.remove(em.merge(m)); //fix for at sletting skal fungere - eirik
   }
}
