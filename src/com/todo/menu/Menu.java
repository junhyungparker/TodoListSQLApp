package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("==== TodoList 명령어 목록 =====");
        System.out.println("1. add: 항목 추가");
        System.out.println("2. del: 항목 삭제");
        System.out.println("3. edit: 항목 수정");
        System.out.println("4. ls: 전체 항목 조회");
        System.out.println("5. ls_name_asc: 제목순 정렬");
        System.out.println("6. ls_name_desc: 제목역순 정렬");
        System.out.println("7. ls_date: 날짜순 정렬");
        System.out.println("8. exit: 프로그램 종료");
        System.out.println();
    }
    
    public static void prompt()
    {
    	System.out.print("\n어떤 작업을 수행하고 싶으십니까? > ");
    }
}
