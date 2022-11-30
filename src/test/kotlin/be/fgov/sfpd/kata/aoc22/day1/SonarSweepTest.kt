import DepthChange.DECREASED
import DepthChange.INCREASED
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class SonarSweepTest {
    @Test
    internal fun `given a sweep report, we measure the depth changes`() {
        val report = listOf(1, 2, 3)
        val result = measure(report)
        Assertions.assertThat(result).containsExactly(INCREASED, INCREASED)
    }

    private fun measure(report: List<Int>): List<DepthChange> {
        val depthMeasurements: List<Pair<Int, Int>> = report.zipWithNext()
        return depthMeasurements.map { (previous, current) -> if (current > previous) INCREASED else DECREASED }
    }
}

enum class DepthChange {
    INCREASED,
    DECREASED
}