package com.parkit.parkingsystem.service;

import java.time.Duration;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {
	private TicketDAO ticketDAO = new TicketDAO();

	public void calculateFare(Ticket ticket) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().isBefore(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}

		// TODO: Some tests are failing here. Need to check if this logic is correct
		long minutesInParking = Duration.between(ticket.getInTime(), ticket.getOutTime()).toMinutes();
		double discount = calculateDiscount(ticket.getVehicleRegNumber());

		if (minutesInParking > 30) {
			switch (ticket.getParkingSpot().getParkingType()) {
			case CAR: {
				ticket.setPrice(minutesInParking * Fare.CAR_RATE_PER_MINUTE * discount);
				break;
			}
			case BIKE: {
				ticket.setPrice(minutesInParking * Fare.CAR_RATE_PER_MINUTE * discount);
				break;
			}
			default:
				throw new IllegalArgumentException("Unkown Parking Type");

			}
		} else
			ticket.setPrice(0);
	}

	private double calculateDiscount(String vehicleRegNumber) {
		boolean isReccuringUser = ticketDAO.isReccuringUser(vehicleRegNumber);
		if (isReccuringUser) {
			return 0.95;
		}
		return 1;
	}

}