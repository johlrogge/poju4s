package poju4s.context

import org.junit._
import org.junit.Assert._
import poju4s.result._

class GlobalSpec {
  @Test
  def thisSpecIsInTheListOfGlobalSpecs {
    val thisSpec = Global.list.filter(_ == (getClass.getName, 'thisSpecIsInTheListOfGlobalSpecs))
    assertFalse(thisSpec.isEmpty)
  }

  @Test
  def allEntriesAreUnique {
    Global.list.groupBy(x => x).map(_._2.size).foreach(assertEquals(1, _))
  }

  @Test
  def canSelectBySpecName {
    val res = Global.select('allEntriesAreUnique).map(_())
    assertEquals(List(Success("poju4s.context.GlobalSpec", 'allEntriesAreUnique)), res)
    
  }
}
