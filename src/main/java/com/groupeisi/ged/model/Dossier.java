package com.groupeisi.ged.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Dossier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany(mappedBy = "dossier")
    private List<Document> documents;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Dossier> sousDossiers;

    @ManyToOne
    private Dossier parent;

    @ManyToOne
    @JoinColumn(name = "proprietaire_id")
    private Utilisateur proprietaire;

    // Flag pour identifier le dossier racine
    private boolean isRacine = false;

    public Dossier() {
    }

    public Dossier(String nom) {
        this.nom = nom;
    }

    public Dossier(String nom, Utilisateur proprietaire) {
        this.nom = nom;
        this.proprietaire = proprietaire;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<Dossier> getSousDossiers() {
        return sousDossiers;
    }

    public void setSousDossiers(List<Dossier> sousDossiers) {
        this.sousDossiers = sousDossiers;
    }

    public Dossier getParent() {
        return parent;
    }

    public void setParent(Dossier parent) {
        this.parent = parent;
    }

    public Utilisateur getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Utilisateur proprietaire) {
        this.proprietaire = proprietaire;
    }

    public boolean isRacine() {
        return isRacine;
    }

    public void setRacine(boolean racine) {
        isRacine = racine;
    }
}