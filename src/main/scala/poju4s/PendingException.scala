package poju4s

import org.junit.internal.AssumptionViolatedException
class PendingException(reason:String) extends AssumptionViolatedException(reason)
