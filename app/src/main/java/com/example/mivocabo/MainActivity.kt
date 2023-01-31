package com.example.mivocabo

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.icu.text.ListFormatter.Width
import android.os.Bundle
import android.os.Looper
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.ColorRes
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.mivocabo.ui.theme.MIvocaboTheme
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.jar.Attributes
import java.util.jar.Manifest

class MainActivity : ComponentActivity(), OnMapReadyCallback {
    lateinit var mapView: MapView
    lateinit var latlang:LatLng
    var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MIvocaboTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black //MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                    mapView.onCreate(savedInstanceState)
                    mapView.getMapAsync(this)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        locationCallback?.let {
            val locationRequest =
                LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, 1000)
            locationRequest.setDurationMillis(5000)
            fusedLocationClient?.requestLocationUpdates(
                locationRequest.build(),
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
        p0.setIndoorEnabled(true)
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            p0.isMyLocationEnabled = true
        p0.addMarker()


    }
}

val locationCallback: LocationCallback = object : LocationCallback() {
    override fun onLocationAvailability(p0: LocationAvailability) {
        super.onLocationAvailability(p0)
    }

    override fun onLocationResult(p0: LocationResult) {
        super.onLocationResult(p0)
    }
}

@Composable
fun Greeting(name: String) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.black))
            .padding(16.dp)
    ) {
        Text(
            "Hello $name!", color = Color.White,

            modifier = Modifier
                .layoutId("mytext")
        )
        MyButton(text = "Bul")
        Row(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .background(colorResource(id = R.color.purple_200))
        ) {
            Text(
                "Helloqw $name!", color = Color.White,

                modifier = Modifier
                    .layoutId("mytext1")
            )
            MyButton(text = "Bul")
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            val singapore = LatLng(1.35, 103.87)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(singapore, 10f)
            }

            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
                    .layoutId("localmap"),
                cameraPositionState = cameraPositionState

            ) {
                Marker(
                    state = MarkerState(position = singapore),
                    title = "Singapore",
                    snippet = "Marker in Singapore"
                )
            }
        }
    }

}

@Composable
fun MyButton(text: String, modifier: Modifier = Modifier) {
    Button(
        onClick = { },
        modifier = modifier
    ) {
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MIvocaboTheme {
        Greeting("Android")
    }
}