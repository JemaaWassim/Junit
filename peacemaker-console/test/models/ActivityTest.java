package models;

import static org.junit.Assert.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import static models.Fixtures.activities;
import com.google.common.base.Objects;
public class ActivityTest
{DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mm:ss");
  Activity test = new Activity ("walk",  "fridge", 0.001, LocalDateTime.parse("12:10:2013 09:00:00", formatter),Duration.ofHours(1));
  Activity test2 = new Activity ("walk",  "fridge", 0.001, LocalDateTime.parse("12:10:2013 09:00:00", formatter),Duration.ofHours(1));
  Activity test3 = new Activity ("walk",  "school", 0.002, LocalDateTime.parse("12:10:2013 01:00:00", formatter),Duration.ofHours(2));
  Activity test4 = new Activity ("run",  "fridge", 0.001, LocalDateTime.parse("12:10:2013 09:00:00", formatter),Duration.ofHours(1));
  Activity test5 = new Activity ("walk",  "fridge", 0.002, LocalDateTime.parse("12:10:2013 09:00:00", formatter),Duration.ofHours(1));
 Activity test6 = new Activity ("walk",  "fridge", 0.001, LocalDateTime.parse("23:11:2015 15:15:15", formatter),Duration.ofHours(1));
 Activity test7 = new Activity ("walk",  "fridge", 0.001, LocalDateTime.parse("12:10:2013 09:00:00", formatter),Duration.ofHours(2));
  @Test
  public void testCreate()
  {DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mm:ss");
    assertEquals ("walk",          test.type);
    assertEquals ("fridge",        test.location);
    assertEquals (0.0001, 0.001,   test.distance); 
    assertEquals (LocalDateTime.parse("12:10:2013 09:00:00", formatter),   test.datetime);
    assertEquals (Duration.ofHours(1),   test.duration);
  }

  @Test
  public void testIds()
  {
    Set<Long> ids = new HashSet<>();
    for (Activity activity : activities)
    {
      ids.add(activity.id);
    }
    assertEquals (activities.length, ids.size());
  }

  @Test
  public void testToString()
  {
    assertEquals ("Activity{" + test.id + ", walk, fridge, 0.001, [], " +test.datetime+", "+test.duration+"}",test.toString());
  }
  @Test
  public void testHashcode(){
	 assertEquals(Objects.hashCode(test.id, test.type, test.location, test.distance,test.datetime,test.duration),test.hashCode());
  }
  @Test
  public void testEqual(){
	Location l =new Location(23.3f, 33.3f);
	test2.route.add(l);
	 assertEquals((test.datetime.equals(test2.datetime)&&(test.distance==(test2.distance)&&(test.duration.equals(test2.duration)&&(test.location.equals(test2.location)&&(test.route.equals(test2.route)&&(test.type.equals(test2.type))))))), test.equals(test2)); 
assertEquals((test.datetime.equals(test3.datetime)&&(test.distance==(test3.distance)&&(test.duration.equals(test3.duration)&&(test.location.equals(test3.location)&&(test.route.equals(test3.route)&&(test.type.equals(test3.type))))))), test.equals(test3)); 
assertEquals((test.datetime.equals(test4.datetime)&&(test.distance==(test4.distance)&&(test.duration.equals(test4.duration)&&(test.location.equals(test4.location)&&(test.route.equals(test4.route)&&(test.type.equals(test4.type))))))), test.equals(test4)); 
assertEquals((test.datetime.equals(test5.datetime)&&(test.distance==(test5.distance)&&(test.duration.equals(test5.duration)&&(test.location.equals(test5.location)&&(test.route.equals(test5.route)&&(test.type.equals(test5.type))))))), test.equals(test5)); 
assertEquals((test.datetime.equals(test6.datetime)&&(test.distance==(test6.distance)&&(test.duration.equals(test6.duration)&&(test.location.equals(test6.location)&&(test.route.equals(test6.route)&&(test.type.equals(test6.type))))))), test.equals(test6)); 
assertEquals((test.datetime.equals(test7.datetime)&&(test.distance==(test7.distance)&&(test.duration.equals(test7.duration)&&(test.location.equals(test7.location)&&(test.route.equals(test7.route)&&(test.type.equals(test7.type))))))), test.equals(test7)); 
assertEquals(test.route.equals(test3.distance), test.equals(test3));
assertEquals(test.distance==(test3.distance), false);
User homer = new User("homer", "simpson", "homer@simpson.com", "secret");
assertNotEquals(test.equals(homer), true); 
 }
 
}