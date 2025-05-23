package edu.obya.kotlin.hospital.care

import edu.obya.kotlin.hospital.care.HealthStatus.*
import edu.obya.kotlin.hospital.care.Drug.*

typealias Effect = (HealthStatus) -> HealthStatus
typealias Rule = (HealthStatus, Set<Drug>) -> Effect?

class EffectRuleSet(private val rules: List<Rule>) {

    fun electEffect(state: HealthStatus, drugs: Set<Drug>): Effect? = electEffect(rules, state, drugs)

    private fun electEffect(rules: List<Rule>, state: HealthStatus, drugs: Set<Drug>): Effect? =
        rules.mapNotNull { it(state, drugs) }.firstOrNull()

    companion object {
        fun create(miracleMaker: MiracleMaker): EffectRuleSet {

            val resurrectionRule: Rule = ruleOf(setOf(Dead), { miracleMaker.ask() }, { Healthy })
            val resurrectionEffect: Effect = resurrectionRule(Dead, setOf()) ?: { Dead }

            return EffectRuleSet(
                rules = listOf(
                    resurrectionRule,
                    ruleOf(setOf(), { it.containsAll(setOf(Paracetamol, Aspirin)) }, resurrectionEffect),
                    ruleOf(setOf(Diabetes), { !it.contains(Insulin) }, resurrectionEffect),
                    ruleOf(setOf(Healthy), { it.containsAll(setOf(Antibiotic, Insulin)) }, { Fever }),
                    ruleOf(setOf(Fever), { it.contains(Aspirin) }, { Healthy }),
                    ruleOf(setOf(Fever), { it.contains(Paracetamol) }, { Healthy }),
                    ruleOf(setOf(Tuberculosis), { it.contains(Antibiotic) }, { Healthy })
                )
            )
        }

        internal fun ruleOf(
            initial: Set<HealthStatus>,
            trigger: (Set<Drug>) -> Boolean,
            effect: Effect
        ): Rule = { s, m -> if ((initial.isEmpty() || initial.contains(s)) && trigger(m)) effect else null }
    }

}
