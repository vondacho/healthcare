package edu.obya.kotlin.hospital.io

import edu.obya.kotlin.hospital.care.Drug
import edu.obya.kotlin.hospital.care.HealthStatus

fun String.asPatientList() =
    if (Regex("^(\\s*[A-Za-z]*)(\\s*,\\s*[A-Za-z]+\\s*)*$").matches(this))
        this
            .split(",")
            .filter { it.isNotEmpty() }
            .map { it.trim() }
            .map { it.asHealthStatus() }
            .toList()
    else throw IllegalArgumentException("Illegal syntax of patient list")

fun String.asHealthStatus() =
    HealthStatus.values().find { it.acronym.equals(this, true) }
        ?: throw IllegalArgumentException("Unknown health status: $this")

fun String.asDrugSet() =
    if (Regex("^(\\s*[A-Za-z]*)(\\s*,\\s*[A-Za-z]+\\s*)*$").matches(this))
        this
            .split(",")
            .filter { it.isNotEmpty() }
            .map { it.trim() }
            .map { it.asDrug() }
            .toSet()
    else throw IllegalArgumentException("Illegal syntax of drug set")

fun String.asDrug() =
    Drug.values().find { it.acronym.equals(this, true) }
        ?: throw IllegalArgumentException("Unknown drug: $this")
