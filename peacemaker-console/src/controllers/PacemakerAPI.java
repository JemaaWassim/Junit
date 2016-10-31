package controllers;

import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import utils.Serializer;

import com.bethecoder.ascii_table.ASCIITable;
import com.google.common.base.Optional;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import models.Activity;

import models.User;
import models.Location;;

public class PacemakerAPI
{private Serializer serializer;
  public Map<Long, User>     userIndex       = new HashMap<>();
  public Map<String, User>   emailIndex      = new HashMap<>();
  public Map<Long, Activity>   activitiesIndex      = new HashMap<>();
  
 // public List<Location> route = new ArrayList<>();
  public PacemakerAPI(Serializer serializer)
  {
    this.serializer = serializer;
  }
  public Location createlocation(float latitude, float longitude){
    Location loc = new Location(latitude,longitude);
    //route.add(loc);
    return loc;
  }
  
  public Collection<User> getUsers ()
  {
    return userIndex.values();
  }
 

  public  void deleteUsers() 
  {
    userIndex.clear();
    emailIndex.clear();
  }

  public User createUser(String firstName, String lastName, String email, String password) 
  {
    User user = new User (firstName, lastName, email, password);
    userIndex.put(user.id, user);
    emailIndex.put(email, user);
    return user;
  }

  public User getUserByEmail(String email) 
  {
    return emailIndex.get(email);
  }

  public User getUser(Long id) 
  {
    return userIndex.get(id);
  }

  public void deleteUser(Long id) 
  {
    User user = userIndex.remove(id);
    emailIndex.remove(user.email);
  }
  
  public Activity createActivity(Long id, String type, String location, double distance,LocalDateTime datetime,Duration duration)
  {
    Activity activity = null;
    Optional<User> user = Optional.fromNullable(userIndex.get(id));
    if (user.isPresent())
    {
      activity = new Activity( type, location, distance, datetime, duration);
      user.get().activities.put(activity.id, activity);
      activitiesIndex.put(activity.id, activity);
    }
    return activity;
  }
  
  public Activity getActivity (Long id)
  {
    return activitiesIndex.get(id);
  }
  
  public Collection<Activity> getsortedActivities (Long user,String type)
  {  List<Activity> recipes = new ArrayList<Activity>(userIndex.get(user).activities.values());
  
  if (type.equalsIgnoreCase("distance")){
	  Collections.sort(recipes, new Comparator<Activity>() {
		    @Override
		    public int compare(Activity z1, Activity z2) {
		        if (z1.distance > z2.distance)
		            return 1;
		        if (z1.distance < z2.distance)
		            return -1;
		        return 0;
		    }
		});
 }
  if (type.equalsIgnoreCase("duration")){
	  Collections.sort(recipes, new Comparator<Activity>() {
		    @Override
		    public int compare(Activity z1, Activity z2) {
		        if (z1.duration.toMillis()>z2.duration.toMillis())
		            return 1;
		        if (z1.duration.toMillis() < z2.duration.toMillis())
		            return -1;
		        return 0;
		    }
		});
  }
  if (type.equalsIgnoreCase("type")){
	  Collections.sort(recipes, new Comparator<Activity>() {
		    @Override
		    public int compare(Activity z1, Activity z2) {
		        if (z1.type.length() >z2.type.length())
		            return 1;
		        if (z1.type.length()< z2.type.length())
		            return -1;
		        return 0;
		    }
		});
  }
  String[][] data = new String[recipes.size()][7];
	int i = 0;
	for (Activity o : recipes) {
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
  return recipes;

  
    
  }
  
  
  public Collection<Activity> getActivities (Long user)
  {
    return (Collection<Activity>) userIndex.get(user).activities.values();
  }
  
  
  public void addLocation (Long id, float latitude, float longitude)
  {
    Optional<Activity> activity = Optional.fromNullable(activitiesIndex.get(id));
    if (activity.isPresent())
    {
      activity.get().route.add(new Location(latitude, longitude));
    }
  }
 
  @SuppressWarnings("unchecked")
  public void load() throws Exception
  {
    serializer.read();
    activitiesIndex = (Map<Long, Activity>) serializer.pop();
    emailIndex      = (Map<String, User>)   serializer.pop();
    userIndex       = (Map<Long, User>)     serializer.pop();
   // route = (List<Location>) serializer.pop();
  }

  public void store() throws Exception
  {
    serializer.push(userIndex);
    serializer.push(emailIndex);
    serializer.push(activitiesIndex);
   // serializer.push(route);
    serializer.write(); 
  }
  //...
}

