package controllers;

import static org.junit.Assert.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.plaf.basic.BasicBorders.MarginBorder;

import models.Activity;
import models.Location;
import models.Fixtures.*;
import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import controllers.PacemakerAPI;
import static models.Fixtures.users;
import static models.Fixtures.activities;
import static models.Fixtures.locations;

public class PacemakerAPITest
{
  private PacemakerAPI pacemaker;

  @Before
  public void setup()
  {
    pacemaker = new PacemakerAPI(null);
  }

  @After
  public void tearDown()
  {
    pacemaker = null;
  }
  @Test
  public void testUser()
  {
    User homer = new User ("homer", "simpson", "homer@simpson.com",  "secret");

    assertEquals (0, pacemaker.getUsers().size());
    pacemaker.createUser("homer", "simpson", "homer@simpson.com", "secret");
    assertEquals (1, pacemaker.getUsers().size());

    assertEquals (homer, pacemaker.getUserByEmail("homer@simpson.com"));
  }
  @Test
  public void testUsers()
  {
    for (User user : users)
    {
      pacemaker.createUser(user.firstName, user.lastName, user.email, user.password);
    }
    assertEquals (users.length, pacemaker.getUsers().size());
    for (User user: users)
    {
      User eachUser = pacemaker.getUserByEmail(user.email);
      assertEquals (user, eachUser);
      assertNotSame(user, eachUser);
    }
  }
  @Test
  public void testDeleteUserByID()
  {
    for (User user : users)
    {
      pacemaker.createUser(user.firstName, user.lastName, user.email, user.password);
    }
    assertEquals (users.length, pacemaker.getUsers().size());
    User marge = pacemaker.getUserByEmail("marge@simpson.com");
    pacemaker.deleteUser(marge.id);
    assertEquals (users.length-1, pacemaker.getUsers().size());    
  }

  @Test
  public void testAddActivity()
  {User x =pacemaker.createUser("marge", "wass", "marge@simpson.com", "secret");
      User marge = pacemaker.getUserByEmail("marge@simpson.com"); 
      assertTrue(marge!=null);
   Activity activity =pacemaker.createActivity((long)marge.id,activities[0].type.toString(), activities[0].location.toString(), (double)activities[0].distance,(LocalDateTime)activities[0].datetime,(Duration)activities[0].duration); 
      Activity returnedActivity = (Activity)pacemaker.getActivity(activity.id);
    assertEquals(activities[0],  returnedActivity);
    assertNotSame(activities[0], returnedActivity);
  }
  
  @Test
  public void testEquals()
  {
    User homer = new User ("homer", "simpson", "homer@simpson.com",  "secret");
    User homer2 = new User ("homer", "simpson", "homer@simpson.com",  "secret"); 
    User bart   = new User ("bart", "simpson", "bartr@simpson.com",  "secret"); 

    assertEquals(homer, homer);
    assertEquals(homer, homer2);
    assertNotEquals(homer, bart);
    assertSame(homer, homer);
    assertNotSame(homer, homer2);
  }
  @Test
  public void testAddActivityWithSingleLocation()
  {User x =pacemaker.createUser("marge", "wass", "marge@simpson.com", "secret");
    User marge = pacemaker.getUserByEmail("marge@simpson.com");
    Long activityId = pacemaker.createActivity(marge.id, activities[0].type, activities[0].location, activities[0].distance,LocalDateTime.now(),Duration.ofHours(1)).id;
    pacemaker.addLocation(activityId, locations[0].latitude, locations[0].longitude);

    Activity activity = pacemaker.getActivity(activityId);
    assertEquals (1, activity.route.size());
    assertEquals(0.0001, locations[0].latitude,  activity.route.get(0).latitude);
    assertEquals(0.0001, locations[0].longitude, activity.route.get(0).longitude);   
  }
  
  @Test
  public void testAddActivityWithMultipleLocation()
  {User x =pacemaker.createUser("marge", "wass", "marge@simpson.com", "secret");
  Optional<User> c = Optional.fromNullable( pacemaker.getUserByEmail("wassim@simpson.com"));
  Optional<User> marge = Optional.fromNullable( pacemaker.getUserByEmail("marge@simpson.com"));
 // User marge = pacemaker.getUserByEmail("marge@simpson.com");
 assertTrue(marge.isPresent());
assertEquals(c.isPresent(), false);
    Long activityId = pacemaker.createActivity(marge.get().id, activities[0].type, activities[0].location, activities[0].distance,LocalDateTime.now(),Duration.ofHours(1)).id;

    for (Location location : locations)
    {
      pacemaker.addLocation(activityId, location.latitude, location.longitude);      
    }

    Activity activity = pacemaker.getActivity(activityId);
    assertEquals (locations.length, activity.route.size());
    int i = 0;
    for (Location location : activity.route)
    {
      assertEquals(location, locations[i]);
      i++;
    }
  }
  @Test
  public void testGetUsers(){
	  PacemakerAPI pace = new PacemakerAPI(null);
	  assertTrue(pace.getUsers().isEmpty());
	  User x =pace.createUser("marge", "wass", "marge@simpson.com", "secret");
	  assertTrue(pace.getUsers().size()>0);
	  
  }
  @Test
  public void testGetUsersByid(){
   
    User x =pacemaker.createUser("marge", "wass", "marge@simpson.com", "secret");
    assertEquals(x, pacemaker.getUser(x.id));
    
  }
  @Test
  public void testDeleteUsers(){
   
	  {
		    for (User user : users)
		    {
		      pacemaker.createUser(user.firstName, user.lastName, user.email, user.password);
		    }
		    assertEquals (users.length, pacemaker.getUsers().size());
		    pacemaker.deleteUsers();
		    assertEquals (0, pacemaker.getUsers().size());    
		  }
  }
  @Test
  public void testgetActivities(){
	  User x =pacemaker.createUser("marge", "wass", "marge@simpson.com", "secret");
	    User marge = pacemaker.getUserByEmail("marge@simpson.com");
	    Long activityId = pacemaker.createActivity(marge.id, activities[0].type, activities[0].location, activities[0].distance,LocalDateTime.now(),Duration.ofHours(1)).id;
	 assertEquals((Collection<Activity>) pacemaker.userIndex.get(marge.id).activities.values(), pacemaker.getActivities(marge.id));
	  
  }
  @Test
  public void testAddLocation()
  {
	  
	    Location s= pacemaker.createlocation(22.2f, 23.3f);
	    assertTrue(s.latitude ==22.2f );
	    assertTrue(s.longitude ==23.3f);

  }
  /*
  @Test
  public void testgetLocation(){
	  User x =pacemaker.createUser("marge", "wass", "marge@simpson.com", "secret");
	    User marge = pacemaker.getUserByEmail("marge@simpson.com");
	    Long activityId = pacemaker.createActivity(marge.id, activities[0].type, activities[0].location, activities[0].distance,LocalDateTime.now(),Duration.ofHours(1)).id;
	    assertEquals(pacemaker.getActivity(activityId).route.size(),0);
	    pacemaker.addLocation(activityId, 22.2f, 23.3f);
	    assertEquals(pacemaker.getActivity(activityId).route.size(), 1);
	  assertEquals((Collection<Location>) pacemaker.getActivity(activityId).route, pacemaker.getLocation());
  }*/
  @Test
  public void testSortedActiviies(){
	  User x =pacemaker.createUser("marge", "wass", "marge@simpson.com", "secret");
	    User marge = pacemaker.getUserByEmail("marge@simpson.com");
	    Long activityId = pacemaker.createActivity(marge.id, activities[0].type, activities[0].location, activities[0].distance,activities[0].datetime,activities[0].duration).id;
	    Long activityId2 = pacemaker.createActivity(marge.id, activities[1].type, activities[1].location, activities[1].distance,activities[1].datetime,activities[1].duration).id;
	    Long activityId3 = pacemaker.createActivity(marge.id, activities[2].type, activities[2].location, activities[2].distance,activities[2].datetime,activities[2].duration).id;
	    Long activityId4 = pacemaker.createActivity(marge.id, "ru", activities[2].location, activities[2].distance,activities[2].datetime,activities[2].duration).id;
	    Long activityId5 = pacemaker.createActivity(marge.id, activities[3].type, activities[3].location, activities[3].distance,activities[3].datetime,activities[3].duration).id;

	    System.out.println(pacemaker.activitiesIndex.get(activityId));
		System.out.println(pacemaker.activitiesIndex.get(activityId2));
		System.out.println(pacemaker.activitiesIndex.get(activityId3));
		System.out.println(pacemaker.activitiesIndex.get(activityId4));
	    Collection<Activity> resul = pacemaker.getsortedActivities(marge.id, "distance");
		 assertTrue(((List<Activity>) resul).get(0).equals(pacemaker.activitiesIndex.get(activityId)));
		 assertTrue(((List<Activity>) resul).get(1).equals(pacemaker.activitiesIndex.get(activityId3)));
		 assertTrue(((List<Activity>) resul).get(2).equals(pacemaker.activitiesIndex.get(activityId4)));
		 assertTrue(((List<Activity>) resul).get(3).equals(pacemaker.activitiesIndex.get(activityId2)));
			
		 Collection<Activity> resul2 = pacemaker.getsortedActivities(marge.id, "type");
		 assertTrue(((List<Activity>) resul2).get(0).equals(pacemaker.activitiesIndex.get(activityId2)));
		 assertTrue(((List<Activity>) resul2).get(1).equals(pacemaker.activitiesIndex.get(activityId4)));
		 assertTrue(((List<Activity>) resul2).get(2).equals(pacemaker.activitiesIndex.get(activityId3)));
		 assertTrue(((List<Activity>) resul2).get(3).equals(pacemaker.activitiesIndex.get(activityId)));
				
		 Collection<Activity> resul3 = pacemaker.getsortedActivities(marge.id, "duration");
		 assertTrue(((List<Activity>) resul3).get(0).equals(pacemaker.activitiesIndex.get(activityId2)));
		 assertTrue(((List<Activity>) resul3).get(1).equals(pacemaker.activitiesIndex.get(activityId5)));
		 assertTrue(((List<Activity>) resul3).get(2).equals(pacemaker.activitiesIndex.get(activityId3)));
		 assertTrue(((List<Activity>) resul3).get(3).equals(pacemaker.activitiesIndex.get(activityId4)));
		 assertTrue(((List<Activity>) resul3).get(4).equals(pacemaker.activitiesIndex.get(activityId)));

  }
}