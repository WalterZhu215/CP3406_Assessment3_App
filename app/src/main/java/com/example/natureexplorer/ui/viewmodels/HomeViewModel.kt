package com.example.natureexplorer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.natureexplorer.data.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class ReviewPlace(
    val name: String,
    val rating: String,
    val review: String
)


data class WeatherUiData(

    val temperature: Double,

    val humidity: Int,

    val windSpeed: Double,

    val condition: String,

    val observationTime: String
)


data class HomeUiState(

    val featuredTitle: String =
        "Redwood National Park",

    val featuredSubtitle: String =
        "Discover the ancient giants.",

    val communityReviews:
    List<ReviewPlace> =
        emptyList(),

    val weather:
    WeatherUiData? =
        null,

    val isWeatherLoading:
    Boolean =
        false,

    val weatherError:
    String? =
        null
)


class HomeViewModel(

    private val weatherRepository:
    WeatherRepository

) : ViewModel() {


    private val _uiState =
        MutableStateFlow(
            HomeUiState()
        )


    val uiState:
            StateFlow<HomeUiState> =
        _uiState.asStateFlow()


    init {

        loadHomeData()

        loadWeather()
    }


    private fun loadHomeData() {

        val reviews =
            listOf(

                ReviewPlace(
                    name =
                        "Pine Valley Trail",
                    rating =
                        "4.8",
                    review =
                        "Great for beginners!"
                ),

                ReviewPlace(
                    name =
                        "Lake Serenity",
                    rating =
                        "4.9",
                    review =
                        "Beautiful sunset views."
                ),

                ReviewPlace(
                    name =
                        "Echo Canyon",
                    rating =
                        "4.5",
                    review =
                        "A bit rocky, wear good shoes."
                )
            )


        _uiState.value =
            _uiState.value.copy(

                communityReviews =
                    reviews
            )
    }


    /*
     * Fetches live environmental data
     * from Open-Meteo.
     *
     * These coordinates represent the
     * Redwood National Park region.
     */
    fun loadWeather() {

        viewModelScope.launch {


            _uiState.value =
                _uiState.value.copy(

                    isWeatherLoading =
                        true,

                    weatherError =
                        null
                )


            try {


                val result =

                    weatherRepository
                        .getCurrentWeather(

                            latitude =
                                41.2132,

                            longitude =
                                -124.0046
                        )


                val weatherData =

                    WeatherUiData(

                        temperature =
                            result.temperature,

                        humidity =
                            result.humidity,

                        windSpeed =
                            result.windSpeed,

                        condition =
                            weatherCodeToDescription(
                                result.weatherCode
                            ),

                        observationTime =
                            result.time
                    )


                _uiState.value =
                    _uiState.value.copy(

                        weather =
                            weatherData,

                        isWeatherLoading =
                            false,

                        weatherError =
                            null
                    )


            } catch (exception: Exception) {


                _uiState.value =
                    _uiState.value.copy(

                        isWeatherLoading =
                            false,

                        weatherError =
                            "Unable to load live environmental data."
                    )
            }
        }
    }


    private fun weatherCodeToDescription(
        code: Int
    ): String {


        return when (code) {


            0 ->
                "Clear sky"


            1, 2 ->
                "Partly cloudy"


            3 ->
                "Overcast"


            45, 48 ->
                "Fog"


            51, 53, 55,
            56, 57 ->
                "Drizzle"


            61, 63, 65,
            66, 67 ->
                "Rain"


            71, 73, 75,
            77 ->
                "Snow"


            80, 81, 82 ->
                "Rain showers"


            85, 86 ->
                "Snow showers"


            95, 96, 99 ->
                "Thunderstorm"


            else ->
                "Unknown conditions"
        }
    }
}


class HomeViewModelFactory(

    private val weatherRepository:
    WeatherRepository

) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {


        if (
            modelClass.isAssignableFrom(
                HomeViewModel::class.java
            )
        ) {


            @Suppress("UNCHECKED_CAST")

            return HomeViewModel(
                weatherRepository
            ) as T
        }


        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}
