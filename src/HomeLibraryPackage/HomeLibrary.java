package HomeLibraryPackage;

import HomeLibraryPackage.AuthentificationPackage.Authentification;
import HomeLibraryPackage.AuthentificationPackage.User;
import HomeLibraryPackage.BookPackage.AbstractBook;
import HomeLibraryPackage.BookPackage.EBook;
import HomeLibraryPackage.BookPackage.PaperBook;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**Класс загружает в себя и хранит список книг в библиотеке.
 * Через встроенное меню, предоставляет набор функций для авторизации,
 * а также работы с библиотекой*/
public class HomeLibrary {

    /**Библиотека в памяти представляет собой список ArrayList с объектами-наследниками класса AbstractBook */
    ArrayList<AbstractBook> books;

    /**Объект юзер, хранящийся для управления правами доступа к библиотеке*/
    User user;

    public static void main(String []args) {
        HomeLibrary hm=new HomeLibrary();
        hm.start();
    }

    /**Функция сначала производит авторизацию, потом работу с базой данных книг в библиотеке*/
    public void start() {
        AuthMenu();
        readFromFile();
        mainMenu();
    }

    /**Основное меню: просмотр, поиск, добавление (если админ), удаление (если админ).
     * После добавления админов описания книги в каталог, уведомление приходит всем пользователям.
     * Обычный юзер может заполнить описание книги и выслать админу на емэйл с предложением к публикации.*/
    private void mainMenu() {
        Scanner sc=new Scanner(System.in);
        String menu=user.isAdmin()?"1-view books; 2-search book; 3-add book; 4-delete book 0-exit"
                :"1-view books; 2-search book; 3-suggest admin to add a book; 0-exit";
        System.out.println(menu);
        int choice=sc.nextInt();
        while (choice!=0) {// view books
            if (choice ==1) {
                System.out.println(getBooks());
            }

            if (choice==2) {//search book
                searchBlock();
            }

            if (choice==4 && user.isAdmin()) {//delete book
                deleteBlock();
            }

            if (choice==3 ) {//add book
                addBlock();
            }

            System.out.println(menu);
            choice=sc.nextInt();
        }

        if (choice==0)
            System.exit(0);
    }

    /**Код, выполняемый при выборе функции добавления книги.
     * Вынесен в отдельный метод для повышения читабельности кода
     * в методе основного меню mainMenu()*/
    private void addBlock() {
        Scanner sc=new Scanner(System.in);
        System.out.println("1-ebook; 2-paper book");
        int choice1=sc.nextInt();
        AbstractBook ab=choice1==1?new EBook():new PaperBook();

        System.out.println("Enter book's title:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            ab.setTitle(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Enter book's Author:");
        try {
            ab.setAuthor(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Genre: 1-poetry; 2-fiction; 3-nonfiction; 4- drama");
        choice1=sc.nextInt();
        if (choice1==1)
            ab.setGenre(AbstractBook.Genre.Poetry);
        else
        if (choice1==2)
            ab.setGenre(AbstractBook.Genre.Fiction);
        else
        if (choice1==3)
            ab.setGenre(AbstractBook.Genre.Nonfiction);
        else
            ab.setGenre(AbstractBook.Genre.Drama);

        System.out.println("Enter number of pages:");
        ab.setNumberOfPages(sc.nextInt());
        if(ab instanceof EBook) {
            System.out.println("Enter format: ");
            ((EBook) ab).setFormat(sc.next());
            System.out.println("Enter file size: ");
            ((EBook) ab).setSize(sc.nextInt());
        }
        if (ab instanceof PaperBook){
            System.out.println("Paper type: 1-glossy; 2-matte");
            choice1=sc.nextInt();
            if (choice1==1)
                ((PaperBook) ab).setPaperType(PaperBook.PaperType.Glossy);
            else
                ((PaperBook) ab).setPaperType(PaperBook.PaperType.Matte);
            System.out.println("Enter publishing house: ");
            try {
                ((PaperBook) ab).setPublishingHouse(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (user.isAdmin())
            addBook(ab);
        else
            EmailSender.send(Authentification.getAdminEmail(),"Suggestion to add a book",
                    "Could you add this book: title: "+ab);
    }

    /**Код, выполняемый при выборе функции удаления книги.
     * Вынесен в отдельный метод для повышения читабельности кода
     * в методе основного меню mainMenu()*/
    private void deleteBlock() {
        System.out.println("Enter book's title:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String title = null;
        try {
            title = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        deleteBook(title);
    }

    /**Код, выполняемый при выборе функции поиска книги.
     * Вынесен в отдельный метод для повышения читабельности кода
     * в методе основного меню mainMenu()*/
    private void searchBlock() {
        System.out.println("Enter book's title for searching: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String title = null;
        try {
            title = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(searchBook(title));
    }

    /**Меню авторизации: логин, регистрация*/
    private void AuthMenu() {
        Scanner sc=new Scanner(System.in);
        System.out.println("1-login; 2-register; 0-exit");
        int choice=sc.nextInt();
        while (choice!=0) {//login
            System.out.println("Enter your name: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String name = null;
            try {
                name = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Enter your password: ");
            String password=sc.next();

            if (choice==2) {//register
                System.out.println("Enter your email: ");
                String email=null;
                try {
                     email= reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Authentification.registerUser(name,password,email)) {
                    user=new User(name,password,email);
                    Authentification.isAdmin(user);
                    return;
                }
            }

            if (choice==1) {
                user= Authentification.getUser(name,password);
                if (user==null) {
                    System.out.println("Incorrect name or password!");
                }else {
                    return;
                }
            }

            System.out.println("1-login; 2-register; 0-exit");
            choice=sc.nextInt();
        }

        if (choice==0)
            System.exit(0);
    }

    /**Чтение каталога книг из файла*/
    private void readFromFile() {
        try {
            FileInputStream fis = new FileInputStream("HomeLibrary.txt");
            ObjectInputStream oin = new ObjectInputStream(fis);
            books=(ArrayList<AbstractBook>) oin.readObject();
        } catch (Exception e) {

        }
        finally {
            if (books==null) {
                books= new ArrayList<>();
            }
        }
    }

    /**Запись каталога книг в файл*/
    private void writeToFile() {
        try {
            FileOutputStream fos = new FileOutputStream("HomeLibrary.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(books);
            oos.flush();
            oos.close();
        } catch (Exception e) {
        }
    }

    /**Возвращает текстовую строку с краткой инфой про все книги в каталоге*/
    private String getBooks() {
        String view=new String("Books available:\n");
        for(AbstractBook ab: books) {
            String temp=ab instanceof EBook ? "ebook" : "paper book";
            view = view+    "type of book: "+ temp+"; "+
                            "title: "+ab.getTitle()+"; author: "+ab.getAuthor()+"\n";
        }
        return view;
    }

    /**Поиск книги по названию в библиотеке*/
    private String searchBook(String title) {
        for (AbstractBook ab: books) {
            if (ab.getTitle().equals(title)) {
                String temp = (ab instanceof EBook) ? "ebook" : "paper book";
               return "type of book: "+ temp+"; "+ ab.toString();
            }
        }
        return null;
    }

    /**Добавление книги из списка, вызов функции рассылки уведомлений, запись обновленного каталога в файл*/
    private void addBook(AbstractBook book) {
        if (!user.isAdmin()) {
            System.out.println("Не достаточно прав!");
            return;
        }
        books.add(book);
        emailAllUsers(book);
        writeToFile();
    }

    /**Удаление книги из списка, запись обновленного каталога в файл*/
    private void deleteBook(String title) {
        if (!user.isAdmin()) {
            System.out.println("Не достаточно прав!");
            return;
        }
        for (int i=0; i<books.size(); i++) {
            AbstractBook ab=books.get(i);
            if (ab.getTitle().equals(title)) {
                books.remove(ab);
                i--;
            }
        }
        books.trimToSize();
        writeToFile();
    }

    /**Рассылка емэйл-уведомлений всем пользовтелям (после добавления новой книги в каталог)*/
    private void emailAllUsers(AbstractBook book) {
        for (String address: Authentification.getAllEmails()) {
            EmailSender.send(address,"New book","Hey, we got new book for you: title: "+book.getTitle()+"; author: "+book.getAuthor());
        }
    }

}
