package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
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
            System.out.println("\n====================");
            System.out.println(" 도서 관리 프로그램 ");
            System.out.println("====================");
            System.out.println("1. 도서 목록 보기");
            System.out.println("2. 도서 목록 정렬");
            System.out.println("3. 도서 검색");
            System.out.println("4. 도서 추가");
            System.out.println("5. 도서 수정");
            System.out.println("6. 도서 삭제");
            System.out.println("7. 도서 DB 저장");
            System.out.println("8. 도서 통계 보기");
            System.out.println("9. 도서 수정 이력 보기");
            System.out.println("0. 종료");
            System.out.print("\n메뉴를 선택하세요 : ");

            int choice = in.nextInt();
            in.nextLine();

            switch (choice) {
                case 1:
                    listBook();
                    break;
                case 2:
                    sortBook();
                    break;
                case 3:
                    searchBook();
                    break;
                case 4:
                    addBook();
                    break;
                case 5:
                    updateBook();
                    break;
                case 6:
                    deleteBook();
                    break;
                case 7:
                    crud.saveFile();
                    break;
                case 8:
                    statisticsBook();
                    break;
                case 9:
                    showUpdateHistory();
                    break;
                case 0:
                    System.out.println("\n프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("\n잘못된 선택입니다. 다시 입력해주세요.");
            }
        }
    }

    private void addBook() {
        System.out.println("\n== 도서 추가 ==");
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
        System.out.println("도서가 성공적으로 추가되었습니다.");
    }

    private void listBook() {
        List<Book> books = crud.getAllBooks();
        System.out.println("\n== 도서 목록 ==");
        for (Book book : books) {
            System.out.println("────────────────────");
            System.out.println("ID : " + book.getId());
            System.out.println("제목 : " + book.getTitle());
            System.out.println("저자 : " + book.getAuthor());
            System.out.println("출판사 : " + book.getPublisher());
            System.out.println("출판일 : " + book.getPublicationDate());
            System.out.println("추가일 : " + book.getCreateDate());
        }
        System.out.println("────────────────────");
        System.out.println("총 " + books.size() + "권의 도서가 있습니다.");
    }

    private void updateBook() {
        System.out.println("\n== 도서 정보 수정 ==");
        System.out.print("수정할 도서의 ID를 입력하세요 : ");
        int id = in.nextInt();
        in.nextLine();

        System.out.print("새로운 제목 : ");
        String newTitle = in.nextLine();
        System.out.print("새로운 저자 : ");
        String newAuthor = in.nextLine();
        System.out.print("새로운 출판사 : ");
        String newPublisher = in.nextLine();
        System.out.print("새로운 출판일 (예: 2024년 11월 02일) : ");
        String newPublicationDate = in.nextLine();

        crud.updateBook(id, newTitle, newAuthor, newPublisher, newPublicationDate);
        System.out.println("도서 정보가 성공적으로 수정되었습니다.");
    }

    private void deleteBook() {
        System.out.println("\n== 도서 삭제 ==");
        System.out.print("삭제할 도서의 ID를 입력하세요 : ");
        int id = in.nextInt();
        in.nextLine();

        crud.deleteBook(id);
        System.out.println("도서가 성공적으로 삭제되었습니다.");
    }

    private void searchBook() {
        System.out.println("\n== 도서 검색 ==");
        System.out.print("검색할 키워드를 입력하세요 : ");
        String keyword = in.nextLine();

        List<Book> books = crud.searchBook(keyword);
        if (books.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
        } else {
            System.out.println("<검색 결과>");
            for (Book book : books) {
                System.out.println("────────────────────");
                System.out.println("ID : " + book.getId());
                System.out.println("제목 : " + book.getTitle());
                System.out.println("저자 : " + book.getAuthor());
                System.out.println("출판사 : " + book.getPublisher());
                System.out.println("출판일 : " + book.getPublicationDate());
                System.out.println("추가일 : " + book.getCreateDate());
            }
            System.out.println("────────────────────");
            System.out.println("총 " + books.size() + "권의 도서가 검색되었습니다.");
        }
    }

    private void sortBook() {
        System.out.println("\n== 도서 정렬 ==");
        System.out.print("정렬 기준을 선택하세요. (author / publisher) : ");
        String sortType = in.nextLine();

        List<Book> books = crud.sortBook(sortType);

        if (books.isEmpty()) {
            System.out.println("정렬할 도서가 없습니다.");
        } else {
            System.out.println("<정렬된 도서 목록>");
            for (Book book : books) {
                System.out.println("────────────────────");
                System.out.println("ID : " + book.getId());
                System.out.println("제목 : " + book.getTitle());
                System.out.println("저자 : " + book.getAuthor());
                System.out.println("출판사 : " + book.getPublisher());
                System.out.println("출판일 : " + book.getPublicationDate());
                System.out.println("추가일 : " + book.getCreateDate());
            }
            System.out.println("────────────────────");
            System.out.println("총 " + books.size() + "권의 도서가 정렬되었습니다.");
        }
    }

    private void statisticsBook() {
        System.out.println("\n== 도서 통계 ==");

        int totalBooks = crud.totalBook();
        System.out.println("전체 도서 수 : " + totalBooks);

        Map<String, Integer> authorCount = crud.bookByAuthor();
        System.out.println("\n* 저자별 도서 수");
        for (Map.Entry<String, Integer> entry : authorCount.entrySet()) {
            System.out.println("저자 : " + entry.getKey() + " - 도서 수 : " + entry.getValue());
        }

        Map<String, Integer> publisherCount = crud.bookByPublisher();
        System.out.println("\n* 출판사별 도서 수");
        for (Map.Entry<String, Integer> entry : publisherCount.entrySet()) {
            System.out.println("출판사 : " + entry.getKey() + " - 도서 수 : " + entry.getValue());
        }
    }

    private void showUpdateHistory() {
        System.out.print("수정 이력을 조회할 도서의 ID를 입력하세요: ");
        int bookId = in.nextInt();
        in.nextLine(); // 개행 문자 소비

        viewUpdateHistory(bookId);
    }

    private void viewUpdateHistory(int bookId) {
        String fileName = "book_" + bookId + "_history.txt";

        System.out.println("\n== 도서 수정 이력 보기 ==");
        System.out.println("도서 ID " + bookId + "의 수정 이력 :");

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("이력 파일 조회 오류 : " + e.getMessage());
        }
    }
}