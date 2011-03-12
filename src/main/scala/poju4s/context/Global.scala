package poju4s.context

import poju4s.Interaction
import java.net.URLClassLoader
import java.net.URL
import java.io.File
import scala.collection.JavaConversions._

object Global extends Interaction {
  override def list: List[(String, Symbol)] = {
    val cldr = Thread.currentThread.getContextClassLoader
    (for (
      clazz <- discoverClasses(cldr);
      descriptor <- listForClass(clazz)
    ) yield {
      descriptor
    })
  }

  def discoverClasses(cldr: ClassLoader, discovered: List[Class[_]] = Nil): List[Class[_]] = {
    cldr match {
      case urlCldr: URLClassLoader => discoverClasses(cldr.getParent, discovered ++ classesFromUrls(urlCldr.getURLs))
      case null => discovered
      case _ => discoverClasses(cldr.getParent, discovered)
    }
  }

  def classesFromUrls(urls: Array[URL]) = {
    (for (
      dirUrl <- urls if dirUrl.getPath.endsWith("/");
      className <- classesFromFile(new File(dirUrl.toURI))
    ) yield {
      loadClass(className.drop(new File(dirUrl.toURI).getAbsolutePath.length + 1).dropRight(".class".length).replaceAll("/", "."))
    }).toList
  }

  def classesFromFile(file: File): List[String] = {
    if (file.isDirectory()) {
      (for (
        sub <- file.listFiles();
        aClass <- classesFromFile(sub) if aClass.endsWith(".class")
      ) yield {
        aClass
      }).toList
    } else {
      List[String](file.getAbsolutePath())
    }
  }

  def loadClass(name: String) = {
    Thread.currentThread.getContextClassLoader.loadClass(name)
  }
}

