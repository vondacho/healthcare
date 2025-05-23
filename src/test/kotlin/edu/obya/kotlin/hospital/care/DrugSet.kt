package edu.obya.kotlin.hospital.care;

import com.marcinmoskala.math.powerset

internal object DrugSet {

    private val ALL_SUBSETS: Set<Set<Drug>> = Drug.values().toSet().powerset()

    fun full(): Set<Drug> = Drug.values().toSet()

    fun allSubsetsHaving(drugs: Set<Drug>): Set<Set<Drug>> =
        ALL_SUBSETS.filter { drugs.all { m -> it.contains(m) } }.toSet()

    fun allSubsetsNotHaving(drugs: Set<Drug>): Set<Set<Drug>> =
        ALL_SUBSETS.filter { drugs.none { m -> it.contains(m) } }.toSet()

    fun allSubsets(having: Set<Drug>, notHaving: Set<Drug>): Set<Set<Drug>> =
        ALL_SUBSETS
            .filter { having.all { m -> it.contains(m) } }
            .filter { notHaving.none { m -> it.contains(m) } }
            .toSet()

    fun allSubsets(): Set<Set<Drug>> = ALL_SUBSETS

    fun empty(): Set<Drug> = setOf()
}
