package poju4s.exception

import org.junit.internal.AssumptionViolatedException
class PendingException(reason:String) extends AssumptionViolatedException(reason)
