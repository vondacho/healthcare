package edu.obya.kotlin.hospital.io

import edu.obya.kotlin.hospital.care.HealthStatus
import edu.obya.kotlin.hospital.care.HealthStatus.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PatientSummaryFormatTest {

    @Test
    fun `should format summary of patient list accordingly`() {
        assertThat(listOf<HealthStatus>().format()).isEqualTo("F:0,H:0,D:0,T:0,X:0");
        assertThat(listOf(Fever).format()).isEqualTo("F:1,H:0,D:0,T:0,X:0");
        assertThat(listOf(Fever, Fever).format()).isEqualTo("F:2,H:0,D:0,T:0,X:0");
        assertThat(listOf(Fever, Healthy, Diabetes, Tuberculosis, Dead).format()).isEqualTo("F:1,H:1,D:1,T:1,X:1");
        assertThat(listOf(Fever, Healthy, Healthy, Healthy, Dead).format()).isEqualTo("F:1,H:3,D:0,T:0,X:1");
    }
}
