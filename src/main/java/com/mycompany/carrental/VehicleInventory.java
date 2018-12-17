package com.mycompany.carrental;


import com.mycompany.carrental.VehicleType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author qzhang
 */
public class VehicleInventory {
    private static final Map<VehicleType, List<Vehicle>> inv = new HashMap<>();
    private static final VehicleInventory instance = new VehicleInventory();
    private VehicleInventory(){}
    
    public static VehicleInventory getInstance(){
        return instance;
    }
    
    public void addVehicle(Vehicle obj){
        if (!inv.containsKey(obj.getVehicleType())){
            inv.put(obj.getVehicleType(), new ArrayList<>());
        }
        inv.get(obj.getVehicleType()).add(obj);
    }

    public List<Vehicle> getVehicleList(VehicleType carTypeParam) {
        return inv.get(carTypeParam);
    }
    
    public void clear(){
        inv.clear();
    }
}
