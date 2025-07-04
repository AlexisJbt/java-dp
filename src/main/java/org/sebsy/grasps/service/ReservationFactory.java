package org.sebsy.grasps;

import org.sebsy.grasps.model.Client;
import org.sebsy.grasps.model.Reservation;
import org.sebsy.grasps.model.TypeReservation;

import java.time.LocalDateTime;

public class ReservationFactory {
    private final Tarification tarification;

    public ReservationFactory(Tarification tarification) {
        this.tarification = tarification;
    }

    public Reservation creer(Client client, TypeReservation type, LocalDateTime date, int nbPlaces) {
        Reservation reservation = new Reservation(date);
        reservation.setClient(client);
        reservation.setNbPlaces(nbPlaces);

        double total = tarification.calculerTotal(type, nbPlaces, client.isPremium());
        reservation.setTotal(total);

        return reservation;
    }
}