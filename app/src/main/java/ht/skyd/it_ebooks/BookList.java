package ht.skyd.it_ebooks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
public class BookList {

    @SerializedName("Error")
    @Expose
    private String Error;
    @SerializedName("Time")
    @Expose
    private Double Time;
    @SerializedName("Total")
    @Expose
    private String Total;
    @SerializedName("Page")
    @Expose
    private Integer Page;
    @SerializedName("Books")
    @Expose
    private List<Book> Books = new ArrayList<Book>();

    /**
     *
     * @return
     * The Error
     */
    public String getError() {
        return Error;
    }

    /**
     *
     * @param Error
     * The Error
     */
    public void setError(String Error) {
        this.Error = Error;
    }

    /**
     *
     * @return
     * The Time
     */
    public Double getTime() {
        return Time;
    }

    /**
     *
     * @param Time
     * The Time
     */
    public void setTime(Double Time) {
        this.Time = Time;
    }

    /**
     *
     * @return
     * The Total
     */
    public String getTotal() {
        return Total;
    }

    /**
     *
     * @param Total
     * The Total
     */
    public void setTotal(String Total) {
        this.Total = Total;
    }

    /**
     *
     * @return
     * The Page
     */
    public Integer getPage() {
        return Page;
    }

    /**
     *
     * @param Page
     * The Page
     */
    public void setPage(Integer Page) {
        this.Page = Page;
    }

    /**
     *
     * @return
     * The Books
     */
    public List<Book> getBooks() {
        return Books;
    }

    /**
     *
     * @param Books
     * The Books
     */
    public void setBooks(List<Book> Books) {
        this.Books = Books;
    }

}
