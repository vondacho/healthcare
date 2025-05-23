package edu.obya.kotlin.hospital.care

import edu.obya.kotlin.hospital.care.EffectRuleSet.Companion.ruleOf
import edu.obya.kotlin.hospital.care.HealthStatus.*
import edu.obya.kotlin.hospital.care.Drug.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class EffectRuleSetTest {

    @Test
    fun `if no existing rule then no elected effect`() {
        assertThat(EffectRuleSet(listOf()).electEffect(Healthy, setOf())).isNull()
    }

    @Test
    fun `if no match with existing rule then elected effect is no effect`() {
        val safe = ruleOf(setOf(Fever), { it.contains(Paracetamol) }, { Healthy })
        val mortal = ruleOf(setOf(Fever), { it.containsAll(setOf(Aspirin, Paracetamol)) }, { Dead })
        val effectRuleSet = EffectRuleSet(listOf(mortal, safe))

        assertThat(effectRuleSet.electEffect(Healthy, setOf())).isNull()
        assertThat(effectRuleSet.electEffect(Fever, setOf())).isNull()
        assertThat(effectRuleSet.electEffect(Fever, setOf(Antibiotic))).isNull()
    }

    @Test
    fun `should elect a safe effect if no eligible mortal effect`() {
        val safe1 = ruleOf(setOf(Fever), { it.contains(Paracetamol) }, { Healthy })
        val safe2 = ruleOf(setOf(Fever), { it.contains(Aspirin) }, { Healthy })
        val mortal = ruleOf(setOf(Fever), { it.containsAll(setOf(Aspirin, Paracetamol)) }, { Dead })
        val effectRuleSet = EffectRuleSet(listOf(mortal, safe1, safe2))

        assertThat(effectRuleSet.electEffect(Fever, setOf(Aspirin))?.invoke(Fever)).isEqualTo(Healthy);
        assertThat(effectRuleSet.electEffect(Fever, setOf(Paracetamol))?.invoke(Fever)).isEqualTo(Healthy);
    }

    @Test
    fun `should elect a mortal effect first`() {
        val safe1 = ruleOf(setOf(Fever), { it.contains(Paracetamol) }, { Healthy })
        val safe2 = ruleOf(setOf(Fever), { it.contains(Aspirin) }, { Healthy })
        val mortal = ruleOf(setOf(Fever), { it.containsAll(setOf(Aspirin, Paracetamol)) }, { Dead })
        val effectRuleSet = EffectRuleSet(listOf(mortal, safe1, safe2))

        assertThat(effectRuleSet.electEffect(Fever, setOf(Aspirin, Paracetamol))?.invoke(Fever)).isEqualTo(Dead);
    }
}
