package com.example.mylocation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.location.LocationManager
import android.content.pm.PackageManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.widget.TextView
import android.location.LocationProvider
import android.provider.Settings
import android.widget.Toast
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 位置情報の権限を確認
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            // 権限がない場合、ダイヤログを表示
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),8888)
        } else {

            //　位置情報の取得
            getLocation()

            // lateinit がつけられたプロパティが安全にアクセスできるか確認
            if (::locationManager.isInitialized) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,50f, this)
            }

        }
    }

    /**
     * 位置情報取得モジュール
     */
    private fun getLocation() {
        Log.d("debug", "locationStart()")

        // LocationManagerのインスタンスを生成
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.d("debug", "Location Manager is enable.")
        } else {
            // GPSのセットアップをする
            val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(settingsIntent)
            Log.d("debug", "GPS error.")
        }

        // 位置情報の権限を確認
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 8888)
            Log.d("debug", "checkSelfPermission is False")
            return
        }
        // locationManagerにより位置情報の更新をリクエストする
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,50f,this)

    }

    /**
     * 位置情報更新モジュール
     */
    override fun onLocationChanged(location: Location) {
        // 位置情報のテキストidを取得
        val loctext: TextView = findViewById(R.id.loc_text)

        // 位置情報のテキストを更新
        loctext.text = "緯度:" + location.getLatitude() + "　" + "経度:" + location.getLongitude()

        // ジオフェンスの判定にもとづきテキストカラーを更新
        if (location.getLatitude() > 35.04 && location.getLatitude() < 35.06
            && location.getLongitude() > 137.15 && location.getLongitude() < 137.17){
            loctext.setTextColor(Color.RED)
        }else{
            loctext.setTextColor(Color.BLACK)
        }
    }
}