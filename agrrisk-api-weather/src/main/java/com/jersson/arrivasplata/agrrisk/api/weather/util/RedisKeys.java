package com.jersson.arrivasplata.agrrisk.api.weather.util;

public enum RedisKeys {

    // Definición de las claves con sus prefijos
    WEATHER_KEY("AGRRISK:WEATHER:"),
    USER_SESSION_KEY("AGRRISK:USER_SESSION:"),
    PRODUCT_KEY("AGRRISK:PRODUCT:");

    private final String prefix;

    // Constructor
    RedisKeys(String prefix) {
        this.prefix = prefix;
    }

    // Método para obtener la clave completa
    public String getKey(String identifier) {
        return prefix + identifier;
    }
}
