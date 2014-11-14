package no.uia.slit.web;


/**
 * Using an enum rather than strings to refer to pages (views in JSF lingo)
 * eliminates typos in page references.
 *
 * @author even
 */
public enum Page {
    home("/index"),
    teacher("/teacher/index"),
    modules("/teacher/modules"),
    module("/teacher/module"),
    viewmodule("/student/module"),
    viewassessment("/student/viewassessment"),
    admin("/admin/index"),
    assessment("/teacher/assessment"),
    studentmodules("/student/modules"),
    studentassessment("/student/assessment"),
    editnews("/teacher/editnews"),
    newslist("/teacher/newslist"),
    error("/error"),
    user,
    users
    ;

    String url;

    Page(String url) {
        this.url = url;
    }

    Page() {
        this.url = this.name();
    }

    public String toString() {
        return url;
    }

}
