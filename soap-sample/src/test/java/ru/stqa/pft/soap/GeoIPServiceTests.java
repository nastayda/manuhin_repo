package ru.stqa.pft.soap;

import net.webservicex.GeoIP;
import net.webservicex.GeoIPService;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

/**
 * Created by manuhin on 15.04.2016.
 */
public class GeoIPServiceTests {

  @Test
  public void testMyIP() {
    GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("82.198.171.178");
    assertEquals(geoIP.getCountryCode(), "RUS");

  }

  @Test
  public void testInvalidIP() {
    GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("82.198.171.xxx");
    assertEquals(geoIP.getCountryCode(), "RUS");

  }
}
