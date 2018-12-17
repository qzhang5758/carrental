/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carrental;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 *
 * @author qzhang
 */
public class Reservation {

    private Vehicle vehicle;
    private Customer customer;
    private Map<LocalDateTime, Integer> timeFramesMap = new HashMap<>();

    public Reservation(Vehicle vehicle, Customer customer, Map<LocalDateTime, Integer> timeFramesMap) {
        this.vehicle = vehicle;
        this.customer = customer;
        this.timeFramesMap = timeFramesMap;
    }

    public static Reservation reserve(Customer customerParam, VehicleType carTypeParam, Map<LocalDateTime, Integer> timeFramesMap) {
        VehicleInventory vehicleInventory = VehicleInventory.getInstance();
        List<Vehicle> vehicleList = vehicleInventory.getVehicleList(carTypeParam);
        Optional<Vehicle> result = vehicleList.stream().filter(isAvailable(timeFramesMap)).findAny();
        if (result.isPresent()){
            return new Reservation(result.get(), customerParam, timeFramesMap);
        }else{
            return null;
        }
    }

    public static Predicate<Vehicle> isAvailable(Map<LocalDateTime, Integer> timeFramesMap) {
        return v -> v.isAvailable(timeFramesMap);
    }
    
    public Vehicle getVehicle() {
        return vehicle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Map<LocalDateTime, Integer> getTimeFramesMap() {
        return timeFramesMap;
    }

    
}
