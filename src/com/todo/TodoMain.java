package com.todo;

import java.util.*;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean quit = false;
		//l.importData("todolist.txt");
		Menu.displaymenu();
		do { 
			Menu.prompt();
			String command = sc.nextLine();
			StringTokenizer stk = new StringTokenizer(command," ");
			String choice = stk.nextToken();
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
				
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
				
			case "ls_cate_asc":
				TodoUtil.listAll(l,"category",1);
				break;
				
			case "ls_cate_desc":
				TodoUtil.listAll(l,"category",0);
				break;
				
			case "find_cate":
				String cate = stk.nextToken();
				TodoUtil.findCateList(l,cate);
				break;

			case "ls_name":
				System.out.println("\n제목순으로 정렬하였습니다. ");
				TodoUtil.listAll(l,"title", 1);
				break;

			case "ls_name_desc":
				System.out.println("\n제목역순으로 정렬하였습니다. ");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("\n날짜순으로 정렬하였습니다. ");
				TodoUtil.listAll(l, "due_date", 1);
				break;
				
			case "ls_date_desc":
				System.out.println("\n날짜역순으로 정렬하였습니다. ");
				TodoUtil.listAll(l, "due_date", 0);
				break;

			case "find":
				String key = stk.nextToken();
				TodoUtil.findItem(l, key);
				break;
				
			case "comp":
				int cnt = 0;
				while(stk.hasMoreTokens()) {
					TodoUtil.completeItem(l, Integer.parseInt(stk.nextToken()));
					cnt++;
				}
				System.out.println("총 " + cnt + "개의 항목이 완료처리 되었습니다!");
				break;
				
			case "ls_comp":
				TodoUtil.listAll(l, 1);
				break;
				
			case "calc_time":
				TodoUtil.calculateTime(l, Integer.parseInt(stk.nextToken()));
				break;
			
			case "exit":
				quit = true;
				break;

			default:
				System.out.println("존재하지 않는 명령어입니다. 다시 입력해주세요. [도움말: help]");
				break;
			}
		} while (!quit);
		TodoUtil.saveList(l, "todolist.txt");
	}
}
