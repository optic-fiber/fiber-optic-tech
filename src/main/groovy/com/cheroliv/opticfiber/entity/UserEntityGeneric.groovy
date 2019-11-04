package com.cheroliv.opticfiber.entity

import java.time.Instant

interface UserEntityGeneric<ID, AUTHORITY extends AuthorityEntityGeneric> extends Serializable {
    static final long serialVersionUID = 1L
    static final String LOGIN_REGEX = '^[_.@A-Za-z0-9-]*$'

    static final String SYSTEM_ACCOUNT = "system"
    static final String DEFAULT_LANGUAGE = "fr"
    static final String ANONYMOUS_USER = "anonymoususer"

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

    Set<AUTHORITY> getAuthorities()

    void setAuthorities(Set<AUTHORITY> authorities)

}