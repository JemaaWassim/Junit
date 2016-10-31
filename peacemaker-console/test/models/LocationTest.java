package models;

import static org.junit.Assert.*;
import static models.Fixtures.locations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Objects;

public class LocationTest
{
  private Location one;
  private Location two;
  private Location three;

  @Before
  public void setup()
  {
    one = new Location(23.3f, 33.3f);
    two = new Location(34.4f, 22.2f);
    three = new Location(23.3f, 30.3f);
  }

  @After
  public void tearDown()
  {
    one = two = three = null;
  }

  @Test
  public void testCreate()
  {
    assertEquals (0.01, 23.3, locations[0].latitude);
    assertEquals (0.01, 33.3, locations[0].longitude);
  }


  @Test
  public void testIds()
  {
    assertNotEquals(locations[0].id, locations[1].id);
  }

  @Test
  public void testToString()
  {
    assertEquals ("Location{" + locations[0].id + ", 23.3, 33.3}", locations[0].toString());
  }
  @Test
  public void testHashcode(){
	  assertEquals(Objects.hashCode(one.id, one.latitude, one.longitude), one.hashCode());
	  
  }
  @Test
  public void testEquals(){
	  User homer2 = new User("homer", "simpson", "homer@simpson.com",
				"secret");
	  assertTrue(one instanceof Location);
	  assertEquals((Objects.equal(one.latitude, two.latitude) 
	          && Objects.equal(one.longitude, two.longitude)),one.equals(two));
	  assertEquals((Objects.equal(one.latitude, three.latitude) 
	          && Objects.equal(one.longitude, three.longitude)),one.equals(three));
	  assertNotEquals(homer2.equals(one), true);
  }
}