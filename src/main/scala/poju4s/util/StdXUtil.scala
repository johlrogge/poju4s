package poju4s.util

import java.io._


object StdXUtil {
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

}
