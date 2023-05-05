package com.leo_escobar.pruebafinal;

import androidx.annotation.NonNull;

import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.routing.CoreRouter;
import com.here.android.mpa.routing.RouteResult;
import com.here.android.mpa.routing.RoutingError;
import com.here.odnp.util.Log;

import java.util.List;

public class MapRoute implements CoreRouter.Listener{

    com.here.android.mpa.mapping.MapRoute mapRoute;
    private Map map = null;


    //metodo definido en Listener
    public void onProgress(int percentage){
        //mostrar progreso
        Log.d("RouteListener", "onProgress: " + percentage);
    }
    //metodo definido en Listener
    public void onCalculaterRouterFinished(List<RouteResult> routeResult, RoutingError error){
        //comprobar si hay error
        if(error == RoutingError.NONE){
            //obtener la ruta en el mapa
            mapRoute = new com.here.android.mpa.mapping.MapRoute(routeResult.get(0).getRoute());
            map.addMapObject(mapRoute);
        }
        else{
            Log.d("RouteListener", "onCalculaterRouterFinished: " + error.toString());
        }
    }

    @Override
    public void onCalculateRouteFinished(@NonNull List<RouteResult> list, @NonNull RoutingError routingError) {

    }
}