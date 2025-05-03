# Weather Application

A modern Android weather application that provides current weather conditions and forecasts based on user location or search queries.

![Weather App Screenshot](screenshots/app_screenshot.png)

## Features

- **Current Weather Display**: Shows temperature, conditions, humidity, and wind speed
- **7-Day Weather Forecast**: Daily weather predictions with high/low temperatures
- **Location-Based Weather**: Automatically detects user location to show relevant weather data
- **Search Functionality**: Allows users to search for weather in any location worldwide
- **Responsive UI**: Clean, modern interface that adapts to different screen sizes
- **Offline Support**: Caches last fetched weather data for offline viewing
- **Dark/Light Mode Support**: Adapts to system theme settings

## Architecture

This application follows the MVVM (Model-View-ViewModel) architecture pattern with Clean Architecture principles:

### Components:

- **View Layer**: Activities and Fragments that display UI and handle user interactions
  - `MainActivity`: Main screen showing current weather and hosting the ViewPager
  - `ForecastFragment`: Shows 7-day forecast in a RecyclerView

- **ViewModel Layer**: Manages UI-related data and business logic
  - `WeatherViewModel`: Handles weather data operations and exposes LiveData for the views

- **Model Layer**: Contains data models and repositories
  - **Repository Pattern**: `WeatherRepository` abstracts data sources
  - **Remote Data Source**: API service for fetching weather data
  - **Local Data Source**: SharedPreferences for caching last known weather data

- **Dependency Injection**: Using a custom ViewModelFactory

### Libraries and Technologies

- **Kotlin**: Primary programming language
- **Android Architecture Components**: LiveData, ViewModel
- **Retrofit**: For API communication
- **ViewBinding**: For type-safe view access
- **RecyclerView**: For displaying lists
- **Material Components**: For modern UI elements
- **Google Play Services Location**: For location services
- **ViewPager2**: For tab navigation

## API Integration

This application uses the [Visual Crossing Weather API](https://www.visualcrossing.com/weather-api) for fetching weather data. The API provides:

- Current weather conditions
- Hourly forecasts
- Daily forecasts
- Historical weather data

## Installation

1. Clone the repository:
   git clone https://github.com/fzry18/WeatherApplication.git
2. Open the project in Android Studio
3. Create a file called `local.properties` in the root directory and add your API key:
   WEATHER_API_KEY=your_api_key_here BASE_URL=https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/
4. Build and run the application

## Configuration

The application requires the following permissions:
- `ACCESS_FINE_LOCATION`: For detecting user's location
- `INTERNET`: For fetching weather data
- `POST_NOTIFICATIONS`: For Android 13+ notification support

## Future Improvements

- Implement Room Database for better offline experience
- Add weather alerts and notifications
- Add weather widgets
- Implement unit and UI tests
- Add more detailed hourly forecasts
- Support for more weather metrics (air quality, UV index, etc.)
- Custom themes and appearance settings

## Credits

- Weather data provided by [Visual Crossing Weather API](https://www.visualcrossing.com/weather-api)
- Icons and design elements from Material Design
