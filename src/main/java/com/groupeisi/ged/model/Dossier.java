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

    @OneToMany(mappedBy = "parent")
    private List<Dossier> sousDossiers;

    @ManyToOne
    private Dossier parent;


    public Dossier() {
    }

    public Dossier(String nom) {
        this.nom = nom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public List<Dossier> getSousDossiers() {
        return sousDossiers;
    }

    public Dossier getParent() {
        return parent;
    }

    public void setParent(Dossier parent) {
        this.parent = parent;
    }

    public void setSousDossiers(List<Dossier> sousDossiers) {
        this.sousDossiers = sousDossiers;
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

}
