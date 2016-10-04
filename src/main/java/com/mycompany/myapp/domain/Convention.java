package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Convention.
 */
@Entity
@Table(name = "convention")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Convention implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "sujet")
    private String sujet;

    @Column(name = "student_signature")
    private Boolean studentSignature;

    @Column(name = "contact_signature")
    private Boolean contactSignature;

    @Column(name = "university_signature")
    private Boolean universitySignature;

    @Column(name = "salary")
    private Double salary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSujet() {
        return sujet;
    }

    public Convention sujet(String sujet) {
        this.sujet = sujet;
        return this;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public Boolean isStudentSignature() {
        return studentSignature;
    }

    public Convention studentSignature(Boolean studentSignature) {
        this.studentSignature = studentSignature;
        return this;
    }

    public void setStudentSignature(Boolean studentSignature) {
        this.studentSignature = studentSignature;
    }

    public Boolean isContactSignature() {
        return contactSignature;
    }

    public Convention contactSignature(Boolean contactSignature) {
        this.contactSignature = contactSignature;
        return this;
    }

    public void setContactSignature(Boolean contactSignature) {
        this.contactSignature = contactSignature;
    }

    public Boolean isUniversitySignature() {
        return universitySignature;
    }

    public Convention universitySignature(Boolean universitySignature) {
        this.universitySignature = universitySignature;
        return this;
    }

    public void setUniversitySignature(Boolean universitySignature) {
        this.universitySignature = universitySignature;
    }

    public Double getSalary() {
        return salary;
    }

    public Convention salary(Double salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Convention convention = (Convention) o;
        if(convention.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, convention.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Convention{" +
            "id=" + id +
            ", sujet='" + sujet + "'" +
            ", studentSignature='" + studentSignature + "'" +
            ", contactSignature='" + contactSignature + "'" +
            ", universitySignature='" + universitySignature + "'" +
            ", salary='" + salary + "'" +
            '}';
    }
}
