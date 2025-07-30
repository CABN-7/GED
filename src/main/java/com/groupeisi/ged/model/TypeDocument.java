package com.groupeisi.ged.model;

import jakarta.persistence.*;
        import java.util.List;

@Entity
public class TypeDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;

    @OneToMany(mappedBy = "type")
    private List<Document> documents;

    
    public TypeDocument() {
    }

    public TypeDocument(String libelle) {
        this.libelle = libelle;
    }

    public TypeDocument(Long id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}
