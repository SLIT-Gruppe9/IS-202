/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.uia.slit.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import no.uia.slit.web.JsfUtils;

/**
 *
 * @author even
 */
@Entity
@Table(name="downloadablefile")
public class DownloadableFile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private long id;
    private String filename;
    private String contentType;
    private int filesize;
    
    private long module_id;  //foreign key in module ( id)
    

    private String username;
    private Timestamp uploadTime;

    /**
     * The actual content of the file. It is stored as a BLOB in the database.
     * FetchType.LAZY means that JPA will not retrieve it from the database
     * before we explicitly as for it. This is to avoid unnecessary load on the
     * database and application server.
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] file;

    public DownloadableFile() {
        uploadTime = new Timestamp(System.currentTimeMillis());
       // username = JsfUtils.getUserName();  //glassfish exception
       /*Exception [EclipseLink-168] (Eclipse Persistence Services - 2.5.0.v20130507-3faac2b): org.eclipse.persistence.exceptions.DescriptorException
Exception Description: Problem in creating new instance using the default constructor.  The default constructor triggered an exception.
Internal Exception: java.lang.reflect.InvocationTargetException
Target Invocation Exception: java.lang.NullPointerException
Descriptor: RelationalDescriptor(no.uia.slit.entity.DownloadableFile --> [DatabaseTable(downloadablefile)])
        
        */
        
    }

    public DownloadableFile(String filename, String contentType,
            int filesize, byte[] file, long module_id) {
        this();

        this.filename = filename;
        this.contentType = contentType;
        this.filesize = filesize;
        this.file = file;
        this.module_id = module_id;
    }

    public String getUsername() {
        return username;
    }

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getModuleId(){
        return module_id;
    }
     public void setModuleId(long id){
        this.module_id = id;
        
    }
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getFilesize() {
        return filesize;
    }

    public void setFilesize(int filesize) {
        this.filesize = filesize;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DownloadableFile)) {
            return false;
        }
        DownloadableFile other = (DownloadableFile) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FileObject[ id=" + id + " ]";
    }
}
