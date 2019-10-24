package com.cheroliv.core.entity.dao

interface ExtendedRepository<T, ID extends Serializable> {
    List<T> findByAttributeContainsText(String attributeName, String text)

    T updateWith(T with, ID id)
}