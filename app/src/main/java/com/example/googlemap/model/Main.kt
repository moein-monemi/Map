package com.example.googlemap.model


data class Main (
  var temp : Double? = null,
  var feelsLike : Double? = null,
  var temp_min : Double? = null,
  var temp_max : Double? = null,
  var pressure : Int? = null,
  var humidity : Int? = null,
  var sea_level : Int? = null,
  var grnd_level : Int? = null
)