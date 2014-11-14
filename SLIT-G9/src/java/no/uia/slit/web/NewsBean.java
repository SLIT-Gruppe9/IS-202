package no.uia.slit.web;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import no.uia.slit.ejb.NewsPersister;
import no.uia.slit.entity.News;


/**
 *
 * @author eirik
 */
@Named("newsBean")
//@ConversationScoped
@SessionScoped
public class NewsBean implements Serializable {
    @EJB private NewsPersister newspst;
    private News selectedNews;

    
    public NewsBean() {
    }


    public List<News> getAllNews() {
        return newspst.findAll();
    }

    public Page createNews() {
        System.out.println("CreatNews item..");
        selectedNews = new News();
        return Page.editnews;
    }

    public Page editNews(long id) {
        selectedNews = newspst.find(id);
        if (null == selectedNews) {
            selectedNews = new News();
        }
        return Page.editnews;
    }
    
 

    public News getSelectedNews() {
        return selectedNews;
    }
    
    
    // Save current news object
    public Page saveSelectedNews(){  
        if(selectedNews != null){
        newspst.save(selectedNews);
        }
       return Page.newslist;
    
    }
    
    //Delete selected
    public Page deleteSelectedNews(){
        newspst.delete(selectedNews);
        
    return Page.newslist;
    }
    
}
