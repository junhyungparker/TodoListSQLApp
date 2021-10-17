package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("==== TodoList 명령어 목록 =====");
        System.out.println("1.  add: 항목 추가");
        System.out.println("2.  del: 항목 삭제");
        System.out.println("3.  edit: 항목 수정");
        System.out.println("4.  find <키워드>: 문자열 검색");
        System.out.println("5.  ls: 전체 항목 조회");
        System.out.println("6.  calc_time: 마감일까지 남은 일수 조회");
        System.out.println("7.  ls_cate: 카테고리 조회");
        System.out.println("8.  find_cate <키워드>: 카테고리 검색");
        System.out.println("9.  comp <번호>: 완료 체크");
        System.out.println("10. ls_comp: 완료된 항목 조회");
        System.out.println("11. ls_name: 전체 항목 제목순 정렬");
        System.out.println("12. ls_name_desc: 전체 항목 제목역순 정렬");
        System.out.println("13. ls_date: 전체 항목 날짜순 정렬");
        System.out.println("14. ls_date_desc: 전체 항목 날짜역순 정렬");
        System.out.println("15. ls_cate_asc: 전체 항목 카테고리순 정렬");
        System.out.println("16. ls_cate_desc: 전체 항목 카테고리역순 정렬");
        System.out.println("17. exit: 프로그램 종료");
        System.out.println();
    }
    
    public static void prompt()
    {
    	System.out.print("\n어떤 작업을 수행하고 싶으십니까? > ");
    }
}
