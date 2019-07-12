package com.app.oniontray.Utils;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Next Brain on 28-01-2017.
 */

public class DirectionsJSONParser {


    public String duration = "";

    /**
     * Receives a JSONObject and returns a list of lists containing latitude and longitude
     */
    public List<List<HashMap<String, String>>> parse(JSONObject jObject) {

        List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
        JSONArray jRoutes = null;
        JSONArray jLegs = null;
        JSONArray jSteps = null;
        JSONObject jDura = null;


        //    {"geocoded_waypoints":[{"geocoder_status":"OK","place_id":"ChIJVWl1_BIVrjsRJ9UfsdU4n2Y",
// "types":["premise"]},{"geocoder_status":"OK","place_id":"ChIJVWl1_BIVrjsRJ9UfsdU4n2Y",
// "types":["premise"]}],"routes":[{"bounds":{"northeast":{"lat":12.9078438,"lng":77.5869609},
// "southwest":{"lat":12.9078436,"lng":77.58695329999999}},"copyrights":"Map data ©2017 Google",
// "legs":[{"distance":{"text":"1 m","value":1},"duration":{"text":"1 min","value":0},
// "end_address":"Pramara Niwas, 22nd Main Rd, Jeewan Griha Colony, 2nd Phase, JP Nagar, Bengaluru, Karnataka 560078, India",
// "end_location":{"lat":12.9078438,"lng":77.5869609},"start_address":"Pramara Niwas, 22nd Main Rd, Jeewan Griha Colony, 2nd Phase, JP Nagar,
// Bengaluru, Karnataka 560078, India","start_location":{"lat":12.9078436,"lng":77.58695329999999},
// "steps":[{"distance":{"text":"1 m","value":1},
// "duration":{"text":"1 min","value":0},"end_location":{"lat":12.9078438,"lng":77.5869609},
// "html_instructions":"Head <b>east<\/b> on <b>12th B Cross Rd<\/b>","polyline":{"points":"_axmAmupxM?A"},
// "start_location":{"lat":12.9078436,"lng":77.58695329999999},"travel_mode":"DRIVING"}],
// "traffic_speed_entry":[],"via_waypoint":[]}],"overview_polyline":{"points":"_axmAmupxM?A"},
// "summary":"12th B Cross Rd","warnings":[],"waypoint_order":[]}],"status":"OK"}


        try {

            jRoutes = jObject.getJSONArray("routes");

            /** Traversing all routes */
            for (int i = 0; i < jRoutes.length(); i++) {
                jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                List path = new ArrayList<HashMap<String, String>>();

                /** Traversing all legs */
                for (int j = 0; j < jLegs.length(); j++) {

                    jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                    jDura = ((JSONObject) jLegs.get(j)).getJSONObject("duration");

                    Log.e("duration", "-" + jDura);

                    duration = jDura.getString("text");

                    Log.e("duration", "-" + duration);

                    /** Traversing all steps */
                    for (int k = 0; k < jSteps.length(); k++) {
                        String polyline = "";
                        polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
                        List<LatLng> list = decodePoly(polyline);

                        /** Traversing all points */
                        for (int l = 0; l < list.size(); l++) {
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("lat", Double.toString(((LatLng) list.get(l)).latitude));
                            hm.put("lng", Double.toString(((LatLng) list.get(l)).longitude));
                            path.add(hm);
                        }
                    }
                    routes.add(path);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }

        return routes;
    }

    /**
     * Method to decode polyline points
     * Courtesy : http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     */
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }


}