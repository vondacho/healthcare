package edu.obya.kotlin.hospital.io;

import edu.obya.kotlin.hospital.care.Drug
import edu.obya.kotlin.hospital.care.Drug.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

internal class DrugSetReaderTest {

    @Test
    fun `reader should parse one valid single medicine accordingly`() {
        values().forEach {
            assertThat(it.acronym.asDrugSet()).isEqualTo(setOf(it))
        }
    }

    @Test
    fun `reader should parse valid multi medicine set accordingly`() {
        //when-then
        assertThat(" As,An, I,P ".asDrugSet()).isEqualTo(setOf(Aspirin, Antibiotic, Insulin, Paracetamol))
        assertThat(" P,P ".asDrugSet()).isEqualTo(setOf(Paracetamol))
    }

    @Test
    fun `reader should parse empty medicine set accordingly`() {
        assertThat("".asDrugSet()).isEqualTo(setOf<Drug>())
    }

    @Test
    fun `reader should detect illegal syntax for medicine set`() {
        assertFailsWith<IllegalArgumentException> { "As,".asDrugSet() }
        assertFailsWith<IllegalArgumentException> { "I;P".asDrugSet() }
    }

    @Test
    fun `reader should detect unknown medicine`() {
        assertFailsWith<IllegalArgumentException> { "a".asDrugSet() }
    }
}
