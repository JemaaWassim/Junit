package controllers;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

import utils.JsonSerilizer;
import utils.Serializer;
import utils.XMLSerializer;
import utils.YamlSerializer;

import com.bethecoder.ascii_table.ASCIITable;
import com.bethecoder.ascii_table.ASCIITableHeader;
import com.bethecoder.ascii_table.spec.IASCIITableAware;
import com.google.common.base.Optional;

import models.Activity;
import models.Location;
import models.User;
import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;

public class Main {
	Serializer x;
	PacemakerAPI paceApi;// = new PacemakerAPI(x);
	Serializer x2;
	PacemakerAPI paceApi2;

	public Main(String link, String format) {
		if (format.equalsIgnoreCase("json")) {
			System.out.println(format);
			this.x = new JsonSerilizer(new File(link));
		} else if (format.equalsIgnoreCase("xml")) {
			System.out.println(format);

			this.x = new XMLSerializer(new File(link));
		} else if (format.equalsIgnoreCase("Yaml")) {
			System.out.println(format+"ggg");

			this.x = new YamlSerializer(new File(link));
		}else {
			System.out.println(format);

			this.x = new XMLSerializer(new File(link));
		}
		this.paceApi = new PacemakerAPI(x);
		
	}
	@Command(description = "load file")
	public void store() throws Exception{
		paceApi.store();
	}
	@Command(description = "store file")
	public
	void load() throws Exception{
	
		
		paceApi.load();
	}
	@Command(description = "change file format")
	public void ChangeFileFormat(@Param(name = "file format") String fileformatn) {
		if (fileformatn.equalsIgnoreCase("json")) {
			System.out.println(fileformatn);
			this.x2 = new JsonSerilizer(new File("src/utils/jasonData"
					+ LocalDateTime.now().getMinute() + ".txt"));
		}
		if (fileformatn.equalsIgnoreCase("xml")) {
			System.out.println(fileformatn);
			this.x2 = new XMLSerializer(new File(
					"src/utils/XMLDataStore"+LocalDateTime.now().getMinute()+".xml"));
		}
		if (fileformatn.equalsIgnoreCase("Yaml")) {
			System.out.println("ccc" + fileformatn);
			this.x2 = new YamlSerializer(new File("src/utils/YamlData"+LocalDateTime.now().getMinute()+".yml"));
		}
		this.paceApi2 = new PacemakerAPI(x2);
		this.paceApi2.activitiesIndex = this.paceApi.activitiesIndex;
		//this.paceApi2.route = this.paceApi.route;
		this.paceApi2.emailIndex = this.paceApi.emailIndex;
		this.paceApi2.userIndex = this.paceApi.userIndex;

	}

	@Command(description = "Create a new User")
	public void createUser(@Param(name = "first name") String firstName,
			@Param(name = "last name") String lastName,
			@Param(name = "email") String email,
			@Param(name = "password") String password) {
		paceApi.createUser(firstName, lastName, email, password);
		ListUsers();
	}

	@Command(description = "Get a Users details")
	public void ListUser(@Param(name = "id") Long id) {
try {
	User user = paceApi.getUser(id);
	String[][] data = new String[1][5];
	data[0][0] = user.id.toString();
	data[0][1] = user.firstName;
	data[0][2] = user.lastName;
	data[0][3] = user.email;
	data[0][4] = user.password;

	String[] header = { "ID", "Type", "Location", "Distance", "Route" };

	ASCIITable.getInstance().printTable(header, data);
} catch (Exception e) {
	System.out.println("user does not exist");
	// TODO: handle exception
}

		

	}

	@Command(description = "Get a Users details by email")
	public void ListUserbyEmail(@Param(name = "email") String email) {
		
		try {
			User user = paceApi.getUserByEmail(email);
			String[][] data = new String[1][5];
			data[0][0] = user.id.toString();
			data[0][1] = user.firstName;
			data[0][2] = user.lastName;
			data[0][3] = user.email;
			data[0][4] = user.password;

			String[] header = { "ID", "Type", "Location", "Distance", "Route" };

			ASCIITable.getInstance().printTable(header, data);
		} catch (Exception e) {
			System.out.println("user does not exist");
		}
	}

	@Command(description = "Get all users details")
	public void ListUsers() {
	
		try {
			Collection<User> users = (Collection<User>) paceApi.getUsers();
			int i =0 ;
			String[][] data = new String[users.size()][5];
			for (User u : users) {
				data[i][0] = u.id.toString();
				data[i][1] = u.firstName;
				data[i][2] = u.lastName;
				data[i][3] = u.email;
				data[i][4] = u.password;
				i++;
			}
			String[] header = { "ID", "firstName", "lastName", "email", "password" };

			ASCIITable.getInstance().printTable(header, data);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// *****
	@Command(description = "Get all acivities details by user")
	public void ListActivities(@Param(name = "User ID")Long id) {
	
		Collection<Activity> Activities = (Collection<Activity>) paceApi
				.getActivities(id);
//		System.out.println(Activities);

		String[][] data = new String[Activities.size()][7];
		int i = 0;
		for (Activity o : Activities) {
			String idS = Double.toString(o.id);
			String Dis = Double.toString(o.distance);
			String route;
			String date;
			if (o.datetime == null) {
				date = "null";
			} else {
				date = o.datetime.toString();
			}
			String duration2;
			if (o.duration == null) {
				duration2 = "null";
			} else {
				duration2 = o.duration.toString();
			}
			if (o.route == null) {
				route = "null";
			} else {
				route = o.route.toString();
			}
			System.out.println(i);
			data[i][0] = idS;
			data[i][1] = o.type;
			data[i][2] = o.location;
			data[i][3] = Dis;
			data[i][4] = route;
			data[i][5] = date;//
			data[i][6] = duration2;
			i++;
		}
		String[] header = { "ID", "Type", "Location", "Distance", "Route",
				"datetime", "duration" };
		ASCIITable.getInstance().printTable(header, data);
	}

	@Command(description = "Get sorted acivities details")
	public void ListActivitiesSortedBY(@Param(name = "User ID")Long id, @Param(name = "value(duration/distance/type)")String type) {
		paceApi.getsortedActivities(id, type);

	}

	@Command(description = "Delete a User by id")
	public void deleteUser(@Param(name = "id") Long id) {
		Optional<User> user = Optional.fromNullable(paceApi.getUser(id));
		if (user.isPresent()) {
			paceApi.deleteUser(user.get().id);
		}
	}

	@Command(description = "Add an activity")
	public void addActivity(@Param(name = "user-id") Long id,
			@Param(name = "type") String type,
			@Param(name = "location") String location,
			@Param(name = "distance") double distance,
			@Param(name = "datetime") String datetime,
			@Param(name = "duration") String duration) {
		Optional<User> user = Optional.fromNullable(paceApi.getUser(id));
		if (user.isPresent()) {

			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("dd:MM:yyyy HH:mm:ss");
			LocalDateTime dt1 = LocalDateTime.parse(datetime, formatter);
			String timestampStr = duration;
			String[] tokens = timestampStr.split(":");
			int hours = Integer.parseInt(tokens[0]);
			int minutes = Integer.parseInt(tokens[1]);
			int seconds = Integer.parseInt(tokens[2]);
			Long longduration = Duration.ofHours(hours).toMinutes()
					+ Duration.ofMinutes(minutes).toMinutes()
					+ Duration.ofSeconds(seconds).toMinutes();

			Duration dd = Duration.ofMinutes(longduration);

			paceApi.createActivity(id, type, location, distance, dt1, dd);

		}
		if (!user.isPresent()){
			System.out.println("user does not exist for acctivity");
		}
		
	}

	@Command(description = "Add Location to an activity")
	public void addLocation(@Param(name = "activity-id") Long id,
			@Param(name = "latitude") float latitude,
			@Param(name = "longitude") float longitude) {
		Optional<Activity> activity = Optional.fromNullable(paceApi
				.getActivity(id));
		if (activity.isPresent()) {
			paceApi.addLocation(activity.get().id, latitude, longitude);
		}else {
			System.out.println("activity does not exists");
		}
	}

	public static void main(String[] args) throws Exception {
		Main main;
		Scanner keyboard = new Scanner(System.in);
		System.out.println("please specify the file format");
		String fileformatx = keyboard.nextLine();
		System.out.println("please specify the link to the file");
		String filelink = keyboard.nextLine();
		main = new Main(filelink, fileformatx);

		try {

			main.paceApi.load();
			Shell shell = ShellFactory.createConsoleShell("pm",
					"Welcome to pacemaker-console - ?help for instructions",main);
			shell.commandLoop();
			if (main.x2 != null) {
				main.paceApi2.store();
			} else {
				main.paceApi.store();
			}

		} catch (Exception E) {
			System.out.println("error ,file not loaded , proceed in a new file");
			System.out.println(E.toString());
			Shell shell = ShellFactory.createConsoleShell("pm",
					"Welcome to pacemaker-console - ?help for instructions",main);
			shell.commandLoop();
			if (main.x2 != null) {
				main.paceApi2.store();
			}
			main.paceApi.store();
		}

	}
	
}