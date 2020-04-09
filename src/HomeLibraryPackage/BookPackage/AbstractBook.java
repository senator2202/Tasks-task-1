package HomeLibraryPackage.BookPackage;

import java.io.Serializable;

/**Класс абстрактной книги, хранит общее описание для бумажной и электронной книг*/
public abstract class AbstractBook implements Serializable {
    String title;
    String author;
    Genre genre;
    int numberOfPages;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public abstract String toString();

    /**Перечисление, хранит типы жанров книг*/
    public enum Genre implements Serializable {
        Poetry,
        Fiction,
        Nonfiction,
        Drama
    }
}
