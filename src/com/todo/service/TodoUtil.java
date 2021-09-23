package com.todo.service;

import java.util.*;
import java.io.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "===== 항목 추가 ==== \n"
				+ "[제목] > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.println("같은 제목의 항목이 이미 있습니다!");
			return;
		}
		sc.nextLine();
		System.out.print("[내용] > ");
		desc = sc.nextLine();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "==== 항목 제거 ====\n"
				+ "[제목] > ");
		
		String title = sc.next();
		
		if (!l.isDuplicate(title)) {
			System.out.println("["+title+"]의 제목을 가진 항목을 찾을 수 없습니다!");
			return;
		}
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("\n삭제되었습니다!");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "==== 항목 수정 ====\n"
				+ "수정하고 싶은 항목의 제목을 입력하세요 > ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("["+title+"]의 제목을 가진 항목을 찾을 수 없습니다!");
			return;
		}
		sc.nextLine();
		System.out.print("[새로운 제목] > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("이미 존재하는 항목입니다!");
			return;
		}
		sc.nextLine();
		System.out.print("[새로운 내용] > ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("\n수정되었습니다!");
			}
		}

	}

	public static void listAll(TodoList l) {
		for (TodoItem item : l.getList()) {
			System.out.println("[" + item.getTitle() + "] " + item.getDesc() + " - " + item.getCurrent_date());
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			FileWriter fw = new FileWriter(filename);
			for (TodoItem item : l.getList()) {
				fw.write(item.getTitle() + "##" + item.getDesc() + "##" + item.getCurrent_date() + "\n");
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
				String desc = stk.nextToken();
				String date = stk.nextToken();
				TodoItem t = new TodoItem(title, desc);
				t.setCurrent_date(date);
				l.addItem(t);
				cnt++;
			}
			System.out.println(cnt + "개의 항목을 읽었습니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
