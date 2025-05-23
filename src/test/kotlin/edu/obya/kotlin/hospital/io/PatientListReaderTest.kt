package edu.obya.kotlin.hospital.io

import edu.obya.kotlin.hospital.care.HealthStatus
import edu.obya.kotlin.hospital.care.HealthStatus.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

internal class PatientListReaderTest {

    @Test
    fun `reader should parse valid single patient accordingly`() {
        values().forEach {
            assertThat(it.acronym.asPatientList()).isEqualTo(listOf(it))
        }
    }

    @Test
    fun `reader should parse valid multi patient list accordingly`() {
        assertThat(" F,H,D,T,X, F,H,D,T,X ".asPatientList()).isEqualTo(
            listOf(Fever, Healthy, Diabetes, Tuberculosis, Dead, Fever, Healthy, Diabetes, Tuberculosis, Dead)
        )
    }

    @Test
    fun `reader should parse empty patient list accordingly`() {
        assertThat("".asPatientList()).isEqualTo(listOf<HealthStatus>())
    }

    @Test
    fun `reader should detect illegal syntax for list`() {
        assertFailsWith<IllegalArgumentException> { "F,".asPatientList() }
        assertFailsWith<IllegalArgumentException> { "F;H".asPatientList() }
    }

    @Test
    fun `reader should detect unknown health status`() {
        assertFailsWith<IllegalArgumentException> { "a".asPatientList() }
    }
}
