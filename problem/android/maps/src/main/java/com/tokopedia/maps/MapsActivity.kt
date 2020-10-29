package com.tokopedia.maps

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mapFragment: SupportMapFragment? = null
    private var googleMap: GoogleMap? = null

    private lateinit var textCountryName: TextView
    private lateinit var textCountryCapital: TextView
    private lateinit var textCountryPopulation: TextView
    private lateinit var textCountryCallCode: TextView

    private var editText: EditText? = null
    private var buttonSubmit: View? = null

    private var maps: Maps = Maps()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        bindViews()
        initListeners()
        loadMap()
//         mapFragment = supportFragmentManager
//                .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment!!.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        mapFragment?.onResume()
    }

    private fun bindViews() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        editText = findViewById(R.id.editText)
        buttonSubmit = findViewById(R.id.buttonSubmit)
        textCountryName = findViewById(R.id.txtCountryName)
        textCountryCapital = findViewById(R.id.txtCountryCapital)
        textCountryPopulation = findViewById(R.id.txtCountryPopulation)
        textCountryCallCode = findViewById(R.id.txtCountryCallCode)
    }

    private fun initListeners() {
        buttonSubmit!!.setOnClickListener {
            // TODO
            // search by the given country name, and
            // 1. pin point to the map
            // 2. set the country information to the textViews.
            searchDataCountry(editText?.text.toString())
        }
    }

    private fun searchDataCountry(country: String) {
        val apiClient = ApiClient()
        val service: MapsApi = apiClient.getRetrofitInstance()!!.create(MapsApi::class.java)
        val call: Call<List<Maps>> = service.getDataCountry(country)
        call.enqueue(object : Callback<List<Maps>> {
            override fun onFailure(call: Call<List<Maps>>, t: Throwable) {}

            override fun onResponse(call: Call<List<Maps>>, response: Response<List<Maps>>) {
                if (!response.body().isNullOrEmpty()) {
                    maps = response.body()!![0]
                    editText?.setText("")
                    googleMap?.clear()
                    googleMap?.addMarker(MarkerOptions()
                        .position(LatLng(maps.latlng?.get(0)?.toDouble() ?: 0.0, maps.latlng?.get(1)?.toDouble() ?: 0.0))
                        .title(maps.name))

                    googleMap?.moveCamera(
                        CameraUpdateFactory.newLatLng(
                            LatLng(maps.latlng!![0].toDouble(), maps.latlng!![1].toDouble())))
                } else {
                    maps = Maps()
                    Toast.makeText(applicationContext,"Not Found",Toast.LENGTH_SHORT).show()
                }
                show()
            }
        })
    }

    private fun show() {
        txtCountryName.text = ("Nama negara: "+ maps.name)
        txtCountryCapital.text = ("Ibukota: "+ maps.capital)
        txtCountryPopulation.text = ("Jumlah penduduk: "+ maps.population)
        txtCountryCallCode.text = ("Kode telepon: "+ maps.callingCodes?.get(0))
    }

    private fun loadMap() {
        mapFragment!!.getMapAsync { googleMap -> this@MapsActivity.googleMap = googleMap }
    }

    override fun onMapReady(p0: GoogleMap?) {
        googleMap = p0
    }
}
