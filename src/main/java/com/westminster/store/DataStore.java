package com.westminster.store;

import com.westminster.model.Room;
import com.westminster.model.Sensor;
import com.westminster.model.SensorReading;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class DataStore {

    public static final Map<String, Room> rooms = new ConcurrentHashMap<>();
    public static final Map<String, Sensor> sensors = new ConcurrentHashMap<>();
    public static final Map<String, List<SensorReading>> sensorReadings = new ConcurrentHashMap<>();

    private DataStore() {
    }

    public static List<SensorReading> getOrCreateReadings(String sensorId) {
        return sensorReadings.computeIfAbsent(sensorId, key -> new CopyOnWriteArrayList<>());
    }
}