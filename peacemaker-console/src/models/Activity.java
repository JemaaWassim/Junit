package models;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.common.base.Objects;

public class Activity 
{ 
  static Long   counter = 0l;

  public Long   id;

  public String type;
  public String location;
  public double distance;
  public LocalDateTime datetime ;
  public Duration duration ;

  public List<Location> route = new ArrayList<>();

 
  /*
  @Override
  public int compareTo( Activity o2) {
      // write comparison logic here like below , it's just a sample
      return (this.type.compareTo(o2.type));
  }*/
  public Activity(String type, String location, double distance, LocalDateTime datetime,Duration duration)
  {
    this.id        = counter++;
    this.type      = type;
    this.location  = location;
    this.distance  = distance;
    this.datetime =datetime;
    this.duration= duration ;
  }


  @Override
  public String toString()
  {
    return toStringHelper(this).addValue(id)
                               .addValue(type)
                               .addValue(location)
                               .addValue(distance)
                               .addValue(route)
                                .addValue(datetime)
                                 .addValue(duration)
                               .toString();
  }

  @Override  
  public int hashCode()  
  {  
     return Objects.hashCode(this.id, this.type, this.location, this.distance,this.datetime,this.duration);  
  }
  @Override
  public boolean equals(final Object obj)
  {
    if (obj instanceof Activity)
    {
      final Activity other = (Activity) obj;
      return Objects.equal(type, other.type) 
          && Objects.equal(location,  other.location)
          && Objects.equal(distance,  other.distance)
          && Objects.equal(route,     other.route)
           && Objects.equal(datetime,     other.datetime)
            && Objects.equal(duration,     other.duration);   
      
    }
    else
    {
      return false;
    }
  }
  
}