package module6;

import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routes;
	
	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
	
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.image(AirportMap.airImage, x-7, y-7, 15, 15);
		
		
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		 // show rectangle with title
		String name = this.getName();
		String country = this.getCountry();
		String city = this.getCity();
		String code = this.getCode();
		String title1 = "Name:" + name+"; City: "+city;
		String title2 = "Country: "+country+"; Code "+code;
		pg.pushStyle();
			
		pg.fill(255, 255, 255);
		pg.textSize(12);
		pg.rectMode(PConstants.CORNER);
		pg.rect(x, y, Math.max(pg.textWidth(title1), pg.textWidth(title2))+5, 32);
		pg.fill(0, 0, 0);
		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		
		pg.text(title1, x+3, y+2);
		pg.text(title2, x+3, y+16);
		pg.popStyle();
		// show routes
		
		
	}
	
	public void showRoutes(PGraphics pg, float x, float y) {
		
	}
	
	public String getName() {
		return getStringProperty("name");
	}
	
	public String getCity() {
		return getStringProperty("city");
	}
	
	public String getCountry() {
		return getStringProperty("country");
	}
	
	public String getCode() {
		return getStringProperty("code");
	}
}
