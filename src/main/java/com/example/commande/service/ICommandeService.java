package com.example.commande.service;

import com.example.commande.model.Commande;
import java.util.List;

public interface ICommandeService {

    // Enregistrer une nouvelle commande
    Commande enregistrerCde(Commande commande);

    // Supprimer une commande par son ID
    void deleteCde(Long id);

    // Mettre à jour le statut d'une commande (version enum)
    Commande updateStatut(Long id, Commande.StatutLivraison nouveauStatut);

    // Méthodes supplémentaires
    List<Commande> getAllCommandes();

    Commande getCommandeById(Long id);

    List<Commande> getCommandesByStatut(Commande.StatutLivraison statut);

    default Commande.StatutLivraison convertirStringVersStatut(String statutStr) {
        if (statutStr == null) {
            return null;
        }
        try {
            // Convertir pour correspondre aux noms de l'énumération corrigée
            String normalized = statutStr.toUpperCase().trim();
            // Gérer les variations possibles
            if ("ENCOURS".equals(normalized) || "EN_COURS".equals(normalized)) {
                return Commande.StatutLivraison.EN_COURS;
            }
            return Commande.StatutLivraison.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Statut invalide. Les statuts valides sont: EN_COURS, LIVRE, ANNULE");
        }
    }
}