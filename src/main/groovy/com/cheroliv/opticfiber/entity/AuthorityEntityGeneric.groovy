package com.cheroliv.opticfiber.entity

interface AuthorityEntityGeneric <ID> extends Serializable {
    static final long serialVersionUID = 1L
    ID getName()

    void setName(ID id)
}