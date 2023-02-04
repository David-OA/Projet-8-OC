package com.openclassrooms.realestatemanager.utils

import java.util.*

var idGeneratedProperty: String = ""
    get() {
        field = UUID.randomUUID().toString()
        return field
    }


