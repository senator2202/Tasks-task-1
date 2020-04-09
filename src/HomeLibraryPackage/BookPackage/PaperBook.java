package HomeLibraryPackage.BookPackage;

import java.io.Serializable;

/**Класс бумажная книга, наследует класс абстрактная книга,
 * дополняет его уникальными методами и свойствами*/
public class PaperBook extends AbstractBook implements Serializable {

    /**Тип бумаги*/
    private PaperType paperType;

    /**Издательство*/
    private String publishingHouse;

    public PaperType getPaperType() {
        return paperType;
    }

    public void setPaperType(PaperType paperType) {
        this.paperType = paperType;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }


    public enum PaperType implements Serializable {
        Glossy,//глянцевая
        Matte//матовая
    }


    @Override
    public String toString() {
        return "title: "+title+"; author: "+author+"; genre: "+genre+"; number of pages: "+numberOfPages+
                "; paper type: "+paperType+"; publishing house: "+publishingHouse;
    }

    /**Класс-билдер для PaperBook*/
    public static class PaperBookBuilder implements Serializable {
        private final PaperBook paperBook;

        public PaperBookBuilder() {
            paperBook=new PaperBook();
        }

        public PaperBookBuilder withPublishingHouse(String publishingHouse) {
            paperBook.publishingHouse=publishingHouse;
            return this;
        }

        public PaperBookBuilder withPaperType(PaperType paperType) {
            paperBook.paperType=paperType;
            return this;
        }

        public PaperBookBuilder withTitle(String title) {
            paperBook.title=title;
            return this;
        }

        public PaperBookBuilder withAuthor(String author) {
            paperBook.author=author;
            return this;
        }

        public PaperBookBuilder withGenre(Genre genre) {
            paperBook.genre=genre;
            return this;
        }

        public PaperBookBuilder withNumberOfPages(int numberOfPages) {
            paperBook.numberOfPages=numberOfPages;
            return this;
        }

        public PaperBook build() {
            return paperBook;
        }
    }
}
