import React, { useEffect, useState } from 'react';

import { Line, Pie } from 'react-chartjs-2';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';

import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  ArcElement,
} from 'chart.js';

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  ArcElement
);

const kelvinToCelsius = (kelvin) => (kelvin - 273.15).toFixed(2);

const WeatherDashboard = () => {
  const [weatherData, setWeatherData] = useState(null);

  const city = "Lima"; // Puedes cambiar esto o hacerlo dinámico

  useEffect(() => {
    fetch(`http://localhost:7002/api/v1/weathers/city/${city}`)
      .then(response => response.json())
      .then(data => setWeatherData(data))
      .catch(error => console.error('Error fetching weather data:', error));
  }, [city]);

  if (!weatherData) {
    return <div>Loading...</div>;
  }

  // Configuración del gráfico de temperatura
  const tempData = {
    labels: ['Min', 'Current', 'Max'],
    datasets: [{
      label: 'Temperatura (°C)',
      data: [
        kelvinToCelsius(weatherData.main.temp_min), 
        kelvinToCelsius(weatherData.main.temp), 
        kelvinToCelsius(weatherData.main.temp_max)
      ],
      backgroundColor: ['rgba(75, 192, 192, 0.2)'],
      borderColor: ['rgba(75, 192, 192, 1)'],
      borderWidth: 1
    }]
  };

  // Configuración del gráfico de cobertura de nubes
  const cloudData = {
    labels: ['Nubosidad', 'Claro'],
    datasets: [{
      data: [weatherData.clouds.all, 100 - weatherData.clouds.all],
      backgroundColor: ['#36A2EB', '#FFCE56']
    }]
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
      <h1>Dashboard de Clima para {weatherData.name}, {weatherData.sys.country}</h1>

      <div style={{ height: '300px', width: '100%', marginBottom: '20px' }}>
        <MapContainer center={[weatherData.coord.lat, weatherData.coord.lon]} zoom={13} style={{ height: '100%' }}>
          <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
          <Marker position={[weatherData.coord.lat, weatherData.coord.lon]}>
            <Popup>{weatherData.name}</Popup>
          </Marker>
        </MapContainer>
      </div>

      <div style={{ display: 'flex', justifyContent: 'space-around', width: '100%' }}>
        <div>
          <h3>Temperatura</h3>
          <Line data={tempData} />
        </div>

        <div>
          <h3>Porcentaje de nubes</h3>
          <Pie data={cloudData} />
        </div>
      </div>

      <div style={{ marginTop: '20px' }}>
        <h3>Viento</h3>
        <p>Velocidad: {weatherData.wind.speed} m/s, Direcci&oacute;n: {weatherData.wind.deg}°</p>

        <h3>Otros detalles</h3>
        <p>Presi&oacute;n: {weatherData.main.pressure} hPa</p>
        <p>Humedad: {weatherData.main.humidity}%</p>
        <p>Visibilidad: {weatherData.visibility / 1000} km</p>
        <p>Condición: {weatherData.weather[0].description}</p>
      </div>
    </div>
  );
};

export default WeatherDashboard;
