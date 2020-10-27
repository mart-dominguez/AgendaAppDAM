package isi.dam.agendaappdam;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    Button btnMapa;
    TextView tvMapa;
    final LatLng PARQUE_FEDERAL = new LatLng(-31.620553, -60.696278);
    final LatLng PARQUE_INDEPENDENCIA = new LatLng(-32.959106, -60.661168);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        btnMapa = findViewById(R.id.btnMapa);
        tvMapa = findViewById(R.id.tvMapa);

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                CameraPosition posicion = new CameraPosition.Builder()
                        .target(PARQUE_FEDERAL)
                        .zoom(14)
                        .bearing(90)
                        .tilt(15)
                        .build();

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(posicion),10000,null);
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    9999);
            return;
        }
        organizarMapa();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==9999 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            organizarMapa();
        }
    }

    @SuppressLint("MissingPermission")
    private void organizarMapa(){
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setTrafficEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PARQUE_INDEPENDENCIA, 16));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                tvMapa.setText("Marcador id "+marker.getId());
                marker.setVisible(false);
                return false;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                tvMapa.setText("CLICK EN "+latLng.latitude + " - "+latLng.longitude);
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                tvMapa.setText("CLICK LARGO EN "+latLng.latitude + " - "+latLng.longitude);
                MarkerOptions marcador = new MarkerOptions()
                        .alpha(0.5f)
                        .position(latLng)
                        .draggable(true)
                        .title("TITULO")
                        .snippet("SNIPET hola 2 asasdfasdf");
                mMap.addMarker(marcador);

            }
        });
        puntos();
    }
    private class PointsKeywords {
        public List<LatLng> points;
        public String keyword;

        public PointsKeywords(List<LatLng> points, String keyword) {
            this.points = points;
            this.keyword = keyword;
        }
    }

    private HashMap<String, TileOverlay> mOverlays = new HashMap<String, TileOverlay>();

    private void puntos(){
        List<LatLng> points = new ArrayList<>();
        // Check that it wasn't an empty query.
        for(int i=0;i<1000;i++){
            points.add(getRandomLocation(new LatLng(-31.632307, -60.792245),5000));
        }

        if (!points.isEmpty()) {
            if (mOverlays.size() < 5) {
                HeatmapTileProvider provider = new HeatmapTileProvider.Builder()
                        .data(points)
                        .gradient(makeGradient(HEATMAP_COLORS[1]))
                        .build();
                TileOverlay overlay = this.mMap.addTileOverlay(new TileOverlayOptions().tileProvider(provider));
                //mOverlays.put(keyword, overlay);
            }
        }
    }

    private Gradient makeGradient(int color) {
        int[] colors = {color};
        float[] startPoints = {1.0f};
        return new Gradient(colors, startPoints);
    }

    public enum HeatmapColors {
        RED (Color.rgb(238, 44, 44)),
        BLUE (Color.rgb(60, 80, 255)),
        GREEN (Color.rgb(20, 170, 50)),
        PINK (Color.rgb(255, 80, 255)),
        GREY (Color.rgb(100, 100, 100));

        private final int color;
        HeatmapColors(int color) {
            this.color = color;
        }
    }

    private static final int[] HEATMAP_COLORS = {
            HeatmapColors.RED.color,
            HeatmapColors.BLUE.color,
            HeatmapColors.GREEN.color,
            HeatmapColors.PINK.color,
            HeatmapColors.GREY.color
    };

    public LatLng getRandomLocation(LatLng point, int radius) {

        List<LatLng> randomPoints = new ArrayList<>();
        List<Float> randomDistances = new ArrayList<>();


        //This is to generate 10 random points
            double x0 = point.latitude;
            double y0 = point.longitude;

            Random random = new Random();

            // Convert radius from meters to degrees
            double radiusInDegrees = radius / 111000f;

            double u = random.nextDouble();
            double v = random.nextDouble();
            double w = radiusInDegrees * Math.sqrt(u);
            double t = 2 * Math.PI * v;
            double x = w * Math.cos(t);
            double y = w * Math.sin(t);

            // Adjust the x-coordinate for the shrinking of the east-west distances
            double new_x = x / Math.cos(y0);

            double foundLatitude = new_x + x0;
            double foundLongitude = y + y0;
            return new LatLng(foundLatitude, foundLongitude);
    }
}