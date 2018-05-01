package src.models;

/*
* This class defines the model of the Page
* */
public class PageModel {

    private int pageId;
    private int pageValue;


//    Constructor for the PageModel
    public PageModel(int pageId, int pageValue) {
        this.pageId = pageId;
        this.pageValue = pageValue;
    }

//    Getter for PageId
    public int getPageId() {
        return pageId;
    }

//    Setter for PageId
    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

//    Getter for PageValue
    public int getPageValue() {
        return pageValue;
    }

//    Setter for PageValue
    public void setPageValue(int pageValue) {
        this.pageValue = pageValue;
    }

}
