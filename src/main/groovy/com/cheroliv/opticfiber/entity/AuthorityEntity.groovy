package com.cheroliv.opticfiber.entity


import groovy.transform.ToString
import groovy.transform.TypeChecked

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@ToString
@TypeChecked
@Table(name = "`authority`")
class AuthorityEntity implements Serializable {
    static final long serialVersionUID = 1L
    @NotNull
    @Size(max = 50)
    @Id
    @Column(length = 50, name = "`name`")
    String name

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (this.class != o.class) return false
        AuthorityEntity authority = o as AuthorityEntity
        return name == authority.name
    }

    @Override
    int hashCode() {
        name != null ? name.hashCode() : 0
    }
}
