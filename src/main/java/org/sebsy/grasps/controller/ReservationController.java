package org.sebsy.grasps;

import org.sebsy.grasps.dto.Params;
import org.sebsy.grasps.model.Client;
import org.sebsy.grasps.model.Reservation;
import org.sebsy.grasps.model.TypeReservation;
import org.sebsy.grasps.daos.ClientDao;
import org.sebsy.grasps.daos.TypeReservationDao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Controlleur qui prend en charge la gestion des réservations client
 */
public class ReservationController {

    /**
     * formatter
     */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * DAO permettant d'accéder à la table des clients
     */
    private final ClientDao clientDao;

    /**
     * DAO permettant d'accéder à la table des types de réservation
     */
    Tarification tarification = new Tarification();
    private TypeReservationDao typeReservationDao = new TypeReservationDao();
    private ReservationFactory reservationFactory = new ReservationFactory(tarification);
    /**
     * Méthode qui créée une réservation pour un client à partir des informations transmises
     *
     * @return Reservation
     *
     */
    public ReservationController() {
        this.clientDao = new ClientDao();
        this.typeReservationDao = new TypeReservationDao();
        Tarification tarification = new Tarification();
        this.reservationFactory = new ReservationFactory(tarification);
    }

    public ReservationController(ClientDao clientDao, TypeReservationDao typeReservationDao, ReservationFactory reservationFactory) {
        this.clientDao = clientDao;
        this.typeReservationDao = typeReservationDao;
        this.reservationFactory = reservationFactory;
    }

    public Reservation creerReservation(Params params) {

        String identifiantClient = params.getIdentifiantClient();
        LocalDateTime dateReservation = toDate(params.getDateReservation());
        int nbPlaces = params.getNbPlaces();
        String typeReservationCode = params.getTypeReservation();

        Client client = clientDao.extraireClient(identifiantClient);
        TypeReservation type = typeReservationDao.extraireTypeReservation(typeReservationCode);

        Reservation reservation = reservationFactory.creer(client, type, dateReservation, nbPlaces);
        client.ajouterReservation(reservation);

        return reservation;
    }

    /**
     * Transforme une date au format String en {@link LocalDateTime}
     *
     * @param dateStr date au format String
     * @return LocalDateTime
     */
    private LocalDateTime toDate(String dateStr) {

        return LocalDateTime.parse(dateStr, formatter);
    }
}
