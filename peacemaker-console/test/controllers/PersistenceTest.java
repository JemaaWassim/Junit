package controllers;

import static org.junit.Assert.*;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

import models.Activity;
import models.Location;
import models.User;

import org.junit.Test;

import controllers.PacemakerAPI;

//import utils.BinarySerializer;
import utils.JsonSerilizer;
import utils.Serializer;
import utils.XMLSerializer;
import utils.YamlSerializer;
import models.Fixtures.*;
import static models.Fixtures.users;
import static models.Fixtures.activities;
import static models.Fixtures.locations;

public class PersistenceTest {
	  PacemakerAPI pacemaker;
	  PacemakerAPI pacemaker2;
	  void populate (PacemakerAPI pacemaker)
	  {  
	    for (User user : users)
	    {
	      pacemaker.createUser(user.firstName, user.lastName, user.email, user.password);
	    }
	    User user1 = pacemaker.getUserByEmail(users[0].email);
	    Activity activity = pacemaker.createActivity(user1.id,activities[0].type.toString(), activities[0].location.toString(), (double)activities[0].distance,activities[0].datetime,(Duration)activities[0].duration); 
	    pacemaker.createActivity(user1.id,activities[1].type.toString(), activities[1].location.toString(), (double)activities[1].distance,activities[1].datetime,(Duration)activities[1].duration); 
	    User user2 = pacemaker.getUserByEmail(users[1].email);
	    pacemaker.createActivity(user2.id,activities[2].type.toString(), activities[2].location.toString(), (double)activities[2].distance,activities[2].datetime,(Duration)activities[2].duration); 
	    pacemaker.createActivity(user2.id,activities[3].type.toString(), activities[3].location.toString(), (double)activities[3].distance,activities[3].datetime,(Duration)activities[3].duration); 

	    for (Location locat : locations)
	    {
	      pacemaker.addLocation(activity.id, locat.latitude, locat.longitude);
	    }
	  }
	  
	  
	  
	  
	  @Test
	  public void testPopulate()
	  { 
	    pacemaker = new PacemakerAPI(null);

	    assertEquals(0, pacemaker.getUsers().size());
	    populate (pacemaker);

	    assertEquals(users.length, pacemaker.getUsers().size());
	    assertEquals(2, pacemaker.getUserByEmail(users[0].email).activities.size());
	    assertEquals(2, pacemaker.getUserByEmail(users[1].email).activities.size());   
	    Long activityID = pacemaker.getUserByEmail(users[0].email).activities.keySet().iterator().next();
	    assertEquals(locations.length, pacemaker.getActivity(activityID).route.size());   
	  }
	  void deleteFile(String fileName)
	  {
	    File datastore = new File (fileName);
	    if (datastore.exists())
	    {
	      datastore.delete();
	    }
	  }
	  @Test
	  public void testXMLSerializer() throws Exception
	  { 
	    String datastoreFile = "testdatastore.xml";
	    deleteFile (datastoreFile);

	    Serializer serializer = new XMLSerializer(new File (datastoreFile));

	    pacemaker = new PacemakerAPI(serializer); 
	    populate(pacemaker);
	    pacemaker.store();

	    PacemakerAPI pacemaker2 =  new PacemakerAPI(serializer);
	    pacemaker2.load();

	    assertEquals (pacemaker.getUsers().size(), pacemaker2.getUsers().size());
	    for (User user : pacemaker.getUsers())
	    {
	      assertTrue (pacemaker2.getUsers().contains(user));
	    }
	    deleteFile ("testdatastore.xml");
	  }
	  @Test
	  public void testJSONSerializer() throws Exception
	  { 
		  Serializer serializer2 = new JsonSerilizer(new File ("src/utils/jasonData52.xml"));

		  PacemakerAPI    pacemaker2 = new PacemakerAPI(serializer2); 
		   pacemaker2.store();
		  
		   Serializer serializer3 = new JsonSerilizer(new File ("src/utils/jasonData52.xml"));
pacemaker2.load();
PacemakerAPI   pacemaker3 = new PacemakerAPI(serializer3); 
	  assertEquals (pacemaker2.getUsers().size(), pacemaker3.getUsers().size());
		
	
	   /* for (User user : paceApi.getUsers())
	    {
	      assertTrue (paceApi2.getUsers().contains(user));
	    }
	    deleteFile ("testdatastore.txt");
	    
	    
	    Serializer ser1 =new JsonSerilizer(new File("file"));
		
	PacemakerAPI paceApiser1=  new PacemakerAPI(ser1) ;
	PacemakerAPI paceApiser2=  new PacemakerAPI(ser1) ;
	paceApiser1.store();
	paceApiser2.load();
	assertEquals (paceApiser1.getUsers().size(), paceApiser2.getUsers().size());
	
	    */
	  }
	  
	  
	  @Test
	  public void testYamlSerializer() throws Exception
	  { 
		  Serializer yaml =new YamlSerializer(new File("testdatastore2.txt"));
			
		PacemakerAPI paceApiYaml=  new PacemakerAPI(yaml) ;
		PacemakerAPI paceApiYaml2=  new PacemakerAPI(yaml) ;


		//User homer = paceApiYaml.createUser("homer", "simpson", "homer@simpson.com",  "secret");
		//User homer2 = paceApiYaml.createUser("homer2", "simpso2", "homer2@simpson.com",  "secret2");
	//	Activity activity =paceApiYaml.createActivity((long)homer.id,activities[0].type.toString(), activities[0].location.toString(), (double)activities[0].distance,activities[0].datetime,(Duration)activities[0].duration); 
		//Activity activity2 =paceApi.createActivity((long)homer2.id,activities[0].type.toString(), activities[0].location.toString(), (double)activities[0].distance,LocalDateTime.parse(activities[0].datetime.toString()),(Duration)activities[0].duration); 

		paceApiYaml.store();
		paceApiYaml2.load();
		
	  assertEquals (paceApiYaml.getUsers().size(), paceApiYaml2.getUsers().size());
		
	
	    for (User user : paceApiYaml.getUsers())
	    {
	      assertTrue (paceApiYaml2.getUsers().contains(user));
	    }
	    deleteFile ("testdatastore.txt");
	    
	  }
	  
}
