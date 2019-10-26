package com.cheroliv.core.entity

import java.time.Instant

interface UserEntityGeneric<ID, AUTHORITIES extends AuthorityEntityGeneric> extends Serializable {
    static final long serialVersionUID = 1L

    ID getId()

    void setId(ID id)

    String getLogin()

    void setLogin(String login)

    String getPassword()

    void setPassword(String password)

    String getEmail()

    void setEmail(String email)

    String getLangKey()

    void setLangKey(String langKey)

    String getImageUrl()

    void setImageUrl(String imageUrl)

    String getActivationKey()

    void setActivationKey(String activationKey)


    String getResetKey()

    void setResetKey(String resetKey)

    Instant getResetDate()

    void setResetDate(Instant resetDate)

    Instant getCreatedDate()

    void setCreatedDate(Instant createdDate)

    Set<AUTHORITIES> getAuthorities()

    void setAuthorities(Set<AUTHORITIES> authorities)

}