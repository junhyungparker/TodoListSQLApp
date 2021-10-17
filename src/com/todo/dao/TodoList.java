package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.todo.service.DbConnect;

public class TodoList {
	private List<TodoItem> list;
	
	Connection conn;

	public TodoList() {
		this.conn = DbConnect.getConnection();
	}
	
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, memo, current_date, due_date)"
					+ " values (?,?,?,?);";
			String sql_cate = "insert into category (category) values (?)";
			int records = 0;
			while((line = br.readLine())!= null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String title = st.nextToken();
				String category = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, description);
				//pstmt.setString(4, category);
				pstmt.setString(3, current_date);
				pstmt.setString(4, due_date);
				int count = pstmt.executeUpdate();
				
				pstmt = conn.prepareStatement(sql_cate);
				pstmt.setString(1, category);
				pstmt.executeUpdate();
				
				if(count > 0) records++;
				pstmt.close();
			}
			System.out.println(records + " records read!!");
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, current_date, due_date)"
				+ " values (?,?,?,?);";
		String sql_cate = "insert into category (category) values (?);";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			//pstmt.setString(3, t.getCategory());
			pstmt.setString(3, t.getCurrent_date());
			pstmt.setString(4, t.getDue_date());
			count = pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement(sql_cate);
			pstmt.setString(1, t.getCategory());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int index) {
		String sql = "delete from list where id=?;";
		String sql_cate = "delete from category where id=?;";
		PreparedStatement pstmt;
		PreparedStatement pstmt_cate;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
			
			pstmt_cate = conn.prepareStatement(sql_cate);
			pstmt_cate.setInt(1, index);
			pstmt_cate.executeUpdate();
			pstmt_cate.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int updateItem(TodoItem t) {
		String sql = "update list set title=?, memo=?, current_date=?, due_date=?"
				+" where id = ?;";
		String sql_cate = "update category set category=? where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			//pstmt.setString(3, t.getCategory());
			pstmt.setString(3, t.getCurrent_date());
			pstmt.setString(4, t.getDue_date());
			int num = t.getId();
			pstmt.setInt(5, num);
			count = pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement(sql_cate);
			pstmt.setString(1, t.getCategory());
			pstmt.setInt(2, num);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		Statement stmt_cate;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			stmt_cate = conn.createStatement();
			String sql_cate = "SELECT * FROM category";
			ResultSet rs_cate = stmt_cate.executeQuery(sql_cate);
			while(rs.next() && rs_cate.next()) {
				int id = rs.getInt("id");
				String category = rs_cate.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				TodoItem t = new TodoItem(title, category, description, due_date, is_completed);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
			stmt_cate.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		PreparedStatement pstmt_cate;
		keyword = "%"+keyword+"%";
		try {
			String sql = "SELECT * FROM list WHERE title like ? or memo like ?";
			String sql_cate = "SELECT * FROM category WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			
			pstmt_cate = conn.prepareStatement(sql_cate);
			
			while(rs.next()) {
				int id = rs.getInt("id");	

				pstmt_cate.setInt(1,id);
				ResultSet rs_cate = pstmt_cate.executeQuery();
				
				String category = rs_cate.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				TodoItem t = new TodoItem(title, category, description, due_date, is_completed);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(int num){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		PreparedStatement pstmt_cate;
		try {
			String sql = "SELECT * FROM list WHERE is_completed like ?";
			String sql_cate = "SELECT * FROM category WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			ResultSet rs = pstmt.executeQuery();
			pstmt_cate = conn.prepareStatement(sql_cate);
			while(rs.next()) {
				int id = rs.getInt("id");
				pstmt_cate.setInt(1,id);
				ResultSet rs_cate = pstmt_cate.executeQuery();
				
				String category = rs_cate.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				TodoItem t = new TodoItem(title, category, description, due_date, is_completed);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_completed(num);
				list.add(t);
			}
			pstmt.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<String> getCategories(){
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt_cate;
		try {
			stmt_cate = conn.createStatement();
			String sql_cate = "SELECT DISTINCT category FROM category";
			ResultSet rs_cate = stmt_cate.executeQuery(sql_cate);
			while(rs_cate.next()) {
				String category = rs_cate.getString("category");
				list.add(category);
			}
			stmt_cate.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt_cate;
		PreparedStatement pstmt;
		try {
			String sql_cate = "SELECT * FROM category WHERE category = ?";
			String sql = "SELECT * FROM list WHERE id = ?";
			pstmt_cate = conn.prepareStatement(sql_cate);
			pstmt_cate.setString(1, keyword);
			ResultSet rs_cate = pstmt_cate.executeQuery();
			
			pstmt = conn.prepareStatement(sql);
			while(rs_cate.next()) {
				int id = rs_cate.getInt("id");
				
				pstmt.setInt(1,id);
				ResultSet rs = pstmt.executeQuery();
				
				String category = rs_cate.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				TodoItem t = new TodoItem(title, category, description, due_date, is_completed);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
			pstmt_cate.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		PreparedStatement pstmt_cate;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;
			String sql_cate = "SELECT * FROM category WHERE id=?";
			if (ordering == 0)
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);
			
			pstmt_cate = conn.prepareStatement(sql_cate);
			while(rs.next()) {
				int id = rs.getInt("id");
				
				pstmt_cate.setInt(1,id);
				ResultSet rs_cate = pstmt_cate.executeQuery();
				
				String category = rs_cate.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				TodoItem t = new TodoItem(title, category, description, due_date, is_completed);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getOrderedCate(String orderby, int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		PreparedStatement pstmt;
		try {
			stmt = conn.createStatement();
			String sql_cate = "SELECT * FROM category ORDER BY " + orderby;
			String sql = "SELECT * FROM list WHERE id=?";
			if (ordering == 0)
				sql_cate  += " desc";
			ResultSet rs_cate = stmt.executeQuery(sql_cate);
			
			pstmt = conn.prepareStatement(sql);
			while(rs_cate.next()) {
				int id = rs_cate.getInt("id");
				
				pstmt.setInt(1,id);
				ResultSet rs = pstmt.executeQuery();
				
				String category = rs_cate.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				TodoItem t = new TodoItem(title, category, description, due_date, is_completed);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int completeItem(int index) {
		String sql = "update list set is_completed=?" + " where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
			pstmt.setInt(2, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public void calculateTime(int index) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
        String current_date=f.format(new Date());
        
		String sql = "SELECT * FROM list WHERE id = ?";
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			ResultSet rs = pstmt.executeQuery();
			
			String due_date = rs.getString("due_date");
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");

			LocalDate date1 = LocalDate.parse(current_date, dtf);
			LocalDate date2 = LocalDate.parse(due_date, dtf);
			long daysBetween = ChronoUnit.DAYS.between(date1, date2);
			System.out.println ("마감일까지 " + daysBetween + "일 남았습니다.");
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getCount() {
		Statement stmt;
		int count=0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title) {
		for (TodoItem item : this.getList()) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
}
