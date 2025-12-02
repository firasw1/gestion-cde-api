package com.example.commande.dao;

import com.example.commande.model.Commande;
import com.example.commande.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAO {

    private Connection connection;

    public CommandeDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public Commande save(Commande commande) {
        String sql = "INSERT INTO commande (datecde, statut, refcode, nomclient, telclient, nomproduit, qte, prixu, datelivraison) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Date de création - toujours la date système
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));

            // Convertir l'enum en String pour la base de données
            stmt.setString(2, commande.getStatut().name()); // Utilisation de .name()
            stmt.setString(3, commande.getRefCode());
            stmt.setString(4, commande.getNomClient());
            stmt.setString(5, commande.getTelClient());
            stmt.setString(6, commande.getNomProduit());
            stmt.setInt(7, commande.getQuantite());
            stmt.setDouble(8, commande.getPrixUnitaire());

            // Date de livraison - peut être null
            if (commande.getDateLivraison() != null) {
                stmt.setDate(9, new java.sql.Date(commande.getDateLivraison().getTime()));
            } else {
                stmt.setNull(9, Types.DATE);
            }

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        commande.setId(rs.getLong(1));
                    }
                }
            }
            return commande;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de la commande: " + e.getMessage(), e);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM commande WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Aucune commande trouvée avec l'ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de la commande: " + e.getMessage(), e);
        }
    }

    public Commande update(Commande commande) {
        String sql = "UPDATE commande SET statut = ?, nomclient = ?, telclient = ?, " +
                "nomproduit = ?, qte = ?, prixu = ?, datelivraison = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Convertir l'enum en String pour la base de données
            stmt.setString(1, commande.getStatut().name()); // Utilisation de .name()
            stmt.setString(2, commande.getNomClient());
            stmt.setString(3, commande.getTelClient());
            stmt.setString(4, commande.getNomProduit());
            stmt.setInt(5, commande.getQuantite());
            stmt.setDouble(6, commande.getPrixUnitaire());

            // Date de livraison - peut être null
            if (commande.getDateLivraison() != null) {
                stmt.setDate(7, new java.sql.Date(commande.getDateLivraison().getTime()));
            } else {
                stmt.setNull(7, Types.DATE);
            }

            stmt.setLong(8, commande.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Aucune commande trouvée avec l'ID: " + commande.getId());
            }
            return commande;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la commande: " + e.getMessage(), e);
        }
    }

    public Commande findById(Long id) {
        String sql = "SELECT * FROM commande WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToCommande(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la commande: " + e.getMessage(), e);
        }
    }

    public List<Commande> findAll() {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commande ORDER BY datecde DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                commandes.add(mapResultSetToCommande(rs));
            }
            return commandes;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des commandes: " + e.getMessage(), e);
        }
    }

    public List<Commande> findByStatut(Commande.StatutLivraison statut) { // Changé le paramètre
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commande WHERE statut = ? ORDER BY datecde DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Convertir l'enum en String pour la base de données
            stmt.setString(1, statut.name()); // Utilisation de .name()
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                commandes.add(mapResultSetToCommande(rs));
            }
            return commandes;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche par statut: " + e.getMessage(), e);
        }
    }

    private Commande mapResultSetToCommande(ResultSet rs) throws SQLException {
        Commande commande = new Commande();
        commande.setId(rs.getLong("id"));

        // Date de création
        Timestamp dateCreation = rs.getTimestamp("datecde");
        if (dateCreation != null) {
            commande.setDateCreation(new java.util.Date(dateCreation.getTime()));
        }

        // Convertir le String de la base de données en enum
        String statutStr = rs.getString("statut");
        if (statutStr != null) {
            commande.setStatut(Commande.StatutLivraison.valueOf(statutStr)); // Utilisation de valueOf()
        }

        commande.setRefCode(rs.getString("refcode"));
        commande.setNomClient(rs.getString("nomclient"));
        commande.setTelClient(rs.getString("telclient"));
        commande.setNomProduit(rs.getString("nomproduit"));
        commande.setQuantite(rs.getInt("qte"));
        commande.setPrixUnitaire(rs.getDouble("prixu"));

        // Date de livraison - peut être null
        java.sql.Date dateLivraison = rs.getDate("datelivraison");
        if (dateLivraison != null) {
            commande.setDateLivraison(new java.util.Date(dateLivraison.getTime()));
        }
        // Sinon, dateLivraison reste null

        return commande;
    }
}