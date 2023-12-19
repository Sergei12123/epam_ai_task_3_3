package com.example.epam_ai_task_3_3.service;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GoogleMapsService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    public DirectionsResult getRouteToNearestHospital(String userAddress) {
        try {
            GeoApiContext context = new GeoApiContext.Builder().apiKey(apiKey).build();

            // Use the Geocoding API to get the coordinates of the user's address
            GeocodingResult[] geocodingResults = GeocodingApi.geocode(context, userAddress).await();
            LatLng userLocation = geocodingResults[0].geometry.location;

            // Use the Places API to search for hospitals near the user's location
            PlacesSearchResponse placesSearchResponse = PlacesApi.nearbySearchQuery(context, userLocation)
                    .type(PlaceType.HOSPITAL)
                    .rankby(RankBy.DISTANCE)
                    .await();

            // Get details of the nearest hospital
            PlacesSearchResult nearestHospital = placesSearchResponse.results[0];
            LatLng destination = nearestHospital.geometry.location;

            // Use the Directions API to get the route from the user's location to the nearest hospital
            DirectionsResult directionsResult = DirectionsApi.newRequest(context)
                    .origin(userAddress)
                    .destination(destination.toString())
                    .mode(TravelMode.DRIVING)
                    .await();

            return directionsResult;
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

