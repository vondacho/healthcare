package edu.obya.kotlin.hospital.care

import edu.obya.kotlin.hospital.care.Drug.Insulin
import edu.obya.kotlin.hospital.care.Drug.values
import com.marcinmoskala.math.pow
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class DrugSetTest {

    @Test
    fun `should return all medicines`() {
        assertThat(DrugSet.full()).containsExactlyInAnyOrder(*values())
    }

    @Test
    fun `should return no medicine`() {
        assertThat(DrugSet.empty()).hasSize(0)
    }

    @Test
    fun `should return any mixture of medicines`() {
        //when
        val result = DrugSet.allSubsets()
        //then
        assertThat(result).hasSize(2.pow(values().size))
        assertThat(result).doesNotHaveDuplicates()
    }

    @Test
    fun `should return any mixture of medicines but insulin`() {
        //when
        val result = DrugSet.allSubsetsNotHaving(setOf(Insulin))
        //then
        assertThat(result).hasSize(2.pow(values().size - 1))
        assertThat(result).doesNotHaveDuplicates()
    }

    @Test
    fun `should return any mixture of medicines including insulin`() {
        //when
        val result = DrugSet.allSubsetsHaving(setOf(Insulin))
        //then
        assertThat(result).hasSize(2.pow(values().size - 1))
        assertThat(result).doesNotHaveDuplicates()
    }
}
