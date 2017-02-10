package com.cetinje.bozo.cetinjevodic;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.views.MapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.PathOverlay;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import android.Manifest;
import android.widget.Toast;

import java.util.ArrayList;


public class MapActivity extends Activity  implements MapEventsReceiver{
    private MapView mMapView;
    private MapController mMapController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mMapView = (org.osmdroid.views.MapView) findViewById(R.id.map);
        //Trazenje dozvole da se pristupi EXTERNAL STORAGE-u
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        }
        // Setovanje izvora offline mape (zipovani fajl se stavlja u folder osmdroid u telefonu)
        mMapView.setTileSource(new XYTileSource("MapquestOSM", // ime foldera u zipovanom fajlu,
                16, 19, 256, ".png", new String[] { // minimalni zoom, maximalni zoom, format slike
                "http://otile1.mqcdn.com/tiles/1.0.0/map/",
                "http://otile2.mqcdn.com/tiles/1.0.0/map/",
                "http://otile3.mqcdn.com/tiles/1.0.0/map/",
                "http://otile4.mqcdn.com/tiles/1.0.0/map/" }));
        mMapView.setClickable(true);
        //mMapView.setBuiltInZoomControls(true); // Dugmad + - za zoom
        // Omogucavanje okretanja mape pomocu multitouch-a
        RotationGestureOverlay mRotationGestureOverlay = new RotationGestureOverlay(this.getApplicationContext(), mMapView);
        mRotationGestureOverlay.setEnabled(true);
        mMapView.setMultiTouchControls(true);
        mMapView.getOverlays().add(mRotationGestureOverlay);
        //Postavljanje pocetnog zoom-a pomocu kontrolera
        mMapController = (org.osmdroid.views.MapController) mMapView.getController();
        mMapController.setZoom(13);
        //Postavljanje pocetne pozicije mape
        float lat = 42.393096f; float lng = 18.911596f;
        GeoPoint gPt = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6)); //Konvertovanje longitude i latitude u decimalni zapis
        mMapController.setCenter(gPt);

        //Postavljanje okvira mapi (da se e bi moglo da se ide van offline regiona mape)
        double north = 42.407995;
        double east  =  18.951674;
        double south = 42.377124;
        double west  =  18.900776;
        BoundingBoxE6 bBox = new BoundingBoxE6(north, east, south, west);
        mMapView.setScrollableAreaLimit(bBox);
        mMapView.setUseDataConnection(false);
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(this, (MapEventsReceiver) this);
        mMapView.getOverlays().add(0, mapEventsOverlay);


        float lat1 = 42.394237f, lng1 = 18.916687f, lat2 = 42.396598f, lng2 = 18.911923f, lat3 = 42.398341f, lng3 = 18.913575f, lat4 = 42.398088f, lng4 = 18.920549f;
        GeoPoint gPt1 = new GeoPoint((int)(lat1 * 1E6), (int)(lng1 * 1E6));
        GeoPoint gPt2 = new GeoPoint((int)(lat2 * 1E6), (int)(lng2 * 1E6));
        GeoPoint gPt3 = new GeoPoint((int)(lat3 * 1E6), (int)(lng3 * 1E6));
        GeoPoint gPt4 = new GeoPoint((int)(lat4 * 1E6), (int)(lng4 * 1E6));
        ArrayList<GeoPoint> gPts = new ArrayList<GeoPoint>();
        gPts.add(gPt);
        gPts.add(gPt1);
        gPts.add(gPt2);
        gPts.add(gPt3);
        gPts.add(gPt4);
        Polyline pathOverlay = null;
        pathOverlay = new Polyline(this.getApplicationContext());
        pathOverlay.setPoints(gPts);
        pathOverlay.setColor(R.color.colorAccent);
        mMapView.getOverlays().add(pathOverlay);
        // Create an ArrayList with overlays to display objects on map
        ArrayList<OverlayItem> overlayItemArray = new ArrayList<OverlayItem>();
        // Create som init objects
        OverlayItem cetinjeMarker1 = new OverlayItem("Start", "Montenegro",
                gPt);
        OverlayItem cetinjeMarker2 = new OverlayItem("Prva lokacija", "Ime rute",
                gPt1);
        OverlayItem cetinjeMarker3 = new OverlayItem("Druga lokacija", "Ime rute",
                gPt2);
        OverlayItem cetinjeMarker4 = new OverlayItem("Treca lokacija", "Ime rute",
                gPt3);
        OverlayItem cetinjeMarker5 = new OverlayItem("Cetvrta lokacija", "Ime rute",
                gPt4);
        overlayItemArray.add(cetinjeMarker1);
        overlayItemArray.add(cetinjeMarker2);
        overlayItemArray.add(cetinjeMarker3);
        overlayItemArray.add(cetinjeMarker4);
        overlayItemArray.add(cetinjeMarker5);
        Drawable newMarker = this.getResources().getDrawable(R.drawable.cetinje_marker);
        cetinjeMarker1.setMarker(newMarker);
        ItemizedIconOverlay<OverlayItem> itemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(this, overlayItemArray, null);
        // Add the overlay to the MapView
        mMapView.getOverlays().add(itemizedIconOverlay);

    }
    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
        //Funkcija kada se jednom kratko pritisne na mapu
        Toast.makeText(this, "Tap on ("+p.getLatitude()+","+p.getLongitude()+")", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean longPressHelper(GeoPoint p) {
        //Funkcija kada se dugo pritisne na mapu
        return false;
    }

    @Override
    public void onBackPressed() {
        //Kod za unistavanje svih aktivitija osim MainActivity-a
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}
