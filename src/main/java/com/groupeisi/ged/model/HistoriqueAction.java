package com.groupeisi.ged.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class HistoriqueAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;
    private Date dateAction;

    @ManyToOne
    private Utilisateur utilisateur;

    @ManyToOne
    private Document document;

    public HistoriqueAction() {
    }

    public HistoriqueAction(String action, Date dateAction, Utilisateur utilisateur, Document document) {
        this.action = action;
        this.dateAction = dateAction;
        this.utilisateur = utilisateur;
        this.document = document;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getDateAction() {
        return dateAction;
    }

    public void setDateAction(Date dateAction) {
        this.dateAction = dateAction;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

}