package poju4s

import org.junit._
import org.junit.Assert._
import org.hamcrest.CoreMatchers._

class PendingSpec extends Pending with TestLog {
  @Test(expected=classOf[PendingException])
  def aPendingFailingTestDoesNotReportFailures = pending("supposed to be pending, not failing")({
    assertThat(true, is(false))
  })

  @Test(expected=classOf[PendingException])
  def aPendingTestLogsThatItIsPending {
    try {
      pending("reason") {
        assertThat(true, is(false))
      }
    }
    finally {
       messages.headOption map (assertEquals(LogPending("reason"), _)) orElse (Some(fail("Nothing was logged")))
    }
  }

  @Test(expected = classOf[FixedButPendingException])
  def aFixedPendingTestStartsFailing {
    pending("reason") {
      // succeeds
    }
  }

}
