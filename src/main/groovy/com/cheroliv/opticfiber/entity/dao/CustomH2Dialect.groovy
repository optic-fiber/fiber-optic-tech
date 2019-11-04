package com.cheroliv.opticfiber.entity.dao


import org.hibernate.dialect.H2Dialect

import java.sql.Types

class CustomH2Dialect extends H2Dialect {

    CustomH2Dialect() {
        super()
        registerColumnType Types.FLOAT, "real"
    }
}
