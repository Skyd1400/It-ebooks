package ht.skyd.it_ebooks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Book {

    @SerializedName("ID")
    @Expose
    private Long ID;
    @SerializedName("Title")
    @Expose
    private String Title;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("Image")
    @Expose
    private String Image;
    @SerializedName("isbn")
    @Expose
    private String isbn;
    @SerializedName("SubTitle")
    @Expose
    private String SubTitle;

    /**
     *
     * @return
     * The ID
     */
    public Long getID() {
        return ID;
    }

    /**
     *
     * @param ID
     * The ID
     */
    public void setID(Long ID) {
        this.ID = ID;
    }

    /**
     *
     * @return
     * The Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     *
     * @param Title
     * The Title
     */
    public void setTitle(String Title) {
        this.Title = Title;
    }

    /**
     *
     * @return
     * The Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     *
     * @param Description
     * The Description
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     *
     * @return
     * The Image
     */
    public String getImage() {
        return Image;
    }

    /**
     *
     * @param Image
     * The Image
     */
    public void setImage(String Image) {
        this.Image = Image;
    }

    /**
     *
     * @return
     * The isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     *
     * @param isbn
     * The isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     *
     * @return
     * The SubTitle
     */
    public String getSubTitle() {
        return SubTitle;
    }

    /**
     *
     * @param SubTitle
     * The SubTitle
     */
    public void setSubTitle(String SubTitle) {
        this.SubTitle = SubTitle;
    }

}