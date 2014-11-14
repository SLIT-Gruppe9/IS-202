/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.uia.slit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author even
 * mods: eirik
 */
@Entity
@Table(name="module")
public class Module {
   @Id @GeneratedValue
   private long id;
   @Column(unique=true)
   private String name;
   @Lob                             // Store description as Clob ( to save strings larger than 255 )
   private String description;
   @ManyToOne
   private Module requiredModule;
   
    @OneToOne
   private DownloadableFile file;   // Module FIle attachment
   
   public Module() {
   }

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
      //System.out.print("Saved name: "+name);
   }

   public String getDescription() {
      return description;
   }

   public String getShortDescription(){
       //fiksa bug; hvis description er under 50 chars feiler subSequence.
       if( description.length() >= 50){
       return (String)description.subSequence(0, 50)+"...";
       }else {
       return description;
       }
   }
   
   public void setDescription(String description) {
      this.description = description;
   }

   public Module getRequiredModule() {
      return requiredModule;
   }

   public void setRequiredModule(Module requiredModule) {
      this.requiredModule = requiredModule;
   }

   public DownloadableFile getFile() {
        return file;
    }

    public void setFile(DownloadableFile file) {
        this.file = file;
    }
   
   
   @Override
   public int hashCode() {
      int hash = 7;
      hash = 73 * hash + (int) (this.id ^ (this.id >>> 32));
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
      final Module other = (Module) obj;
      if (this.id != other.id) {
         return false;
      }
      return true;
   }
}
