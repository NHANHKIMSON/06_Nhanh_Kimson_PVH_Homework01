public class Book {
    private static int countId = 0;
    private int id;
    private String title;
    private Author author;
    private int publishedYear;
    private String status; // Available or Borrowed

    // Constructor
    public Book(String title, Author author, int publishedYear) {
        ++countId;
        this.id = countId;
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.status = "Available"; // Default status
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return  id + "\t" + title + "\t" + author + "\t" + publishedYear + "\t" + status;
    }

}