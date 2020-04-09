package HomeLibraryPackage.BookPackage;

import java.io.Serializable;

/**Класс электронная книга, наследует класс абстрактная книга,
 * дополняет его уникальными методами и свойствами*/
public class EBook extends AbstractBook implements Serializable {

    /**Электронный формат*/
    private String format;

    /**Размер файла электронной книги, в Мб*/
    private int size;


    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "title: "+title+"; author: "+author+"; genre: "+genre+"; number of pages: "+numberOfPages+
                "; format: "+format+"; book size: "+size+" Mb";
    }


    /**Класс-билдер для Ebook*/
    public static class EBookBuilder implements Serializable{
        private final EBook eBook;

        public EBookBuilder() {
            eBook=new EBook();
        }

        public EBookBuilder withFormat(String format) {
            eBook.format=format;
            return this;
        }

        public EBookBuilder withSize(int size) {
            eBook.size=size;
            return this;
        }

        public EBookBuilder withTitle(String title) {
            eBook.title=title;
            return this;
        }

        public EBookBuilder withAuthor(String author) {
            eBook.author=author;
            return this;
        }

        public EBookBuilder withGenre(Genre genre) {
            eBook.genre=genre;
            return this;
        }

        public EBookBuilder withNumberOfPages(int numberOfPages) {
            eBook.numberOfPages=numberOfPages;
            return this;
        }

        public EBook build() {
            return eBook;
        }
    }
}
