package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "siret")
    private Integer siret;

    @Column(name = "name")
    private String name;

    @Column(name = "adress")
    private String adress;

    @Column(name = "mail")
    private String mail;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Stage> stages = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Contact> contacts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSiret() {
        return siret;
    }

    public Company siret(Integer siret) {
        this.siret = siret;
        return this;
    }

    public void setSiret(Integer siret) {
        this.siret = siret;
    }

    public String getName() {
        return name;
    }

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public Company adress(String adress) {
        this.adress = adress;
        return this;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getMail() {
        return mail;
    }

    public Company mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Set<Stage> getStages() {
        return stages;
    }

    public Company stages(Set<Stage> stages) {
        this.stages = stages;
        return this;
    }

    public Company addStage(Stage stage) {
        stages.add(stage);
        stage.setCompany(this);
        return this;
    }

    public Company removeStage(Stage stage) {
        stages.remove(stage);
        stage.setCompany(null);
        return this;
    }

    public void setStages(Set<Stage> stages) {
        this.stages = stages;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public Company contacts(Set<Contact> contacts) {
        this.contacts = contacts;
        return this;
    }

    public Company addContact(Contact contact) {
        contacts.add(contact);
        contact.setCompany(this);
        return this;
    }

    public Company removeContact(Contact contact) {
        contacts.remove(contact);
        contact.setCompany(null);
        return this;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Company company = (Company) o;
        if(company.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + id +
            ", siret='" + siret + "'" +
            ", name='" + name + "'" +
            ", adress='" + adress + "'" +
            ", mail='" + mail + "'" +
            '}';
    }
}
