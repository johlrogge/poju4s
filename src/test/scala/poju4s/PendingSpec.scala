package poju4s

import org.junit._
import org.junit.Assert._
import org.hamcrest.CoreMatchers._

class PendingSpec extends Pending with TestLog {
  @Test
  def aPendingFailingTestDoesNotReportFailures = pending("supposed to be pending, not failing")({
    assertThat(true, is(false))
  })

  @Test
  def aPendingTestLogsThatItIsPending {
    pending("reason") {
      assertThat(true, is(false))
    }
    messages.headOption map (assertEquals(Warn("pending: reason"), _)) orElse (Some(fail("Nothing was logged")))
  }

  @Test(expected = classOf[FixedButPendingException])
  def aFixedPendingTestStartsFailing {
    pending("reason") {
      // succeeds
    }
  }

}
