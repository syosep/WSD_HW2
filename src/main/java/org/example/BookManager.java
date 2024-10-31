package org.example;

import java.util.Scanner;

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
            switch (choice) {
                case 4:
                    addBook();
                case 0:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        }
    }

    private void addBook() {
        System.out.print("도서 제목 : ");
        String title = in.next();
        System.out.print("저자 : ");
        String author = in.next();
        System.out.print("출판사 : ");
        String publisher = in.next();
        System.out.print("출판일 : ");
        String publicationDate = in.next();
        System.out.print("DB 추가일 : ");
        String createDate = in.next();
        System.out.println("도서가 추가되었습니다.");
    }
}
