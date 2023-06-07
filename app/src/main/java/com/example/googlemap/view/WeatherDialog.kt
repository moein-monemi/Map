package com.example.googlemap.view

import android.app.Activity
import com.example.googlemap.R
import com.example.googlemap.base.BaseDialog
import com.example.googlemap.databinding.WeatherDialogBinding
import com.example.googlemap.model.Main

class WeatherDialog(activity: Activity): BaseDialog<WeatherDialogBinding>(activity, R.layout.weather_dialog) {

    init {
        buildDialog()
    }

    fun setParameter (main: Main) {
        getBinding().txtTemp.text     = "Temp = ${(main.temp?.minus(273.15)).toString()}"
        getBinding().txtTempMin.text  = "Temp min = ${(main.temp_min?.minus(273.15)).toString()}"
        getBinding().txtTempMax.text  = "Temp max = ${(main.temp_max?.minus(273.15)).toString()}"
        getBinding().txtPressure.text = "Pressure = ${main.pressure.toString()}"
        getBinding().txtHumidity.text = "Humidity = ${main.humidity.toString()}"
        getBinding().txtSeaLevel.text = "Sea level = ${main.sea_level.toString()}"
        getBinding().txtGroundLevel.text = "Ground level = ${main.grnd_level.toString()}"
    }


}