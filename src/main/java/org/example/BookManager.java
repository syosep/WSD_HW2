package org.example;

import java.util.Scanner;
import java.util.List;

public class BookManager {
    private BookCRUD crud;
    private Scanner in;

    public BookManager() {
        this.crud = new BookCRUD();
        this.in = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("1. 도서 목록 보기");
            System.out.println("2. 도서 목록 정렬");
            System.out.println("3. 도서 검색");
            System.out.println("4. 도서 추가");
            System.out.println("5. 도서 수정");
            System.out.println("6. 도서 삭제");
            System.out.println("7. 도서 DB 저장");
            System.out.println("0. 종료");
            System.out.print("메뉴를 선택하세요: ");

            int choice = in.nextInt();
            in.nextLine();

            switch (choice) {
                case 1:
                    listBook();
                    break;
                case 4:
                    addBook();
                    break;
                case 5:
                    updateBook();
                    break;
                case 0:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        }
    }

    private void addBook() {
        in.nextLine();

        System.out.print("도서 제목 : ");
        String title = in.nextLine();
        System.out.print("저자 : ");
        String author = in.nextLine();
        System.out.print("출판사 : ");
        String publisher = in.nextLine();
        System.out.print("출판일 : ");
        String publicationDate = in.nextLine();
        System.out.print("DB 추가일 : ");
        String createDate = in.nextLine();

        Book book = new Book (0, title, author, publisher, publicationDate, createDate);
        crud.addBook(book);
    }

    private void listBook() {
        List<Book> books = crud.getAllBooks();
        System.out.println("[도서 목록]");
        for (Book book : books) {
            System.out.println("ID : " + book.getId() +
                    ", 제목 : " + book.getTitle() +
                    ", 저자 : " + book.getAuthor() +
                    ", 출판사 : " + book.getPublisher() +
                    ", 출판일 : " + book.getPublicationDate() +
                    ", 추가일 : " + book.getCreateDate());
        }
    }

    private void updateBook() {
        System.out.print("수정할 도서의 ID를 입력하세요 : ");
        int id = in.nextInt();
        in.nextLine();

        System.out.print("새로운 제목 : ");
        String newTitle = in.nextLine();
        System.out.print("새로운 저자 : ");
        String newAuthor = in.nextLine();
        System.out.print("새로운 출판사 : ");
        String newPublisher = in.nextLine();
        System.out.print("새로운 출판일 : ");
        String newPublicationDate = in.nextLine();

        crud.updateBook(id, newTitle, newAuthor, newPublisher, newPublicationDate);
    }
}
