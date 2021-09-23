package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		TodoUtil.loadList(l, "todolist.txt");
		Menu.displaymenu();
		do { 
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "help":
				Menu.displaymenu();
				break;
			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				l.sortByName();
				isList = true;
				System.out.println("\n제목순으로 정렬하였습니다. ");
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				isList = true;
				System.out.println("\n제목역순으로 정렬하였습니다. ");
				break;
				
			case "ls_date":
				l.sortByDate();
				isList = true;
				System.out.println("\n날짜순으로 정렬하였습니다. ");
				break;

			case "exit":
				quit = true;
				break;

			default:
				System.out.println("존재하지 않는 명령어입니다. 다시 입력해주세요. [도움말: help]");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
		TodoUtil.saveList(l, "todolist.txt");
	}
}
