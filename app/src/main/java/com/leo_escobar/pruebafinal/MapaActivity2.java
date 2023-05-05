package com.leo_escobar.pruebafinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.GeoPolyline;
import com.here.android.mpa.common.Image;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.ViewObject;
import com.here.android.mpa.mapping.AndroidXMapFragment;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapGesture;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapPolyline;
import com.here.android.mpa.mapping.MapRoute;
import com.here.android.mpa.mapping.MapScreenMarker;
import com.here.android.mpa.routing.CoreRouter;
import com.here.android.mpa.routing.RouteOptions;
import com.here.android.mpa.routing.RoutePlan;
import com.here.android.mpa.routing.RouteResult;
import com.here.android.mpa.routing.RouteWaypoint;
import com.here.android.mpa.routing.RoutingError;
import com.here.odnp.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapaActivity2 extends AppCompatActivity {
    private double latitude2;
    private double longitude2;
    private double latitude;
    private double longitude;
    private String street;
    private String neighborhood;
    //private int postalCode;
    private String postalCode1;
    private String city1;
    private String state1;
    private int idCliente;
    private List<GeoCoordinate> coordinatesList = new ArrayList<>();


    private GeoCoordinate mSelectedCoordinate;
    com.here.android.mpa.mapping.MapRoute mapRoute;


    private boolean mIsMapMoving = false;
    private MapMarker m_mapMarker;

    private String mAddressMarker2;




    private static Image m_markerImage;

    private MapScreenMarker m_tapMarker;



    private FusedLocationProviderClient mFusedLocationClient;


    // permissions request code

    /**
     * Permissions that need to be explicitly requested from end user.
     */


    // map embedded in the map fragment
    private Map map = null;

    // map fragment embedded in this activity
    private AndroidXMapFragment mapFragment = null;

    private MapMarker currentLocationMarker = null;

    private class RouteListener implements CoreRouter.Listener{
        //metodo definido en Listener
        public void onProgress(int percentage){
            //mostrar progreso
            Log.d("RouteListener", "onProgress: " + percentage);
        }
        //metodo definido en Listener
        public void onCalculateRouteFinished(List<RouteResult> routeResult, RoutingError error){
            //comprobar si hay error
            if(error == RoutingError.NONE){
                //obtener la ruta en el mapa

                mapRoute = new MapRoute(routeResult.get(0).getRoute());
                map.addMapObject(mapRoute);
            }
            else{
                Log.d("RouteListener", "onCalculateRouteFinished: " + error.toString());
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        initialize();

        //obtener el id del cliente del editarCliente

        idCliente = getIntent().getIntExtra("idCliente3", 0);

        //toast con el idCliente
        Toast.makeText(this, "idCliente: " + idCliente, Toast.LENGTH_SHORT).show();

        // boton de ubicacion actual
        Button actualLocationButton = findViewById(R.id.my_location_button);
        actualLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //codigo para obtener ubicacion actual y centrar el mapa en ella sin necesidad de crear un marcador
                if (ContextCompat.checkSelfPermission(MapaActivity2.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mFusedLocationClient.getLastLocation().addOnSuccessListener(MapaActivity2.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                GeoCoordinate geoCoordinate = new GeoCoordinate(location.getLatitude(), location.getLongitude());
                                map.setCenter(geoCoordinate, Map.Animation.NONE);
                                map.setZoomLevel(15);
                            }
                        }
                    });
                } else {
                    ActivityCompat.requestPermissions(MapaActivity2.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }
        });

        EditText searchEditText = findViewById(R.id.search_edittext);
        Button searchButton = findViewById(R.id.search_button);
        Button confirmButton = findViewById(R.id.aceptarUbicacion);

        //llamar al boton de aceptar ubicacion para mandarme a la actividad de CrearRutaActivity
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapaActivity2.this, editarCliente.class);
                //pasar las coordenadas en un putExtra
                intent.putExtra("latitud", latitude2);
                intent.putExtra("longitud", longitude2);
                intent.putExtra("calle", street);
                intent.putExtra("colonia", neighborhood);
                intent.putExtra("codigoPostal", postalCode1);
                intent.putExtra("ciudad", city1);
                intent.putExtra("estado", state1);

                //pasar el id del cliente a editarCliente
                intent.putExtra("idCliente2", idCliente);
                //log con el id del cliente
                Log.d("MapaActivity", "log 1: " + idCliente);
                //log con street y neighborhood
                //Log.d("MapaActivity", "log 2: " + street + " " + neighborhood);

                intent.putExtra("edicion","mapa");
                startActivity(intent);
            }
        });


    }

    //crear metodo para agregar la línea de ruta entre los 2 marcadores estaticos de momento
    private void addPolyline() {
        // Create a GeoPolyline with the coordinates list.
        GeoPolyline geoPolyline = new GeoPolyline(coordinatesList);
        // Create a MapPolyline with the GeoPolyline.
        MapPolyline mapPolyline = new MapPolyline(geoPolyline);
        // Set the width of the MapPolyline in pixels.
        mapPolyline.setLineWidth(10);
        // Add the MapPolyline to the map.
        map.addMapObject(mapPolyline);
    }
    private AndroidXMapFragment getMapFragment() {
        return (AndroidXMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapfragment);
    }

    @SuppressWarnings("deprecation")
    private void initialize() {
        //log de prueba
        Log.d("MapaActivity", "log 1: ");
        setContentView(R.layout.activity_mapa_activitty);

        // Search for the map fragment to finish setup by calling init().
        mapFragment = getMapFragment();

        // Use this method to specify custom map disk cache path.
        com.here.android.mpa.common.MapSettings.setDiskCacheRootPath(
                getApplicationContext().getExternalFilesDir(null) + File.separator + ".here-maps");

        // Initialize MapEngine
        mapFragment.init(new OnEngineInitListener() {
            @Override
            public void onEngineInitializationCompleted(
                    final OnEngineInitListener.Error error) {
                if (error == OnEngineInitListener.Error.NONE) {
                    // retrieve a reference of the map from the map fragment
                    map = mapFragment.getMap();
                    //log de prueba 2
                    Log.d("MapaActivity", "log 2: ");



                    mapFragment.getMapGesture().addOnGestureListener(new MapGesture.OnGestureListener(){

                        @Override
                        public void onPanStart() {
                            mIsMapMoving = true;
                        }

                        @Override
                        public void onPanEnd() {
                            mIsMapMoving = false;
                        }

                        @Override
                        public void onMultiFingerManipulationStart() {

                        }

                        @Override
                        public void onMultiFingerManipulationEnd() {

                        }

                        @Override
                        public boolean onMapObjectsSelected(List<ViewObject> list) {
                            return false;
                        }

                        @Override
                        public boolean onTapEvent(PointF pointF) {
                            //log de prueba
                            Log.d("onTapEvent", "log de prueba 3");
                            //toast de prueba

                            if(m_mapMarker != null){
                                map.removeMapObject(m_mapMarker);

                                // Eliminar la ruta anterior del mapa
                                if (mapRoute != null) {
                                    map.removeMapObject(mapRoute);
                                }

                                //eliminar trazos de ruta del RoutePlan
                                coordinatesList.clear();
                            }
                            // Crear un objeto de marcador
                            MapMarker marker = new MapMarker();
                            //implementar marcador cuando se toque el mapa
                            if(m_tapMarker ==null){

                                //obtener coordenadas
                                GeoCoordinate geoCoordinate = map.pixelToGeo(pointF);
                                mSelectedCoordinate = geoCoordinate;



                                latitude2 = geoCoordinate.getLatitude();
                                longitude2 = geoCoordinate.getLongitude();



                                //pasarle las coordenadas

                                //Agregar las coordenadas a la lista
                                coordinatesList.add(geoCoordinate);

                                //obtener las coordenadas del dispositivo actual y agregarla a una variable de latitud y otra de longitud
                                if (ContextCompat.checkSelfPermission(MapaActivity2.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                    mFusedLocationClient.getLastLocation().addOnSuccessListener(MapaActivity2.this, new OnSuccessListener<Location>() {
                                        @Override
                                        public void onSuccess(Location location) {
                                            //log de prueba
                                            Log.d("Coordenadas desde MapaActivity", "Hola?");



                                            if (location != null) {
                                                GeoCoordinate geoCoordinate = new GeoCoordinate(location.getLatitude(), location.getLongitude());
                                                latitude = geoCoordinate.getLatitude();
                                                longitude = geoCoordinate.getLongitude();

                                                //obtener la calle de la ubicación



                                                //Guardar las coordenadas en SharedPreferences
                                                SharedPreferences sharedPreferences = getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("latitud", String.valueOf(latitude2));
                                                editor.putString("longitud", String.valueOf(longitude2));

                                                //log con las coordenadas
                                                Log.d("Coordenadas desde MapaActivity", "Latitud: " + latitude2 + " Longitud: " + longitude2);




                                                //toast con las coordenadas
                                                //Toast.makeText(MapaActivity.this, "Latitud: " + latitude2 + " Longitud: " + longitude2, Toast.LENGTH_SHORT).show();


                                                //declarar a coreRouter
                                                CoreRouter router = new CoreRouter();

                                                //crear un routePlan y añadir 2 GeoCoordinate waypoints
                                                RoutePlan routePlan = new RoutePlan();
                                                routePlan.addWaypoint(new RouteWaypoint(new GeoCoordinate(latitude2, longitude2)));
                                                routePlan.addWaypoint(new RouteWaypoint(new GeoCoordinate(latitude, longitude)));

                                                //crear RouteOptions y poner el modo de transporte en coche y tipo
                                                RouteOptions routeOptions = new RouteOptions();
                                                routeOptions.setTransportMode(RouteOptions.TransportMode.CAR);
                                                routeOptions.setRouteType(RouteOptions.Type.FASTEST);
                                                routePlan.setRouteOptions(routeOptions);

                                                //calcular la ruta
                                                router.calculateRoute(routePlan,new MapaActivity2.RouteListener());

                                            }
                                        }
                                    });
                                } else {
                                    ActivityCompat.requestPermissions(MapaActivity2.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                                }

                                //obtener direccion
                                Geocoder geocoder = new Geocoder(MapaActivity2.this, Locale.getDefault());

                                List<Address> addresses = null;
                                try {
                                    addresses = geocoder.getFromLocation(geoCoordinate.getLatitude(), geoCoordinate.getLongitude(), 1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                String address = addresses.get(0).getAddressLine(0);
                                String city = addresses.get(0).getLocality();
                                String state = addresses.get(0).getAdminArea();
                                String country = addresses.get(0).getCountryName();
                                String postalCode = addresses.get(0).getPostalCode();
                                String knownName = addresses.get(0).getFeatureName();
                                String fullAddress = address + ", " + city + ", " + state + ", " + country + ", " + postalCode + ", " + knownName;
                                //agregar direccion al marcador
                                marker.setCoordinate(geoCoordinate);
                                marker.setTitle(fullAddress);

                                if(m_mapMarker != null){
                                    map.removeMapObject(m_mapMarker);
                                    //eliminar trazos de ruta del RoutePlan
                                    coordinatesList.clear();

                                }
                                map.addMapObject(marker);



                                //Actualizar textView del segundo marcador

                                //log de prueba
                                Log.d("Coordenadas desde MapaActivity 2", "Latitud: " + latitude2 + " Longitud: " + longitude2);
                                TextView textView = (TextView) findViewById(R.id.description_ubication_text_view2);
                                textView.setText(fullAddress);

                                //obtener la calle y colonia de la ubicación
                                street = addresses.get(0).getThoroughfare();
                                neighborhood = addresses.get(0).getSubLocality();
                                //obtener el postal code
                                postalCode1 = postalCode;
                                //obtener el estado
                                state1 = addresses.get(0).getAdminArea();
                                //obtener la ciudad
                                city1 = addresses.get(0).getLocality();
                                //log con la calle y colonia
                                //Log.d("Calle y colonia", "Calle: " + street + " Colonia: " + neighborhood);
                                //toast con la calle y colonia
                                //Toast.makeText(MapaActivity.this, "Calle: " + street + " Colonia: " + neighborhood, Toast.LENGTH_SHORT).show();
                                //Toast con el postal code
                                //Toast.makeText(MapaActivity.this, "Postal Code: " + postalCode1, Toast.LENGTH_SHORT).show();
                                m_mapMarker = marker;
                            }else{
                                m_tapMarker.setScreenCoordinate(pointF);
                                //remove marker

                            }

                            return false;
                        }

                        @Override
                        public boolean onDoubleTapEvent(PointF pointF) {
                            return false;
                        }

                        @Override
                        public void onPinchLocked() {

                        }

                        @Override
                        public boolean onPinchZoomEvent(float v, PointF pointF) {
                            return false;
                        }

                        @Override
                        public void onRotateLocked() {

                        }

                        @Override
                        public boolean onRotateEvent(float v) {
                            return false;
                        }

                        @Override
                        public boolean onTiltEvent(float v) {
                            return false;
                        }

                        @Override
                        public boolean onLongPressEvent(PointF pointF) {
                            return false;
                        }

                        @Override
                        public void onLongPressRelease() {

                        }

                        @Override
                        public boolean onTwoFingerTapEvent(PointF pointF) {
                            return false;
                        }

                    },0, false);
                    if(ContextCompat.checkSelfPermission(MapaActivity2.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    GeoCoordinate geoCoordinate = new GeoCoordinate(location.getLatitude(), location.getLongitude());
                                    map.setCenter(geoCoordinate, Map.Animation.NONE);

                                    //añadir la coordenada a la lista
                                    coordinatesList.add(geoCoordinate);

                                    //extraer la longitud y latitud a 6 decimales
                                    double latitude = Math.round(location.getLatitude() * 1000000.0) / 1000000.0;
                                    double longitude = Math.round(location.getLongitude() * 1000000.0) / 1000000.0;

                                    // Crear un objeto de marcador
                                    MapMarker marker = new MapMarker();

                                    // Establecer la posición del marcador  en la ubicación actual del usuario
                                    marker.setCoordinate(geoCoordinate);

                                    // Agregar el marcador al mapa
                                    map.addMapObject(marker);

                                    // Obtener la dirección actual
                                    Geocoder geocoder = new Geocoder(MapaActivity2.this, Locale.getDefault());
                                    try {
                                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                        if (addresses != null && !addresses.isEmpty()) {
                                            Address address = addresses.get(0);
                                            String street = address.getThoroughfare();
                                            String colony = address.getSubLocality();
                                            // Actualizar el texto del TextView con la calle y la colonia actuales
                                            TextView textView = findViewById(R.id.description_ubication_text_view);
                                            textView.setText(street + ", " + colony);
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                //toast  de prueba
                                //Toast.makeText(MapaActivity.this, "Ubicacion actual", Toast.LENGTH_SHORT).show();

                                //mostrar resultados de una  busqueda de prueba en un toast

                            }
                        });
                    }
                    //establecer el zoom en la posicion actual y exacta
                    map.setZoomLevel(15);


                } else {
                    System.out.println("ERROR: Cannot initialize Map Fragment");

                    runOnUiThread(new Runnable() {
                        @Override public void run() {
                            new AlertDialog.Builder(MapaActivity2.this).setMessage(
                                            "Error : " + error.name() + "\n\n" + error.getDetails())
                                    .setTitle("Este es un titulo XD")
                                    .setNegativeButton(android.R.string.cancel,
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int which) {
                                                    finishAffinity();
                                                }
                                            }).create().show();
                        }
                    });
                }
            }
        });
    }


    /**
     * Checks the dynamically controlled permissions and requests missing permissions from end user.
     */

}
