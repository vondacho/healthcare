package edu.obya.kotlin.hospital.care

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.offset
import org.junit.jupiter.api.Test

internal class MiracleMakerTest {

    @Test
    fun `sure miracle should occur`() {
        assertThat(MiracleMaker.sure().ask()).isTrue()
    }

    @Test
    fun `impossible miracle should not occur`() {
        assertThat(MiracleMaker.never().ask()).isFalse()
    }

    @Test
    fun `sure miracle should occur with given probability of one`() {
        assertThat(MiracleMaker.oneEvery(magnitude = 1).ask()).isTrue()
    }

    @Test
    fun `miracle should occur once with given probability`() {

        fun checkSingleOccurrenceProbability(
            attemptCount: Int,
            magnitude: Long,
            expectedHitProbability: Double,
            tolerance: Double) {

            var hitCount = 0
            repeat(attemptCount) {
                if (MiracleMaker.oneEvery(magnitude).ask()) hitCount++
            }
            assertThat(hitCount.toDouble() / attemptCount.toDouble()).isCloseTo(expectedHitProbability, offset(tolerance))
        }

        checkSingleOccurrenceProbability(1000, 2, 0.5, 0.05)
        checkSingleOccurrenceProbability(100000, 1000, 0.001, 0.0005)
        checkSingleOccurrenceProbability(100000000, 1000000, 0.000001, 0.0000005)
    }
}
