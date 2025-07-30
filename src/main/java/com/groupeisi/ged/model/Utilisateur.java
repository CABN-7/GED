package com.groupeisi.ged.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Utilisateur {

    public enum Role {
        UTILISATEUR,
        GESTIONNAIRE,
        ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private boolean actif = true; // Compte actif par défaut

    @Column(nullable = false)
    private boolean doitChangerMotDePasse = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private java.util.Date dateCreation = new java.util.Date();


    @OneToMany(mappedBy = "proprietaire")
    private List<Document> documents;

    // Constructeurs
    public Utilisateur() {
    }

    public Utilisateur(String nom, String prenom, String email, String motDePasse, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
        this.actif = true;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public boolean isDoitChangerMotDePasse() {
        return doitChangerMotDePasse;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public Role getRole() {
        return role;
    }

    public boolean isActif() {
        return actif;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    // Setters
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public void setDoitChangerMotDePasse(boolean doitChangerMotDePasse) {
        this.doitChangerMotDePasse = doitChangerMotDePasse;
    }
}
