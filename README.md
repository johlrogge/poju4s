Poju4s - Plain Old JUnit for scala
==================================
Plain old JUnit for scala is an experiment. The idea is to add scala sugar on top of JUnit rather than rolling a completely new framework. The general idea is that JUnit is well integrated in existing tools, well known and works pretty decently. Scala opens up new options that can make JUnit smoother to work with and sugar the tests a bit.
There are already good testing frameworks for scala. [ScalaTest](http://scalatest.org/) is my personal favorite and I recommend anyone looking for a testframework to try it out. Poju4s will not prioritize backwards compatibility for a while so if you use it don't blame me if I break your code in the future. Feel free to fork :)

Philosophy
----------
While it may seem that the philosophy is to reuse as much as possible of existing infrastructure that is not the main point. It's true that I have been rather happy with JUnit and that it's probably an easier transition from JUnit to poju4s than to start over with a brand new framework.
The philosophy is rather to find patterns for extending JUnit into something that suits you. To find a way to add stuff to JUnit that feels natural. It's also a personal challenge for me to see if I can add something to the JUnit experience tapping from what I have learned from Scala and clojure in recent years.
I am also very interested in unifying the TDD-way of developing code and the more common REPL driven approach that I can only assume is the norm in FP circles.
I'm currently focusing my learning on emacs so expect my efforts (when I find the time) to be on something that works well with emacs and Ensime. Poju4s will probably work just fine with eclipse and intellij but that is not where my initial focus will lie.

Features
----------
As soon as I have something worth showing I will add examples here. Here is a little bit of what I have planned:

* Pending until fixed functionality (ignoring a nonworking test until it works)
* Functional fixtures. (A functional, immutable alternative to setUp tearDown)
* Wrapped runner. (Easy running of JUnit tests from the repl)
* Interactive runner. (Interactive run menus from the REPL)

On the more wild side of things:
* Ivy based runner (Start the interactive runner stand alone with a classpath defined with ivy)
* Dynamic reloading of classes for the Ivy runner. The idea is to run sbt with ~compile and have a separate test-shell pick up the changes dynamically for interactive running and inspection)

All of this may change without notice as I learn more.

Licence
----------
    "THE BEER-WARE LICENSE" (Revision 42):
    Joakim Ohlrogge wrote this code. As long as you retain this notice you
    can do whatever you want with this stuff. If we meet some day, and you think
    this stuff is worth it, you can buy me a beer in return /Joakim Ohlrogge

Contributing
------------
If you feel that you have some contribution that should make it into this project then pleas form my code, make your changes and send me a pull request.

I would of course be thrilled to hear if you use the code and what you think about it. Just remember that I write this stuff on my sparetime and it would look a million times better if I could only find the time to do things properly it would suck less (yeah right :))








