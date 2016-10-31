package models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Fixtures
{public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mm:ss");

  public static User[] users =
  {
    new User ("marge", "simpson", "marge@simpson.com",  "secret"),
    new User ("lisa",  "simpson", "lisa@simpson.com",   "secret"),
    new User ("bart",  "simpson", "bart@simpson.com",   "secret"),
    new User ("maggie","simpson", "maggie@simpson.com", "secret")
  };

  public static Activity[] activities =
  {
    new Activity ("cycle",  "fridgeee", 0.001,LocalDateTime.parse("13:10:2013 09:00:00", formatter),Duration.ofHours(5)),
    new Activity ("r",  "bar",    1.0,LocalDateTime.parse("12:10:2013 09:00:00", formatter),Duration.ofHours(2)),
    new Activity ("run",   "work",   0.2,LocalDateTime.parse("12:10:2013 19:00:00", formatter),Duration.ofHours(4)),
    new Activity ("gooooo",  "shop",   2.5,LocalDateTime.parse("12:10:2013 09:00:00", formatter),Duration.ofHours(3)),
    new Activity ("cycle", "school", 4.5,LocalDateTime.parse("12:10:2013 09:00:00", formatter),Duration.ofHours(1))
  };

  public static Location[]locations =
  {
    new Location(23.3f, 33.3f),
    new Location(34.4f, 45.2f),  
    new Location(25.3f, 34.3f),
    new Location(44.4f, 23.3f)       
  };
}