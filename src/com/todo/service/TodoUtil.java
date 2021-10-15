package com.todo.service;

import java.util.*;
import java.io.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList l) {
		
		String title, category, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "===== 항목 추가 ==== \n"
				+ "[제목] > ");
		
		title = sc.next();
		if (l.isDuplicate(title)) {
			System.out.println("같은 제목의 항목이 이미 있습니다!");
			return;
		}
		sc.nextLine();
		System.out.print("[카테고리] > ");
		category = sc.nextLine().trim();
		System.out.print("[내용] > ");
		desc = sc.nextLine().trim();
		System.out.print("[마감일자] > ");
		due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, category, desc, due_date);
		if(l.addItem(t)>0)
			System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "==== 항목 삭제 ====\n"
				+ "삭제할 항목의 번호를 입력하세요 > ");
		
		int num = sc.nextInt();
		if(l.deleteItem(num)>0)
			System.out.println("삭제되었습니다!");
/*		if (num > l.getList().size()) {
			System.out.println("항목을 찾을 수 없습니다!");
			return;
		}
		
		TodoItem item = l.getList().get(num-1);
		System.out.println(num + ". [" + item.getTitle() + "] " + item.getCategory() + " - " + item.getDesc() + " - " + item.getDue_date() + " - " + item.getCurrent_date());
		
		while(true) {
			System.out.print("위 항목을 삭제하시겠습니까? (y/n) > ");
			String ans = sc.next();
			if(ans.charAt(0) == 'y') {
				l.deleteItem(item);
				System.out.println("\n삭제되었습니다!");
				break;
			}
			else if(ans.charAt(0) == 'n') {
				System.out.println("\n취소되었습니다.");
				break;
			}
			else {
				System.out.println("\n잘못 입력하였습니다. 다시 입력하세요.");
			}
		}*/
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "==== 항목 수정 ====\n"
				+ "수정할 항목의 번호를 입력하세요 > ");
		int num = sc.nextInt();
		System.out.print("[새 제목] > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("이미 존재하는 항목입니다!");
			return;
		}
		sc.nextLine();
		System.out.print("[새 카테고리] > ");
		String new_category = sc.nextLine().trim();
		System.out.print("[새 내용] > ");
		String new_description = sc.nextLine().trim();
		System.out.print("[새 마감일자] > ");
		String new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date);
		t.setId(num);
		if(l.updateItem(t) > 0)
			System.out.println("수정되었습니다.");
	}

	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개\n", l.getCount());
		for(TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			FileWriter fw = new FileWriter(filename);
			for (TodoItem item : l.getList()) {
				fw.write(item.toSaveString());
			}
			System.out.println("모든 데이터가 저장되었습니다.");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("파일 저장을 실패하였습니다.");
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String str;
			int cnt = 0;
			while ((str = reader.readLine()) != null) {
				StringTokenizer stk=new StringTokenizer(str,"##");
				String title = stk.nextToken();
				String category = stk.nextToken();
				String desc = stk.nextToken();
				String due_date = stk.nextToken();
				String date = stk.nextToken();
				TodoItem t = new TodoItem(title, category, desc, due_date);
				t.setCurrent_date(date);
				l.addItem(t);
				cnt++;
			}
			reader.close();
			System.out.println(cnt + "개의 항목을 읽었습니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
	}
	
	public static void findItem(TodoList l, String key) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[검색결과]");
		
		int cnt = 0, cnt_item = 0;
		for (TodoItem item : l.getList()) {
			cnt++;
			if(item.getTitle().contains(key) || item.getCategory().contains(key) || item.getDesc().contains(key) || item.getDue_date().contains(key) || item.getCurrent_date().contains(key)) {
				System.out.println(cnt + ". [" + item.getTitle() + "] " + item.getCategory() + " - " + item.getDesc() + " - " + item.getDue_date() + " - " + item.getCurrent_date());
				cnt_item++;
			}
		}
		System.out.println("총 " + cnt_item + "개의 항목을 찾았습니다.");
	}
}
