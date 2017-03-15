package com.cetinje.bozo.cetinjevodic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.views.MapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import android.support.v4.app.ActivityCompat;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;


public class MapActivity extends Activity implements MapEventsReceiver {
    private MapView mMapView;
    private MapController mMapController;
    private VideoView vVideoView;
    private DatabaseHelper db;
    private ArrayList<Tour> tours;
    private ArrayList<Path> paths;
    private ArrayList<CulturalHeritage> cultural_heritages;

    public static final String BarcodeObject = "Barcode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mMapView = (org.osmdroid.views.MapView) findViewById(R.id.map);
        vVideoView = (VideoView) findViewById(R.id.videoView);
        db = new DatabaseHelper(getApplicationContext());
        // Setovanje izvora offline mape (zipovani fajl se stavlja u folder osmdroid u telefonu)
        mMapView.setTileSource(new XYTileSource("MapquestOSM", // ime foldera u zipovanom fajlu,
                16, 19, 256, ".png", new String[]{ // minimalni zoom, maximalni zoom, format slike
                "http://otile1.mqcdn.com/tiles/1.0.0/map/",
                "http://otile2.mqcdn.com/tiles/1.0.0/map/",
                "http://otile3.mqcdn.com/tiles/1.0.0/map/",
                "http://otile4.mqcdn.com/tiles/1.0.0/map/"}));
        mMapView.setClickable(true);

        // Omogucavanje okretanja mape pomocu multitouch-a
        RotationGestureOverlay mRotationGestureOverlay = new RotationGestureOverlay(this.getApplicationContext(), mMapView);
        mRotationGestureOverlay.setEnabled(true);
        mMapView.setMultiTouchControls(true);
        mMapView.getOverlays().add(mRotationGestureOverlay);

        //Postavljanje pocetnog zoom-a pomocu kontrolera
        mMapController = (org.osmdroid.views.MapController) mMapView.getController();
        mMapController.setZoom(16);

        //Postavljanje okvira mapi (da se e bi moglo da se ide van offline regiona mape)
        double north = 42.407995;
        double east = 18.951674;
        double south = 42.377124;
        double west = 18.900776;
        BoundingBoxE6 bBox = new BoundingBoxE6(north, east, south, west);
        mMapView.setScrollableAreaLimit(bBox);
        mMapView.setUseDataConnection(false);
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(this, (MapEventsReceiver) this);
        mMapView.getOverlays().add(0, mapEventsOverlay);

        //paths = new ArrayList<Path>();
        ArrayList<GeoPoint> gPts = new ArrayList<GeoPoint>(); // tacke Rute koje ce da se povlace iz baze
        ArrayList<OverlayItem> overlayItemArray = new ArrayList<OverlayItem>(); // Kreiranje niza markera na mapi
        Drawable newMarker = this.getResources().getDrawable(R.drawable.cetinje_marker);
        paths = db.getAllPaths(); cultural_heritages = db.getAllCulturalHeritages(); tours = db.getAllTours();
        for(int i = 0; i < paths.size(); i++)
        {
            GeoPoint gPt = new GeoPoint((int) (paths.get(i).getLat() * 1E6), (int) (paths.get(i).getLng() * 1E6));
            if(i == 0)
            {
                mMapController.setCenter(gPt);
                OverlayItem marker = new OverlayItem(tours.get(0).getName(), "Start point.", gPt);
                marker.setMarker(newMarker);
                overlayItemArray.add(marker);
            }
            gPts.add(gPt);
        }
        Polyline pathOverlay = new Polyline(this.getApplicationContext());
        pathOverlay.setPoints(gPts);
        pathOverlay.setColor(R.color.colorAccent);
        mMapView.getOverlays().add(pathOverlay);
        for(int i = 0; i < cultural_heritages.size(); i++)
        {
            GeoPoint gPt = new GeoPoint((int) (cultural_heritages.get(i).getLat() * 1E6), (int) (cultural_heritages.get(i).getLng() * 1E6));
            OverlayItem marker = new OverlayItem(tours.get(0).getName(), cultural_heritages.get(i).getName(), gPt);
            marker.setMarker(newMarker);
            overlayItemArray.add(marker);
        }
        MyOwnItemizedOverlay itemizedIconOverlay = new MyOwnItemizedOverlay(this, overlayItemArray); // kada se klikne na marker podesavanje u novoj klasi
        // Postavljanje overlay-a na MAPU
        mMapView.getOverlays().add(itemizedIconOverlay);


        //final ArrayList<Uri> Uris = new ArrayList<Uri>(); // URL-ovi videova koji se ucitavaju iz baze
        //Rad sa VIDEOM
        final MediaController mediaController = new MediaController(this, false);// drugi parametar gasi dugmad za premotavanje
        mediaController.setAnchorView(vVideoView);
        vVideoView.setMediaController(mediaController);
        //Podesavanje video player-a kako bi se onemogucilo da se premotava unazad pomocu seekbara
        vVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                int topContainerId = getResources().getIdentifier("mediacontroller_progress", "id", "android");
                SeekBar seekBarVideo = (SeekBar) mediaController.findViewById(topContainerId);
                seekBarVideo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        seekBar.setEnabled(false);
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        seekBar.setEnabled(false);
                    }
                });
            }
        });
        vVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                //mediaController.setEnabled(false);
                //startOtherVid(Uris);
            }
        });
        //Uri uri=Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media/name_of_file.mp4"); // ucitavanje videa iz storage-a
        File file = getApplicationContext().getFileStreamPath("muzej_kralja_nikole.mp4");
        //Toast.makeText(applicationContext, String.valueOf(file), Toast.LENGTH_LONG).show();
        Uri uri = Uri.parse(String.valueOf(file));
        vVideoView.setVideoURI(uri);
        vVideoView.requestFocus();
        vVideoView.start();
        final LocationListener mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                //Dio koda dje se mijenja lokacija i provjerava da li smo u okolino objekta
                //Toast.makeText(MapActivity.this, "Tap on ("+location.getLatitude()+","+location.getLongitude()+")", Toast.LENGTH_SHORT).show();
                float startLatitude = (float) location.getLatitude(), startLongitude = (float) location.getLongitude();
                Location currentLocation = new Location("Current Location"), objectLocation = new Location("Object Location");
                currentLocation.setLatitude(startLatitude); currentLocation.setLongitude(startLongitude);
                double min_distance = Double.POSITIVE_INFINITY;
                CulturalHeritage closest_cultural_heritage = new CulturalHeritage();
                for(int i = 0; i < cultural_heritages.size(); i++)
                {
                    objectLocation.setLatitude(cultural_heritages.get(i).getLat()); objectLocation.setLongitude(cultural_heritages.get(i).getLng());
                    double distance  = currentLocation.distanceTo(objectLocation);
                    //Toast.makeText(MapActivity.this, String.valueOf(distance), Toast.LENGTH_SHORT).show();
                    if(min_distance > distance)
                    {
                        min_distance = distance;
                        closest_cultural_heritage = cultural_heritages.get(i);
                    }
                }
                //Toast.makeText(MapActivity.this, closest_cultural_heritage.getName() + ": " + String.valueOf(min_distance), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        try {
            LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 20, mLocationListener);
        }
        catch (Exception ex) {
            //do something useful here
        }
    }

    //funkcija koja pokrece video i brise elemente iz liste URI-a
    private void startOtherVid(ArrayList<Uri> Uris){
        vVideoView.stopPlayback();
        if(Uris.size() > 0) {
            Uri videoUri = Uris.get(0);
            Uris.remove(0);
            vVideoView.setVideoURI(videoUri);
            vVideoView.start();
        }
    }

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
        //Funkcija kada se jednom kratko pritisne na mapu
        //Toast.makeText(this, "Tap on ("+p.getLatitude()+","+p.getLongitude()+")", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean longPressHelper(GeoPoint p) {
        //Funkcija kada se dugo pritisne na mapu
        return false;
    }
    @Override
    public void onBackPressed() {
        exitByBackKey();
    }

    //Funkcija kada zelimo da izadjemo iz rute
    protected void exitByBackKey() {
        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Ukoliko izadjete iz rute morate poceti iz pocetka!") //Ovdje ide poruka
                .setPositiveButton("Izadji", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        //Kod za unistavanje svih aktivitija osim MainActivity-a
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT", true);
                        intent.putExtra(BarcodeObject, "0");
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Vrati se", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
    }

}
