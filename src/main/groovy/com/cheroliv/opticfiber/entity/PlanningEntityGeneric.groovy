package com.cheroliv.opticfiber.entity


import java.time.LocalDateTime

interface PlanningEntityGeneric<ID, INTER extends InterEntityGeneric> extends Serializable {
    static final long serialVersionUID = 1L



    ID getId()

    void setId(ID id)

    String getInitialTech()

    void setInitialTech(String initialTech)

    Boolean getOpen()

    void setOpen(Boolean open)

    LocalDateTime getDateTimeCreation()

    void setDateTimeCreation(LocalDateTime dateTimeCreation)

    String getLastNameTech()

    void setLastNameTech(String lastNameTech)

    String getFirstNameTech()

    void setFirstNameTech(String firstNameTech)
}
