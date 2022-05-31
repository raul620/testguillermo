package com.guillermocode.testpractico


import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


import com.squareup.picasso.Picasso


import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar


class DetailArticulo : AppCompatActivity(), OnMapReadyCallback {

    private var category: String = ""
    private var contentx: String = ""
    private var img: String = ""
    private var number: String = ""
    private lateinit var itemImg: ImageView
    private lateinit var detalle1: TextView
    private lateinit var detalle2: TextView
    private lateinit var comprar: Button
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    /////
    private lateinit var locationCallback: LocationCallback

    private val CODIGO_PERMISOS_UBICACION_SEGUNDO_PLANO = 2106
    private val LOG_TAG = "EnviarUbicacion"
    private var haConcedidoPermisos = false
    private lateinit var map: GoogleMap
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        verificarPermisos()

    }
    private fun createMarker(latitud: Double, longitud: Double) {
        val favoritePlace = LatLng(latitud,longitud)
        map.addMarker(MarkerOptions().position(favoritePlace).title("Aqui estoy!"))
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(favoritePlace, 18f),
            4000,
            null
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.detallearticulo)
        verificarPermisos()
        itemImg = findViewById(R.id.imageViewD)
        detalle1 = findViewById(R.id.detalle1)
        detalle2 = findViewById(R.id.detalle2)
        comprar = findViewById(R.id.Comprar)
        comprar.setOnClickListener {
           show(category, contentx)
        }
        getInfo()
        createMapFragment()

    }

    private fun createMapFragment() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    fun imprimirUbicacion(ubicacion: Location) {
        createMarker(ubicacion.latitude, ubicacion.longitude)
        Log.d(LOG_TAG, "Latitud es ${ubicacion.latitude} y la longitud es ${ubicacion.longitude}")
        //Snackbar.make(, "Latitud es ${ubicacion.latitude} y la longitud es ${ubicacion.longitude}", Snackbar.LENGTH_LONG)
         //   .show()
        Toast.makeText(this, "Latitud es ${ubicacion.latitude} y la longitud es ${ubicacion.longitude}", Toast.LENGTH_LONG).show()

    }

    fun onPermisosConcedidos() {
        // Hasta aquí sabemos que los permisos ya están concedidos
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    imprimirUbicacion(it)
                } else {
                    Log.d(LOG_TAG, "No se pudo obtener la ubicación")
                }
            }
            //////
            val locationRequest = LocationRequest.create().apply {
                interval = 10000
                fastestInterval = 5000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult ?: return
                    Log.d(LOG_TAG, "Se recibió una actualización")
                    for (location in locationResult.locations) {
                        imprimirUbicacion(location)
                    }
                }
            }
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            Log.d(LOG_TAG, "Tal vez no solicitaste permiso antes")
        }

    }




    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CODIGO_PERMISOS_UBICACION_SEGUNDO_PLANO) {
            val todosLosPermisosConcedidos =
                grantResults.all { it == PackageManager.PERMISSION_GRANTED }
            if (grantResults.isNotEmpty() && todosLosPermisosConcedidos) {
                haConcedidoPermisos = true;
                onPermisosConcedidos()
                Log.d(LOG_TAG, "El usuario concedió todos los permisos")
            } else {
                Log.d(LOG_TAG, "Uno o más permisos fueron denegados")
            }
        }
    }

    private fun verificarPermisos() {
        val permisos = arrayListOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )
        // Segundo plano para Android Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permisos.add(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
        val permisosComoArray = permisos.toTypedArray()
        if (tienePermisos(permisosComoArray)) {
            haConcedidoPermisos = true
            onPermisosConcedidos()
            Log.d(LOG_TAG, "Los permisos ya fueron concedidos")
        } else {
            solicitarPermisos(permisosComoArray)
        }
    }

    private fun solicitarPermisos(permisos: Array<String>) {
        Log.d(LOG_TAG, "Solicitando permisos...")
        requestPermissions(
            permisos,
            CODIGO_PERMISOS_UBICACION_SEGUNDO_PLANO
        )
    }

    private fun tienePermisos(permisos: Array<String>): Boolean {
        return permisos.all {
            return ContextCompat.checkSelfPermission(
                requireActivity(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requireActivity(): Context {
        return this;
    }

    private fun getInfo() {
        val intent = intent
        if (intent.hasExtra("NUMBER")) {
            //number = intent.getStringExtra("NUMBER").toString()
            //Log.e("number", number)
        }
        if (intent.hasExtra("IMG")) {
            img = intent.getStringExtra("IMG").toString()
            Log.e("img", img)
            Picasso.get().load(img).into(itemImg)
        }
        if (intent.hasExtra("CATEGORY")) {
            category = intent.getStringExtra("CATEGORY").toString()
            detalle1.text = category
        }
        if (intent.hasExtra("CONTENT")) {
            contentx = intent.getStringExtra("CONTENT").toString()
            detalle2.text = contentx
        }
    }

    fun show(title: String, message: String) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea comprar "+title)
        builder.setMessage(message)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
// performing positive action
        builder.setPositiveButton("Yes") { _, _ ->
            //onResponse(ResponseType.YES)
            Toast.makeText(this, "Compra realizada con exito", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, ArticlesActivity::class.java)
            startActivity(intent)
        }

        // performing negative action
        builder.setNegativeButton("No") { _, _ ->
            //onResponse(ResponseType.NO)

            val intent = Intent(applicationContext, ArticlesActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Compra se cancelo", Toast.LENGTH_SHORT).show()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()

        // Set other dialog properties
        alertDialog.setCancelable(false)

        alertDialog.show()

    }
}