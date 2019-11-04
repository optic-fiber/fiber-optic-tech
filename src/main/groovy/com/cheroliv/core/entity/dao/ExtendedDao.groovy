package com.cheroliv.core.entity.dao

import groovy.transform.TypeChecked

@TypeChecked
interface ExtendedDao<T, ID extends Serializable> {
    List<T> findByAttributeContainsText(String attributeName, String text)

    T updateWith(T with, ID id)
}