package org.sebsy.grasps;

import org.sebsy.grasps.model.TypeReservation;

public class Tarification {
    public double calculerTotal(TypeReservation type, int nbPlaces, boolean isPremium) {
        double total = type.getMontant() * nbPlaces;
        if (isPremium) {
            total *= (1 - type.getReductionPourcent() / 100.0);
        }
        return total;
    }
}
