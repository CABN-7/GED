package com.groupeisi.ged.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String cheminFichier;
    private Date dateCreation;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur proprietaire;

    @ManyToOne
    private TypeDocument type;

    @ManyToOne
    private Dossier dossier;

    @Column(nullable = false)
    private String statut = "EN_ATTENTE"; // ou utilise un enum si tu préfères


    // Constructeurs
    public Document() {
    }

    public Document(String titre, String cheminFichier, Date dateCreation, Utilisateur proprietaire, TypeDocument type, Dossier dossier) {
        this.titre = titre;
        this.cheminFichier = cheminFichier;
        this.dateCreation = dateCreation;
        this.proprietaire = proprietaire;
        this.type = type;
        this.dossier = dossier;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Utilisateur getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Utilisateur proprietaire) {
        this.proprietaire = proprietaire;
    }

    public TypeDocument getType() {
        return type;
    }

    public void setType(TypeDocument type) {
        this.type = type;
    }

    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }
}
