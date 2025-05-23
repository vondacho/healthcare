package edu.obya.kotlin.hospital

import edu.obya.kotlin.hospital.care.Hospital
import edu.obya.kotlin.hospital.care.MiracleMaker
import edu.obya.kotlin.hospital.io.asDrugSet
import edu.obya.kotlin.hospital.io.asPatientList
import edu.obya.kotlin.hospital.io.format
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class AcceptanceTests {

    @Test
    fun `diabetic patients die without insulin`() {
        val patients = "D,D".asPatientList()
        val drugs = "".asDrugSet()

        val result = Hospital.create().treat(patients, drugs).format()

        assertThat(result).isEqualTo("F:0,H:0,D:0,T:0,X:2")
    }

    @Test
    fun `paracetamol cures fever`() {
        val patients = "F".asPatientList()
        val drugs = "P".asDrugSet()

        val result = Hospital.create().treat(patients, drugs).format()

        assertThat(result).isEqualTo("F:0,H:1,D:0,T:0,X:0")
    }

    @Test
    fun `sure miracle resurrects at all dead patients`() {
        val patients = "X,X".asPatientList()
        val drugs = "".asDrugSet()

        val result = Hospital.create(MiracleMaker.sure()).treat(patients, drugs).format()

        assertThat(result).isEqualTo("F:0,H:2,D:0,T:0,X:0")
    }
}
