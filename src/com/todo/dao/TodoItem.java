package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
    private String title;
    private String category;
    private String desc;
    private String due_date;
    private String current_date;

    public TodoItem(String title, String category, String desc, String due_date){
        this.title=title;
        this.category=category;
        this.desc=desc;
        this.due_date=due_date;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        this.current_date=f.format(new Date());
    }
    
    public String toSaveString() {
    	return title + "##" + category + "##" + desc + "##" + due_date + "##" + current_date + "\n";
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}
}
