# Principles and Practice in Programming Languages
# Lab 1

This repository contains the student project files. If you are an instructor looking to re-use these materials, please contact me ([Bor-Yuh Evan Chang](https://www.cs.colorado.edu/~bec)).

Refer to the lab handouts for details about each assignment.  This file provides some information to help you get started with setting up your development environment.

## Integrity of the Course Materials

The development effort in the course materials, including these lab assignments, the exercises, and the exams, is significant. You agree that you will not share any course materials publicly. The course materials, include your or anyone else's solutions to the lab assignments, exercises, and exams. In particular, you agree not to post your solutions to the lab assignments in a public source code repository, such as public Github repositories. Please use private source code repositories for your work.

## Repository Organization

In the directory that you want your project files, clone this repository to your local machine. You can determine the clone URL by clicking `Clone or download` in the Github
interface.

    $ git clone <your .git URL>

In this document, the

    $

simply stands for the shell prompt.

The above command will create the directory `pppl-lab1`.

    $ cd pppl-lab1

All other commands in this document will assume that your are in this directory.

Note that the files for a single lab are committed on different branches in the repository.

## Project Files Organization

For Lab 1, the most important project files are shown below.

```
.
├── README.md  (this file)
├── jsy.sh     (run through a .jsy through Node.js)
├── lab1.pdf   (the lab handout)
├── lab1.sh    (run your Javascripty intepreter)
├── src
│   ├── main
│   │   └── scala
│   │       └── jsy
│   │           ├── lab1                 (lab-specific support files will here)
│   │           │   ├── Lab1Like.scala      (the Lab1 interface)
│   │           │   ├── Parser.scala        (the Javascripty parser)
│   │           │   └── ast.scala           (the Javascripty AST classes)
│   │           ├── student              (files for you to edit will be here)
│   │           │   ├── Lab1.scala          (implementation template to **submit**)
│   │           │   └── Lab1Worksheet.sc    (a scratch worksheet)
│   │           └── util
│   └── test
│       ├── resources
│       │   └── lab1    (test .jsy files with expect answers in .ans)
│       │       ├── test101_arith.ans
│       │       ├── test101_arith.jsy
│       │       ├── test102_divbyzero.ans
│       │       └── test102_divbyzero.jsy
│       └── scala
│           └── jsy
│               ├── student
│               │   └── Lab1Spec.scala     (your ScalaTest unit tests)
│               └── tester
│                   └── JavascriptyTester.scala
└── testlab1.sh  (run your Lab1Spec)
```

The files for you to edit and submit will be in `src/main/jsy/student` or `src/test/scala/jsy/student`.

## Scala Development Tools

We support [IntelliJ IDEA](https://www.jetbrains.com/idea/) for development in this course. You are welcome to use any development environment, but we may not be able answer questions in your particular environment.

You will need to [download](https://www.jetbrains.com/idea/download/) and install IntelliJ IDEA. The Community Edition will be fine.

The project is designed to work with [Scala](http://www.scala-lang.org/) is 2.12. We will standardize on 2.12.8. For the most part, you do not need to worry about the Scala version because we are using sbt for building.

### IntelliJ Import

From the IntelliJ splash screen on start up, first make sure that the Scala plugin is installed. Go to

    Configure > Settings or Preferences (depending on your platform) > Plugins

In the plugins list, make sure `Scala` is installed.

Then back at the splash screen, configure your Java software development kit (Java SDK) in IntelliJ

    Configure > Project Defaults > Project Structure

Under Project SDK, select an SDK from the list (Java 1.8 is recommended). If there are no listed, you will have to select the directory with your SDK from

    New ...

Then, again from the splash

    Import Project

and then select the directory with the project files (i.e., `pppl-lab1`) and hit Ok. On the next dialog, select

    Import project from external model > SBT > Next

If you do not see SBT, then you did not select the project files.

On the next dialog, select

    Use auto-import

If you want to be able to navigate to definitions in external sources, you can select to download sources and docs before hitting Finish.

Here is the IntelliJ documentation on [import](https://www.jetbrains.com/idea/help/getting-started-with-sbt.html#import_project).

### Command-Line Tools

While strictly required, you will also want to be use the command-line tools.

You can issue the following command to compile your code:

    $ sbt compile

This command

    $ sbt clean

deletes the previous compilation.

It is most convenient to run sbt interactively

    $ sbt

and then run via

    > compile

at the sbt prompt. The slow load time of sbt is due to starting a JVM instance, which is saved by starting it once and re-using the instance for several operations. In the following, we will show sbt commands from the sbt prompt as above.

In sbt, you can also prefix any command with `~` to re-execute the command when any file in the project is updated.

    > ~compile

### Scala Interactive Console

From sbt, you can start the Scala console using the command

    > console

and can import the functions in your lab in the following way

    scala> import jsy.student.Lab1._

In IntelliJ, you can start a Scala console with the project files available by selecting

    Tools > Run Scala Console

However, for quick experimentation in IntelliJ, it is more convenient to use a Scala Worksheet (e.g., `src/scala/jsy/student/Lab1Worksheet.sc`).

## ScalaTest

We will be using the [ScalaTest](http://www.scalatest.org/) framework for unit testing.  Using this framework, we practice test-driven development (TDD), a standard practice in industry. You do not need to explicitly download ScalaTest.

We provide some unit tests in `src/test/scala/Lab1Spec.scala` to drive your implementation.  To run tests, right-click on the Lab1Suite object in the Project view and select

    Run 'Lab1Suite'

You can also run all test objects under the `src/test` directory via

    > test

Or you can specify, specifically

    > test-only Lab1Suite

## Your Javascripty Interpreter

You can run your Javascripty interpreter with a file (e.g., tests in `src/test/resources`) from sbt or the command-line:

    > runMain jsy.student.Lab1 <arguments>

Or for your convenience,

    $ ./lab1.sh <arguments>

However, it is rare that you will want to run your Javascripty interpreter directly, as you will driving your implementation via tests in `Lab1Spec`.

## Node.js

We have a script to run Javascripty files through Node.js (as JavaScript):

    $ ./jsy.sh test.jsy


## Tool Installation Summaries

### IntelliJ

1. Install [IntelliJ IDEA](https://www.jetbrains.com/idea/download/).

During install, choose the options to install Scala and sbt.

### Scala

Follow this tutorial:

[Getting Started with Scala in IntelliJ](https://docs.scala-lang.org/getting-started/intellij-track/getting-started-with-scala-in-intellij.html)

## Troubleshooting

### Why does SBT does not show up when I try to import into IntelliJ?

Here are some reasons that we have observed.

* In your `pppl-lab1` folder, if you do not see `build.sbt`, then it is likely that you have the project files.

* You need to have sbt installed. If you're using the CU CS VM, you

        $ sudo apt-get update
        $ sudo apt-get install cu-cs-csci-3155

* In your IntelliJ installation, you need to have the Scala plugin installed. From the splash screen,

        Configure > Plugins

### What if I can't run Lab1 or Lab1Spec from IntelliJ?

It could be that you did not import your project as an SBT project. Try to import the project again. You can remove any of IntelliJ's meta-data by deleting the `.idea/` directory in `pppl-lab1/`.


### Why is the editor in IntelliJ is not allowing me to write anything.

Do you have the Vim Emulator mode turned on? Look under `Tools > Vim Emulator`. If you don't know what Vim is, then you should turn this option off.
