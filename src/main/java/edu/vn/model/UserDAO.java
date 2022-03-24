package edu.vn.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
	SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/YYYY");

	public static List<User> ls = new ArrayList();

	public List<User> dummyData() throws ParseException {
		ls.add(new User("A1", "123", "Nguyen Van A1", 22, formatDate.parse("12/12/2012"), false));
		ls.add(new User("A2", "123", "Nguyen Van A2", 22, formatDate.parse("12/12/2012"), false));
		ls.add(new User("A3", "123", "Nguyen Van A3", 22, formatDate.parse("12/12/2012"), false));
		ls.add(new User("A4", "123", "Nguyen Van A4", 22, formatDate.parse("12/12/2012"), false));
		ls.add(new User("A5", "123", "Nguyen Van A5", 22, formatDate.parse("12/12/2012"), false));
		return ls;
	}

	public List<User> getAll() {
		return ls;
	}

	public int insert(User u) {
		ls.add(u);
		return 1;
	}

	public int update(User u) {
		for (int i = 0; i < ls.size(); i++) {
			if (ls.get(i).getUsername().equals(u.getUsername())) {
				ls.set(i, u);
				return i;
			}
		}
		return -1;
	}

	public User findByUsername(String username) {
		for (User user : ls) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	public int save(User user) {
		if (findByUsername(user.getUsername()) != null) {
			update(user);
		} else {
			insert(user);
		}
		return 1;
	}
	
	public int delete(String username) {
		User user = findByUsername(username);
		if(user != null) {
			ls.remove(user);
			return 1;
		}
		return -1;
	}
}
