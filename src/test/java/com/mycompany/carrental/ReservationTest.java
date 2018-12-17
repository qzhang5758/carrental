/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carrental;

import com.mycompany.carrental.Customer;
import com.mycompany.carrental.Vehicle;
import com.mycompany.carrental.Reservation;
import com.mycompany.carrental.VehicleType;
import com.mycompany.carrental.VehicleInventory;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author qzhang
 */
public class ReservationTest {
    private static final VehicleInventory vehicleInventory = VehicleInventory.getInstance();
    
    public ReservationTest() {
    }
    

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        Vehicle sedanA = new Vehicle(VehicleType.SEDAN, "A");
        Vehicle suv = new Vehicle(VehicleType.SUV, "B");
        Vehicle truck = new Vehicle(VehicleType.TRUCK, "C");
        Vehicle sedanD = new Vehicle(VehicleType.SEDAN, "D");

        vehicleInventory.addVehicle(sedanA);
        vehicleInventory.addVehicle(suv);
        vehicleInventory.addVehicle(truck);
        vehicleInventory.addVehicle(sedanD);
    }

    @After
    public void tearDown() {
        vehicleInventory.clear();
    }

    /**
     * Customer requests to reserve one vehicle for one time frame. There is vehicle available in inventory.
     * @result Reservation succeeded with a reservation.
     */
    
    @Test
    public void testReserve() {
        VehicleType carTypeSedan = VehicleType.SEDAN;
        Customer customer = new Customer("FirstName", "LastName");
        Map<LocalDateTime, Integer> timeFramesMap = new HashMap<>();
        LocalDateTime startDateTime = LocalDateTime.of(2018, 12, 28, 10, 20, 00);
        int numOfDays = 6;
        timeFramesMap.put(startDateTime, numOfDays);
        
        String expected = "A";

        Reservation reservation;
        reservation = Reservation.reserve(customer, carTypeSedan, timeFramesMap);
        assertEquals(expected, reservation.getVehicle().getPlateNumber());
    }
    
    /**
     * Customer requests to reserve one vehicle for one time frame. That type of vehicles are all booked for that time frame. 
     * No vehicle available
     * @result Reservation failed with null reservation.
     */
    @Test
    public void testReserveNotAvailable() {
        VehicleType carTypeSedan = VehicleType.SEDAN;
        Customer customer1 = new Customer("FirstName", "LastName");
        Map<LocalDateTime, Integer> timeFramesMap = new HashMap<>();
        LocalDateTime startDateTime = LocalDateTime.of(2018, 12, 28, 10, 20, 00);
        int numOfDays = 6;
        timeFramesMap.put(startDateTime, numOfDays);

        Reservation.reserve(customer1, carTypeSedan, timeFramesMap);

        Customer customer2 = new Customer("FirstName", "LastName");

        Reservation.reserve(customer2, carTypeSedan, timeFramesMap);

        Customer customer3 = new Customer("FirstName", "LastName");
        Reservation reservation;
        reservation = Reservation.reserve(customer3, carTypeSedan, timeFramesMap);
        assertNull(reservation);
    }

    /**
     * Customer requests to reserve one vehicle. Some vehicles are not available for that time frame.
     * But there is another available.
     * @result Reservation succeeded with a reservation.
     */
    @Test
    public void testReserveAnotherVehicleIfFirstNotAvailable() {
        String expected = "D";

        VehicleType carTypeSedan = VehicleType.SEDAN;
        Customer customer1 = new Customer("FirstName1", "LastName1");
        Map<LocalDateTime, Integer> timeFramesMap = new HashMap<>();
        LocalDateTime startDateTime = LocalDateTime.of(2018, 12, 28, 10, 20, 00);
        int numOfDays = 6;
        timeFramesMap.put(startDateTime, numOfDays);

        Reservation.reserve(customer1, carTypeSedan, timeFramesMap);

        Customer customer2 = new Customer("FirstName2", "LastName2");

        Reservation reservation;
        reservation = Reservation.reserve(customer2, carTypeSedan, timeFramesMap);
        assertEquals(expected, reservation.getVehicle().getPlateNumber());
    }
    
    /**
     * Customer requests to reserve one vehicle for multiple time frames. The vehicle is available.
     * @result The reservation succeeded with a reservation.
     */
    @Test
    public void testReserveMultipleTimeFrames() {

        VehicleType carTypeSedan = VehicleType.SEDAN;
        Map<LocalDateTime, Integer> timeFramesMap = new HashMap<>();
        LocalDateTime startDateTime1 = LocalDateTime.of(2018, 12, 28, 10, 20, 00);
        LocalDateTime startDateTime2 = LocalDateTime.of(2019, 1, 28, 10, 20, 00);
        timeFramesMap.put(startDateTime1, 6);
        timeFramesMap.put(startDateTime2, 5);
        Customer customer = new Customer("FirstName", "LastName");
        String expected = "A";

        Reservation reservation;
        reservation = Reservation.reserve(customer, carTypeSedan, timeFramesMap);
        assertEquals(expected, reservation.getVehicle().getPlateNumber());
    }
    
    /**
     * Customer requests to reserve one vehicle for multiple time frames. Other vehicle of that type are booked for that time frames.
     * But there is one vehicle available.
     * @result The reservation succeeded with a reservation.
     */
    @Test
    public void testReserveMultipleTimeFramesWIthAnotherCar() {

        VehicleType carTypeSedan = VehicleType.SEDAN;
        Map<LocalDateTime, Integer> timeFramesMap = new HashMap<>();
        LocalDateTime startDateTime1 = LocalDateTime.of(2018, 12, 28, 10, 20, 00);
        LocalDateTime startDateTime2 = LocalDateTime.of(2019, 1, 28, 10, 20, 00);
        timeFramesMap.put(startDateTime1, 6);
        timeFramesMap.put(startDateTime2, 5);
        Customer customer1 = new Customer("FirstName", "LastName");
        String expected = "D";

        Reservation.reserve(customer1, carTypeSedan, timeFramesMap);

        Customer customer2 = new Customer("FirstName", "LastName");

        Reservation reservation;
        reservation = Reservation.reserve(customer2, carTypeSedan, timeFramesMap);
        assertEquals(expected, reservation.getVehicle().getPlateNumber());
    }
}
