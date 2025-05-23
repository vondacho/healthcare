package edu.obya.kotlin.hospital.care

import edu.obya.kotlin.hospital.care.HealthStatus.*
import edu.obya.kotlin.hospital.care.Drug.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class HospitalTests {

    private val hospital = Hospital.create()

    @Test
    fun `should make patient healthy with the right medicine`() {
        DrugSet.allSubsets(setOf(Aspirin), setOf(Aspirin, Paracetamol)).onEach {
            assertThat(hospital.treat(patient = Fever, drugs = it)).isEqualTo(Healthy)
        }
        DrugSet.allSubsets(setOf(Paracetamol), setOf(Aspirin, Paracetamol)).onEach {
            assertThat(hospital.treat(patient = Fever, drugs = it)).isEqualTo(Healthy)
        }
        DrugSet.allSubsets(setOf(Antibiotic), setOf(Aspirin, Paracetamol)).onEach {
            assertThat(hospital.treat(patient = Tuberculosis, drugs = it)).isEqualTo(Healthy)
        }
    }

    @Test
    fun `should let patient survive with insulin`() {
        DrugSet.allSubsets(setOf(Insulin), setOf(Aspirin, Paracetamol)).onEach {
            assertThat(hospital.treat(patient = Diabetes, drugs = it)).isEqualTo(Diabetes)
        }
    }

    @Test
    fun `should care more than one patient at once`() {
        val patients = listOf(Diabetes, Diabetes)
        val result = hospital.treat(patients, drugs = setOf(Insulin))

        assertThat(result).hasSize(patients.size)
        assertThat(result).isEqualTo(listOf(Diabetes, Diabetes))
    }

    @Test
    fun `should let patient remain sick with the wrong medicine`() {
        DrugSet.allSubsetsNotHaving(setOf(Aspirin, Paracetamol)).onEach {
            assertThat(hospital.treat(patient = Fever, drugs = it)).isEqualTo(Fever)
        }
        DrugSet.allSubsetsNotHaving(setOf(Antibiotic, Aspirin, Paracetamol))
            .onEach { assertThat(hospital.treat(patient = Tuberculosis, drugs = it)).isEqualTo(Tuberculosis)
        }
        DrugSet.allSubsetsNotHaving(setOf(Antibiotic, Paracetamol)).onEach {
            assertThat(hospital.treat(patient = Tuberculosis, drugs = it)).isEqualTo(Tuberculosis)
        }
        DrugSet.allSubsetsNotHaving(setOf(Antibiotic, Aspirin)).onEach {
            assertThat(hospital.treat(patient = Tuberculosis, drugs = it)).isEqualTo(Tuberculosis)
        }
    }

    @Test
    fun `should make healthy patient catch fever with insulin and antibiotic`() {
        DrugSet.allSubsets(setOf(Insulin, Antibiotic), setOf(Aspirin, Paracetamol)).onEach {
            assertThat(hospital.treat(patient = Healthy, drugs = it)).isEqualTo(Fever)
        }
    }

    @Test
    fun `should not resurrect dead patient with any mixture of medicine nor miracle occurrence`() {
        val hospital = Hospital.create(MiracleMaker.never())

        DrugSet.allSubsets().onEach {
            assertThat(hospital.treat(patient = Dead, drugs = it)).isEqualTo(Dead)
        }
    }

    @Test
    fun `should resurrect dead patient without medicine but with sure miracle occurrence`() {
        val hospital = Hospital.create(MiracleMaker.sure())

        assertThat(hospital.treat(patient = Dead, drugs = setOf())).isEqualTo(Healthy)
    }

    @Test
    fun `should kill patient with paracetamol and aspirin`() {
        DrugSet.allSubsetsHaving(setOf(Paracetamol, Aspirin)).onEach {
            assertThat(hospital.treat(patient = Fever, drugs = it)).isEqualTo(Dead)
            assertThat(hospital.treat(patient = Healthy, drugs = it)).isEqualTo(Dead)
            assertThat(hospital.treat(patient = Diabetes, drugs = it)).isEqualTo(Dead)
            assertThat(hospital.treat(patient = Tuberculosis, drugs = it)).isEqualTo(Dead)
        }
    }

    @Test
    fun `should kill patient suffering diabetes without insulin`() {
        DrugSet.allSubsetsNotHaving(setOf(Insulin)).onEach {
            assertThat(hospital.treat(patient = Diabetes, drugs = it)).isEqualTo(Dead)
        }
    }

    @Test
    fun `should resurrect diabetic patient dying without insulin`() {
        val hospital = Hospital.create(MiracleMaker.sure())

        DrugSet.allSubsetsNotHaving(setOf(Insulin)).onEach {
            assertThat(hospital.treat(patient = Diabetes, drugs = it)).isEqualTo(Healthy)
        }
    }
}
