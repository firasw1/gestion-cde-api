package com.example.commande.service;

import com.example.commande.dao.CommandeDAO;
import com.example.commande.model.Commande;

import java.util.Date;
import java.util.List;

public class CommandeServiceImpl implements ICommandeService {

    private CommandeDAO commandeDAO;

    public CommandeServiceImpl() {
        this.commandeDAO = new CommandeDAO();
    }

    @Override
    public Commande enregistrerCde(Commande commande) {
        if (commande.getStatut() == null) {
            // Utiliser la nouvelle valeur d'énumération
            commande.setStatut(Commande.StatutLivraison.EN_COURS);
        }
        return commandeDAO.save(commande);
    }

    @Override
    public void deleteCde(Long id) {
        if (commandeDAO.findById(id) == null) {
            throw new RuntimeException("Commande non trouvée avec l'ID: " + id);
        }
        commandeDAO.delete(id);
    }

    @Override
    public Commande updateStatut(Long id, Commande.StatutLivraison nouveauStatut) {
        // Vérifier que le statut n'est pas null
        if (nouveauStatut == null) {
            throw new IllegalArgumentException("Le statut ne peut pas être null");
        }

        Commande commande = commandeDAO.findById(id);
        if (commande != null) {
            commande.setStatut(nouveauStatut);

            // Si le statut est "LIVRE", on peut automatiquement mettre la date de livraison
            if (nouveauStatut == Commande.StatutLivraison.LIVRE && commande.getDateLivraison() == null) {
                commande.setDateLivraison(new Date());
            }

            // Si le statut est "ANNULE", on peut nettoyer la date de livraison
            if (nouveauStatut == Commande.StatutLivraison.ANNULE) {
                commande.setDateLivraison(null);
            }

            return commandeDAO.update(commande);
        } else {
            throw new RuntimeException("Commande non trouvée avec l'ID: " + id);
        }
    }

    @Override
    public List<Commande> getAllCommandes() {
        return commandeDAO.findAll();
    }

    @Override
    public Commande getCommandeById(Long id) {
        Commande commande = commandeDAO.findById(id);
        if (commande == null) {
            throw new RuntimeException("Commande non trouvée avec l'ID: " + id);
        }
        return commande;
    }

    @Override
    public List<Commande> getCommandesByStatut(Commande.StatutLivraison statut) {
        // Vérifier que le statut n'est pas null
        if (statut == null) {
            throw new IllegalArgumentException("Le statut ne peut pas être null");
        }

        return commandeDAO.findByStatut(statut);
    }}