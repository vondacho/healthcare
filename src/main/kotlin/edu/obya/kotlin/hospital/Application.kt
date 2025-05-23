package edu.obya.kotlin.hospital

import edu.obya.kotlin.hospital.care.Hospital
import edu.obya.kotlin.hospital.care.MiracleMaker
import edu.obya.kotlin.hospital.io.asDrugSet
import edu.obya.kotlin.hospital.io.asPatientList
import edu.obya.kotlin.hospital.io.format

fun main(args: Array<String>) {
    println(
        if (args.isEmpty())
            throw IllegalArgumentException("Please provide at least a patient list as argument")
        else
            Hospital
                .create(miracleMaker = MiracleMaker.oneEvery(1000000))
                .treat(
                    patients = args[0].asPatientList(),
                    drugs = if (args.size > 1) args[1].asDrugSet() else emptySet()
                ).format()
    )
}
