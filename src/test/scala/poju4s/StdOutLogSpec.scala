package poju4s

import java.io.{PrintStream, ByteArrayOutputStream}
import org.junit.Test
import org.junit.Assert._


class StdOutLogSpec extends StdOutLog {
  import poju4s.util.StdXUtil._

  @Test
  def logsPedningToStdOut = overrideStdOut { written =>
    log.pending("reason")
    assertEquals("PENDING: reason", written.trim)            
  }
}
