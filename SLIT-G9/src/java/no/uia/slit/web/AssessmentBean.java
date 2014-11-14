package no.uia.slit.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import no.uia.slit.ejb.AssessmentPersister;
import no.uia.slit.ejb.StudentPersister;
import no.uia.slit.entity.Assessment;
import no.uia.slit.entity.DownloadableFile;
import no.uia.slit.entity.Module;
import no.uia.slit.entity.Student;
import org.apache.commons.io.FilenameUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;



/**
 *
 * @author Criyonit K
 */
@Named("assessmentBean")
//@ConversationScoped
//@ViewScoped
@SessionScoped
public class AssessmentBean implements Serializable {
    @EJB private AssessmentPersister apc;
    @EJB private StudentPersister spc;
    private Assessment selectedAssessment;
    private UploadedFile upload;
   // private Module module;
    
    public AssessmentBean() {
    }


    public List<Assessment> getAllAssessments() {
        return apc.findAll();
    }

   // public Page createAssessment() {
    //    selectedAssessment = new Assessment();
    //    return Page.assessment;
   // }
    
    //Accepts module as parameter, and sets the Assessment module to this param.
    // Used when students are delivering assessments that are attached to a specific module.
    //Allso retrieves username of current user as param
    public Page createAssessment(Module m, String usr){
        
        //Fetch user
        Student s = spc.findByName(usr);
        
        selectedAssessment = new Assessment(m, s);
       //selectedAssessment.setModule(m);
        //module = m;
        //selectedAssessment.set
        System.out.println("User: "+usr+" Created assessment for module: "+m.getName());
        return Page.studentassessment;
    }
    
    public Page viewAssessment(long id){
        
    selectedAssessment = apc.find(id);
    if(selectedAssessment != null){
        return Page.viewassessment;
    }else {
        return Page.error; //todo: fullføre error siden, passe error meld som arg
    }
    }

    /** Prepare module form for editing an existing module.
     * If the requested module does not exist, a new module
     * will be created instead. */
    public Page editAssessment(long id) {
        selectedAssessment = apc.find(id);
        if (selectedAssessment == null) {
            //selectedAssessment = new Assessment();
            System.out.println( "ERROR: assessment not found( id: "+ id +")" );
        }
        return Page.assessment;
    }

    public Assessment getSelectedAssessment() {
        return selectedAssessment;
    }
    
        public UploadedFile getUpload() {
        return upload;
    }

    public void setUpload(UploadedFile upload) {
        this.upload = upload;
    }
    
    
    // Save current Ass
    public Page saveSelectedAssessment() throws IOException{
        
       
        // If user uploaded a file with the module
        // Get file info and create a DownloadableFIle object, and save it along with the module.
        if(upload != null){
        
         String fileName = FilenameUtils.getName(upload.getName());
        String contentType = upload.getContentType();
        byte[] bytes = upload.getBytes();
         //Create a downloadablefile object from uploaded file
        DownloadableFile newFile = new DownloadableFile((fileName), contentType, bytes.length, bytes, selectedAssessment.getId());
        
        selectedAssessment.setFile(newFile);
        apc.save(selectedAssessment, newFile);
        }else {
         //No files attached, Save Assessment withour resource
        apc.save(selectedAssessment, null);
        }
        System.out.println("Assessment saved: ");
       return Page.studentmodules;
    
    }
}
