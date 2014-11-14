package no.uia.slit.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import no.uia.slit.ejb.ModulePersistenceService;
import no.uia.slit.entity.DownloadableFile;
import no.uia.slit.entity.Module;
import org.apache.commons.io.FilenameUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;


/**
 *
 * @author even
 * mods: eirik
 */
@Named("moduleBean")
//@ConversationScoped
@SessionScoped
public class ModuleBean implements Serializable {
    @EJB private ModulePersistenceService moduleSvc;
    private Module selectedModule;
    private UploadedFile upload;
    
    public ModuleBean() {
    }


    public List<Module> getAllModules() {
        return moduleSvc.findAll();
    }

    /** Set up module form for adding a new module */
    public Page createModule() {
        System.out.println("Createmodule..");
        selectedModule = new Module();
        return Page.module;
    }

    /** Prepare module form for editing an existing module.
     * If the requested module does not exist, a new module
     * will be created instead. */
    //brukes i teacher/modules for å redireect til teacher/module hvor man kan redigere moduler
    public Page editModule(long id) {
        selectedModule = moduleSvc.find(id);
        if (null == selectedModule) {
            selectedModule = new Module();
        }
        return Page.module;
    }
    
    // Brukes i student/modules for å redirecte til student/module som er en read-only modul side
    public Page viewModule(long id){
        selectedModule = moduleSvc.find(id);
        
        if(selectedModule == null){
            //Error handling, redirect til error.xhtml med feilmeld "Modul x finnes ikke";
        return Page.error;
        }
        return Page.viewmodule; //student/module    (read-only module details )
    }

    public Module getSelectedModule() {
        return selectedModule;
    }
    
        public UploadedFile getUpload() {
        return upload;
    }

    public void setUpload(UploadedFile upload) {
        this.upload = upload;
    }
    
    
    // Save current module
    public Page saveSelectedModule() throws IOException{
        
        if(selectedModule == null){
            System.out.println("ERROR: SELECTED MODULE ER NULL");
        
        }
       
        // If user uploaded a file with the module
        // Get file info and create a DownloadableFIle object, and save it along with the module.
        if(upload != null){
        
         String fileName = FilenameUtils.getName(upload.getName());
        String contentType = upload.getContentType();
        byte[] bytes = upload.getBytes();
         //Create a downloadablefile object from uploaded file
        DownloadableFile newFile = new DownloadableFile((fileName), contentType, bytes.length, bytes, selectedModule.getId());
        
        selectedModule.setFile(newFile);
        moduleSvc.save(selectedModule, newFile);
        }else {
         //No files attached, save module without resource.
        moduleSvc.save(selectedModule, null);
        }
        
     //  System.out.print("Saved module:" +selectedModule.getName());
       return Page.modules;
    
    }
    
    //Delete selected module
    public Page deleteSelectedModule(){
        if(selectedModule != null){
            moduleSvc.remove(selectedModule);
        }
        return Page.modules;
    }
}
