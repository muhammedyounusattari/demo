package com.ticket.viewers.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

//import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
////@Document
//@Validated

@Table("viewer")
public class Viewers {

    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY) // use if you're using JPA; for R2DBC, custom handling
    private Long id;

//    @NotBlank(message = "viewer.ticketId must be present")
    private Integer ticketid;

//    @NotBlank(message = "viewer.name must be present")
    private String name;

//    @NotBlank(message = "viewer.lastLogin must be present")
    private LocalDate lastlogin;

//    @NotBlank(message = "viewer.status must be present")
    private Boolean status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTicketid() {
        return ticketid;
    }

    public void setTicketid(Integer ticketid) {
        this.ticketid = ticketid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(LocalDate lastlogin) {
        this.lastlogin = lastlogin;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
