package com.mycompany.carrental;


import java.time.LocalDateTime;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author qzhang
 */
public class Vehicle {
    private VehicleType vehicleType;
    private String plateNumber;
    private Map<LocalDateTime, Integer> slotMap = new TreeMap<>();

    public Vehicle(VehicleType vehicleType, String num) {
        this.vehicleType = vehicleType;
        this.plateNumber = num;
        slotMap.put(LocalDateTime.now(), Integer.MAX_VALUE);
    }
    
    public Map<LocalDateTime, Integer> isAvailable(LocalDateTime startDateTime, int numOfDays){
        LocalDateTime keyDateTime = slotMap.keySet().stream().filter(dt -> dt.isBefore(startDateTime) || dt.isEqual(startDateTime)).findFirst().get();
        int daysBetween  = (int) DAYS.between(keyDateTime, startDateTime);
        int availableDays = slotMap.get(keyDateTime) - daysBetween;
        if ( availableDays > numOfDays ) {
            Map<LocalDateTime, Integer> retMap = new TreeMap<>();
            retMap.put(keyDateTime, daysBetween);
            retMap.put(startDateTime.plus(numOfDays, DAYS), availableDays - numOfDays);
            return retMap;
        }
        return null;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    synchronized boolean isAvailable(Map<LocalDateTime, Integer> timeFramesMap) {
        Map<LocalDateTime, Integer> allReturnMap = new TreeMap<>();
        for (Map.Entry<LocalDateTime, Integer> entry : timeFramesMap.entrySet()){
            Map<LocalDateTime, Integer> resultMap = isAvailable(entry.getKey(), entry.getValue());
            if (null != resultMap){
                allReturnMap.putAll(resultMap);
            }else{
                return false;
            }
        }
        slotMap.putAll(allReturnMap);
        return true;
    }

    @Override
     public boolean equals(Object o) {
         if(!(o instanceof Vehicle)){
             return false;
         }else{
             Vehicle v = (Vehicle)o;
             return this.plateNumber.equals(v.getPlateNumber());
         }
     }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.plateNumber);
        return hash;
    }
}
