package com.example.itme.Main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.itme.R;
import com.example.itme.api.APIClient;
import com.example.itme.api.GoogleMapAPI;
import com.example.itme.entities.PlacesResults;
import com.example.itme.entities.Result;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentUserLocation;
    double latitude , longitude ;
    private static final int Request_user_location_code=99;
    List<Result> res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M)
        {
            checkUserLocationPermision();
        }
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
    public void onMapReady(GoogleMap googleMap) { //Créé la map, de google maps API
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(48.8134254,2.127839))); //initialise la position par défaut à la maison
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            AccessData("restaurant");  //On appelle la fonction pour accéder aux données et on lui met en paramètres qu'on veut les restaurants
        }


    }

    public boolean checkUserLocationPermision(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_user_location_code);
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_user_location_code);
            }
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case Request_user_location_code:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                        if(googleApiClient == null){
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }else{
                    Toast.makeText(this,"permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    protected synchronized void buildGoogleApiClient(){
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }
    @Override
    public void onLocationChanged(Location location) { //Va etre appelée systematiquement à chaque fois que la personne change de location

        lastLocation = location;
        if(currentUserLocation != null){
            currentUserLocation.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
      latitude = location.getLatitude();
      longitude  = location.getLongitude();
        //MarkerOptions markerOptions = new MarkerOptions();
        //markerOptions.position(latLng);
        // markerOptions.title("YOU");
        // markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        //currentUserLocation = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(14));

        if(googleApiClient != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
        AccessData("restaurant"); // On appelle la méthode pour avoir tous les restaurants autour de la postion actuelle de la personne
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void AccessData (String type)
    {
        GoogleMapAPI googlemapapi = APIClient.getClient().create(GoogleMapAPI.class);
            String currentLocation = latitude +  "," + longitude;
            int radius = 1000;
            String key = getText(R.string.google_maps_key).toString();

        googlemapapi.getNearBy(currentLocation, radius, type, key).enqueue(new Callback<PlacesResults>() {


            @Override
            public void onResponse(Call<PlacesResults> call, Response<PlacesResults> response) {
                res = response.body().getResults(); // On parcourt la liste, Renvoie une liste de tous les résultats (ici, restaurants), que google a enregistré dans sa base de données
                for (int i=0; i< res.size(); i++)
                {
                    //Afficher un markeur avec les latitudes et longitutes
                    double lat = res.get(i).getGeometry().getLocation().getLat();
                    double lng = res.get(i).getGeometry().getLocation().getLng();
                    MarkerOptions marker = new MarkerOptions();
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)); //Couleur du marker
                    LatLng latLng = new LatLng(lat, lng);
                    String nom = res.get(i).getName();
                    String placeId = res.get(i).getPlaceId();
                    String adress = res.get(i).getIcon();
//Photos photo = res.get(i).getPhotos().get(0);
//Boolean isOpen = res.get(i).getOpeningHours().getOpenNow();
                    marker.position(latLng);
                            marker.title(nom).snippet(adress);  //Le nom du marker prend le nom de restaurant
                            mMap.addMarker(marker);  //On place le marker sur la map


                            }
                            }

@Override
public void onFailure(Call<PlacesResults> call, Throwable t) {
        Log.d("erreur", "erreur con :" +t.getMessage());
        }
        });



        }

@Override
public void onInfoWindowClick(Marker marker) {
        for (int j=0; j<res.size(); j++){
        if(marker.getSnippet().equals(res.get(j).getId())){
        double lat = res.get(j).getGeometry().getLocation().getLat();
        double lng = res.get(j).getGeometry().getLocation().getLng();
        String name = res.get(j).getName();
        Double rating = res.get(j).getRating();

        Intent intent = new Intent(MapsActivity.this, Data.class);  //
        String user_name = "HEYYYY";
        intent.putExtra("name", name);
        startActivity(intent);
        }
        }
        }
        }