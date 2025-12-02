package com.example.commande.web;

import com.example.commande.model.Commande;
import com.example.commande.service.CommandeServiceImpl;
import com.example.commande.service.ICommandeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/commandes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommandeResource {

    private ICommandeService commandeService = new CommandeServiceImpl();
    private ObjectMapper objectMapper = new ObjectMapper();

    @POST
    public Response createCommande(Commande commande) {
        try {
            Commande nouvelleCommande = commandeService.enregistrerCde(commande);
            return Response.status(Response.Status.CREATED).entity(nouvelleCommande).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @GET
    public Response getAllCommandes() {
        try {
            List<Commande> commandes = commandeService.getAllCommandes();
            return Response.ok(commandes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getCommandeById(@PathParam("id") Long id) {
        try {
            Commande commande = commandeService.getCommandeById(id);
            if (commande != null) {
                return Response.ok(commande).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Commande non trouvée\"}").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @PUT
    @Path("/{id}/statut")
    public Response updateStatut(@PathParam("id") Long id, String nouveauStatut) {
        try {
            String statut = objectMapper.readTree(nouveauStatut).get("statut").asText();
            Commande commande = commandeService.updateStatut(id, Commande.StatutLivraison.valueOf(statut));
            return Response.ok(commande).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCommande(@PathParam("id") Long id) {
        try {
            commandeService.deleteCde(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @GET
    @Path("/statut/{statut}")
    public Response getCommandesByStatut(@PathParam("statut") String statut) {
        try {
            List<Commande> commandes = commandeService.getCommandesByStatut(Commande.StatutLivraison.valueOf(statut));
            return Response.ok(commandes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }
}