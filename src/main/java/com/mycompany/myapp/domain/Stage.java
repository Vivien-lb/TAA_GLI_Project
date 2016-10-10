package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Stage.
 */
@Entity
@Table(name = "stage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Stage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @Column(name = "adress")
    private String adress;

    @Column(name = "soon_ending")
    private ZonedDateTime soonEnding;

    @Column(name = "student")
    private Integer student;

    @OneToOne
    @JoinColumn(unique = true)
    private Convention convention;

    @ManyToOne
    private Company company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public Stage startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public Stage endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public String getAdress() {
        return adress;
    }

    public Stage adress(String adress) {
        this.adress = adress;
        return this;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public ZonedDateTime getSoonEnding() {
        return soonEnding;
    }

    public Stage soonEnding(ZonedDateTime soonEnding) {
        this.soonEnding = soonEnding;
        return this;
    }

    public void setSoonEnding(ZonedDateTime soonEnding) {
        this.soonEnding = soonEnding;
    }

    public Integer getStudent() {
        return student;
    }

    public Stage student(Integer student) {
        this.student = student;
        return this;
    }

    public void setStudent(Integer student) {
        this.student = student;
    }

    public Convention getConvention() {
        return convention;
    }

    public Stage convention(Convention convention) {
        this.convention = convention;
        return this;
    }

    public void setConvention(Convention convention) {
        this.convention = convention;
    }

    public Company getCompany() {
        return company;
    }

    public Stage company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stage stage = (Stage) o;
        if(stage.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, stage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Stage{" +
            "id=" + id +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", adress='" + adress + "'" +
            ", soonEnding='" + soonEnding + "'" +
            ", student='" + student + "'" +
            '}';
    }
}
