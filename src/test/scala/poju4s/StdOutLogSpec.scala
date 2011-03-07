package poju4s

import java.io.{PrintStream, ByteArrayOutputStream}
import org.junit.Test
import org.junit.Assert._

class StdOutLogSpec extends StdOutLog {
  def overrideStdOut(body: ( => String) => Unit) = {
    val oldOut = System.out
    val newOut = new ByteArrayOutputStream
    def resultAsString =  {
      System.out.flush
      new String(newOut.toByteArray(), "UTF-8")
    }
    System.setOut(new PrintStream(newOut))
    try {
      body(resultAsString)
    }
    finally {
      System.setOut(oldOut)
    }
  }

  @Test
  def logsPedningToStdOut = overrideStdOut { written =>
    log.pending("reason")
    assertEquals("PENDING: reason", written.trim)            
  }
}
