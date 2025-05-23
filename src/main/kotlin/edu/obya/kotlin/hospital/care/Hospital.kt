package edu.obya.kotlin.hospital.care

class Hospital(private val effectRuleSet: EffectRuleSet) {

    fun treat(patients: List<HealthStatus>, drugs: Set<Drug>): List<HealthStatus> =
        patients.map { treat(patient = it, drugs = drugs) }

    fun treat(patient: HealthStatus, drugs: Set<Drug>): HealthStatus =
        effectRuleSet.electEffect(state = patient, drugs = drugs)
            ?.invoke(patient)
            ?: patient

    companion object {
        fun create(miracleMaker: MiracleMaker) = Hospital(EffectRuleSet.create(miracleMaker))
        fun create(): Hospital = create(MiracleMaker.never())
    }
}
