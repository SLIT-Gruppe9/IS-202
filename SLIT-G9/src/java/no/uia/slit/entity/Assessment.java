/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.uia.slit.entity;

import java.util.Date;
import javax.ejb.EJB;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
/**
 * This class represents the results of an assessment of a student's
 * results for a particular module.
 *
 * @author even
 */
@Entity
@Table(name="assessment")
public class Assessment {
   @Id @GeneratedValue
   private long id;
   @ManyToOne
   private Student student;
   @ManyToOne
   private Module module;
   private Date assessmentDate;
   private boolean achievedLearningGoals; // true means the student passed
   private String teachersComment;
   private String description;
  

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
   
   @OneToOne
   private DownloadableFile file;   // Assessment FIle attachment

    public DownloadableFile getFile() {
        return file;
    }

    public void setFile(DownloadableFile file) {
        this.file = file;
    }
   
   //public Assessment() {
   //}

    //current module and student's username as params.
   public Assessment(Module module, Student student){
       // Set module
       this.module = module;
       //System.out.println("user test: "+username);
       
       //Set current date
       Date date = new Date();
       this.assessmentDate =  date;
       System.out.println("New assessment created for module: "+module.getName());
       System.out.println("Date: "+date);
       
       //Get student ref
      //this.student = spc.findByName(username); // Nullpointer exception...
       this.student = student;
       //if(this.student == null){
       //    System.out.println("ERROR: no student object exists for usr: "+username);
       //}
       
   }
   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public Student getStudent() {
      return student;
   }

   public void setStudent(Student student) {
      this.student = student;
   }

   public Module getModule() {
      return module;
   }

   public void setModule(Module m) {
      this.module = m;
   }

   public Date getApprovedDate() {
      return assessmentDate;
   }

   public void setApprovedDate(Date approvedDate) {
      this.assessmentDate = approvedDate;
   }

   public boolean isApproved() {
      return achievedLearningGoals;
   }

   public void setApproved(boolean approved) {
      this.achievedLearningGoals = approved;
   }

   public String getTeachersComment() {
      return teachersComment;
   }

   public void setTeachersComment(String comment) {
      this.teachersComment = comment;
   }

   @Override
   public int hashCode() {
      int hash = 7;
      hash = 13 * hash + (int) (this.id ^ (this.id >>> 32));
      return hash;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final Assessment other = (Assessment) obj;
      if (this.id != other.id) {
         return false;
      }
      return true;
   }
}
