package com.cetinje.bozo.cetinjevodic;

import android.app.Activity;
import android.os.Bundle;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.views.MapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;

public class MapActivity extends Activity {
    private MapView mMapView;
    private MapController mMapController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mMapView = (org.osmdroid.views.MapView) findViewById(R.id.map);
        mMapView.setTileSource(new XYTileSource("MapquestOSM",
                15, 16, 256, ".png", new String[] {
                "http://otile1.mqcdn.com/tiles/1.0.0/map/",
                "http://otile2.mqcdn.com/tiles/1.0.0/map/",
                "http://otile3.mqcdn.com/tiles/1.0.0/map/",
                "http://otile4.mqcdn.com/tiles/1.0.0/map/" }));
        mMapView.setClickable(true);
        //mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);
        mMapController = (org.osmdroid.views.MapController) mMapView.getController();
        mMapController.setZoom(13);
        float lat = 42.393096f; float lng = 18.911596f; // Koordinate Cetinja
        GeoPoint gPt = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6)); // Setovanje početne tačke
        mMapController.setCenter(gPt);
        double north = 42.409389;
        double east  =  18.940945;
        double south = 42.375793;
        double west  =  18.887901;
        BoundingBoxE6 bBox = new BoundingBoxE6(north, east, south, west);

        mMapView.setScrollableAreaLimit(bBox);
        mMapView.setUseDataConnection(false);
    }
}
