package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    	for (PointFeature earthquake: earthquakes) {
	    		markers.add(createMarker(earthquake));
	    	}
	    
	    for(Marker marker: markers) {
	    	map.addMarker(marker);	    	
	    }

	}
		
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	// TODO: Implement this method and call it from setUp, if it helps
	private SimplePointMarker createMarker(PointFeature feature)
	{
		// finish implementing and use this method, if it helps.
		SimplePointMarker aMarker = new SimplePointMarker(feature.getLocation());
		if ((float) feature.getProperty("magnitude") <= 4) {
			aMarker.setRadius(5);
			int blue = color(0, 0, 255);
			aMarker.setColor(blue);
		}
		if ((float) feature.getProperty("magnitude") < 4.9 && (float) feature.getProperty("magnitude") > 4) {
			aMarker.setRadius(7);
			int yellow = color(255, 255, 0);
			aMarker.setColor(yellow);
		}
		if ((float) feature.getProperty("magnitude") >= 4.9) {
			aMarker.setRadius(10);
			int red = color(255, 0, 0);
			aMarker.setColor(red);
		}
		return aMarker;
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		fill(255, 255, 255);
		rect(25, 50, 150, 250);
		fill(0, 0, 0);
		textSize(18);
		text("Key", 85, 100);
		textSize(12);
		text("5+ Magnitude", 70, 150);
		text("4+ Magnitude", 70, 200);
		text("<4 Magnitude", 70, 250);
		fill(255, 0, 0);
		ellipse(50, 145, 20, 20);
		fill(255, 255, 0);
		ellipse(50, 195, 15, 15);
		fill(0 ,0, 255);
		ellipse(50, 245, 10, 10);
	}
}
