package models;

import static org.junit.Assert.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import static models.Fixtures.users;

public class UserTest {
	User homer = new User("homer", "simpson", "homer@simpson.com", "secret");
	User homer5 = new User("homer", "wassim", "homer@simpson.com", "secret");
	User homer6 = new User("homer", "simpson", "wassim@simpson.com", "secret");
	User homer7 = new User("homer", "simpson", "homer@simpson.com", "not");

	@Test
	public void testCreate() {
		assertEquals("homer", homer.firstName);
		assertEquals("simpson", homer.lastName);
		assertEquals("homer@simpson.com", homer.email);
		assertEquals("secret", homer.password);
		
	}

	@Test
	public void testIds() {
		Set<Long> ids = new HashSet<>();
		for (User user : users) {
			ids.add(user.id);
		}
		assertEquals(users.length, ids.size());
		
	}

	@Test
	public void testToString() {
		assertEquals("User{" + homer.id
				+ ", homer, simpson, secret, homer@simpson.com, {}}",
				homer.toString());
	}

	@Test
	public void Testequals() {
		final User homer2 = new User("homer", "simpson", "homer@simpson.com",
				"secret");
		
		assertEquals(((homer.firstName.equals(homer2.firstName))
				&& (homer.lastName.equals(homer2.lastName))
				&& (homer.activities.equals(homer2.activities))
				&& (homer.password.equals(homer2.password))
				&& (homer.email.equals(homer2.email))),homer.equals(homer2));
		assertEquals(((homer.firstName.equals(homer5.firstName))
				&& (homer.lastName.equals(homer5.lastName))
				&& (homer.activities.equals(homer5.activities))
				&& (homer.password.equals(homer5.password))
				&& (homer.email.equals(homer5.email))),homer.equals(homer5));
		assertEquals(((homer.firstName.equals(homer6.firstName))
				&& (homer.lastName.equals(homer6.lastName))
				&& (homer.activities.equals(homer6.activities))
				&& (homer.password.equals(homer6.password))
				&& (homer.email.equals(homer6.email))),homer.equals(homer6));
		assertEquals(((homer.firstName.equals(homer7.firstName))
				&& (homer.lastName.equals(homer7.lastName))
				&& (homer.activities.equals(homer7.activities))
				&& (homer.password.equals(homer7.password))
				&& (homer.email.equals(homer7.email))),homer.equals(homer7));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mm:ss");
		 Activity test = new Activity ("walk",  "fridge", 0.001, LocalDateTime.parse("12:10:2013 09:00:00", formatter),Duration.ofHours(1));
homer2.activities.put(test.id, test);
assertEquals(((homer.firstName.equals(homer2.firstName))
		&& (homer.lastName.equals(homer2.lastName))
		&& (homer.activities.equals(homer2.activities))
		&& (homer.password.equals(homer2.password))
		&& (homer.email.equals(homer2.email))),homer.equals(homer2));
		Location  one = new Location(23.3f, 33.3f);
		assertNotEquals(one.equals(homer2), true);

	}

}