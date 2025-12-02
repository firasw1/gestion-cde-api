package com.example.commande.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class Commande {
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date dateCreation;

    private StatutLivraison statut; // ← Changé en enum
    private String refCode;
    private String nomClient;
    private String telClient;
    private String nomProduit;
    private Integer quantite;
    private Double prixUnitaire;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateLivraison;

    // Remplacer l'énumération par :
    public enum StatutLivraison {
        EN_COURS("En cours"),
        LIVRE("Livré"),
        ANNULE("Annulé");

        private final String libelle;

        StatutLivraison(String libelle) {
            this.libelle = libelle;
        }

        public String getLibelle() {
            return libelle;
        }
    }

    // Constructeur
    public Commande() {
        this.dateCreation = new Date();
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }

    public StatutLivraison getStatut() { return statut; }
    public void setStatut(StatutLivraison statut) { this.statut = statut; }

    public String getRefCode() { return refCode; }
    public void setRefCode(String refCode) { this.refCode = refCode; }

    public String getNomClient() { return nomClient; }
    public void setNomClient(String nomClient) { this.nomClient = nomClient; }

    public String getTelClient() { return telClient; }
    public void setTelClient(String telClient) { this.telClient = telClient; }

    public String getNomProduit() { return nomProduit; }
    public void setNomProduit(String nomProduit) { this.nomProduit = nomProduit; }

    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }

    public Double getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(Double prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public Date getDateLivraison() { return dateLivraison; }
    public void setDateLivraison(Date dateLivraison) { this.dateLivraison = dateLivraison; }

    // Méthode utilitaire
    public Double getPrixTotal() {
        return quantite != null && prixUnitaire != null ? quantite * prixUnitaire : 0.0;
    }
}