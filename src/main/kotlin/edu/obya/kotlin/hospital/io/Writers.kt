package edu.obya.kotlin.hospital.io

import edu.obya.kotlin.hospital.care.HealthStatus

fun List<HealthStatus>.format(): String = HealthStatus.values()
    .associate { it.acronym to 0 }
    .plus(this.groupingBy { it.acronym }.eachCount())
    .map { "${it.key}:${it.value}" }
    .joinToString(separator = ",")
