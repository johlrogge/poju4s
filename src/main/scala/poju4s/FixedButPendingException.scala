package poju4s

import junit.framework.AssertionFailedError

class FixedButPendingException(pendingReason:String) extends AssertionFailedError("marked as pending because "+ pendingReason + "; but was fixed") {

}
