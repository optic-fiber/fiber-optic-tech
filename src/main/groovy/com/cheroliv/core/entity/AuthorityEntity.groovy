package com.cheroliv.core.entity

import com.cheroliv.core.domain.AuthorityDto
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
class AuthorityEntity implements AuthorityEntityGeneric<String> {

    @Id
    @NotNull
    @Size(max = 50)
    @Column(length = 50, name = "`name`")
    String name

    static AuthorityDto fromEntity(AuthorityEntity a) {
        new AuthorityDto(name: a.name)
    }

    static AuthorityEntity fromDto(AuthorityDto dto) {
        new AuthorityEntity(name: dto.name)
    }


    AuthorityDto toDto(){
        fromEntity(this)
    }
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
