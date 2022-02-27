package fr.epsi.ted_app_2

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MapsFragment : Fragment() {

    lateinit var googleMap :GoogleMap

    companion object {
        val stores = arrayListOf<Store>()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("MissingPermission")
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
                googleMap.isMyLocationEnabled=true
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
                googleMap.isMyLocationEnabled=true
            } else -> {
            // No location access granted.
        }
        }
    }

    val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()
    val mRequestURL = "https://djemam.com/epsi/stores.json"
    val request = Request.Builder()
        .url(mRequestURL)
        .get()
        .cacheControl(CacheControl.FORCE_NETWORK)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("error", e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val data = response.body?.string()

                if (data != null) {
                    val jsStores = JSONObject(data)
                    val jsArrayStores = jsStores.getJSONArray("stores")
                    for (i in 0 until jsArrayStores.length()) {
                        val jsStore = jsArrayStores.getJSONObject(i)
                        val store = Store(
                            storeId = jsStore.optInt("storeId", 0),
                            name = jsStore.optString("name", ""),
                            description = jsStore.optString("description", ""),
                            pictureStore = jsStore.optString("pictureStore", ""),
                            address = jsStore.optString("address", ""),
                            zipCode = jsStore.optString("zipcode", ""),
                            city = jsStore.optString("city", ""),
                            longitude = jsStore.optDouble("longitude", 0.0),
                            latitude = jsStore.optDouble("latitude", 0.0)
                        )
                        stores.add(store)
                    }
                }
            }

        })
    }

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        //48.856614

        val newIntent = Intent(context, StoreDetailActivity::class.java)

        for(i in 0 until stores.size){
            val store = MarkerOptions()
            val storeLatLng = LatLng(stores[i].latitude, stores[i].longitude)
            store.title(stores[i].name)
            store.snippet(stores[i].address + "-" + stores[i].zipCode + " " + stores[i].city)
            store.position(storeLatLng)
            googleMap.addMarker(store)?.tag = i
        }

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.854885,2.338646),5f))

        googleMap.setOnMapClickListener {
            (activity as BaseActivity).showToast(it.toString())
        }

        googleMap.setOnInfoWindowClickListener {

            val markerTag = it.tag
            markerTag.let {
                for (i in 0 until stores.size) {
                    when (markerTag) {
                        i -> {
                            newIntent.putExtra("name", stores[i].name)
                            newIntent.putExtra("pictureStore", stores[i].pictureStore)
                            newIntent.putExtra("address", stores[i].address)
                            newIntent.putExtra("zipCodeCity", stores[i].zipCode + " - " + stores[i].city)
                            newIntent.putExtra("description", stores[i].description)
                            startActivity(newIntent)
                        }
                    }
                }
            }
        }
        this.googleMap=googleMap
        locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}