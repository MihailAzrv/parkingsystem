package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().isBefore(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        int inHour = ticket.getInTime().getHour();
        int outHour = ticket.getOutTime().getHour();

        //TODO: Some tests are failing here. Need to check if this logic is correct
        int duration = outHour - inHour;

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
    
    
    //Une méthode pour vérifier le temps passé dans le parking en minutes (ticket) retourne un long/int le nombre de minute
    
    
    //Une mthode pour savoir si la voiture est déjà passée dans le parking
    //1) récupérer la plaque d'immatriculation
    //2) faire appel à la classe ticketDAO et à une nouvelle méthode pour savoir si ma plaque d'immattriculation est déjà apparu dans la table ticket et il est bien sorti du parking et a bien payé
    //3) ma méthode retourne soit 1 soit 0,95
}