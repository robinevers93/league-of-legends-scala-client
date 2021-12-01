object Settings {

  val compilerOptions: Seq[String] = Seq(

    "-deprecation", // Warning and location for usages of deprecated APIs.
    "-encoding", "utf-8", // Specify character encoding used by source files.
    "-explaintypes", // Explain type errors in more detail.
    "-feature", // Emit warning and location for usages of features that should be imported explicitly.
    "-unchecked", // Enable additional warnings where generated code depends on assumptions.
    "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.

    // ********** Warning Settings ***********************************************
    "-Werror", // Fail the compilation if there are any warnings.
    "-Wdead-code", //  Warn when dead code is identified.
    "-Wextra-implicit", // Warn when more than one implicit parameter section is defined.
    "-Wmacros:after", // Only inspect expanded trees when generating unused symbol warnings.
    "-Wnumeric-widen", // Warn when numerics are widened.
    "-Woctal-literal", // Warn on obsolete octal syntax.
//    "-Wunused:imports", //Warn if an import selector is not referenced.
    "-Wunused:patvars", // Warn if a variable bound in a pattern is unused.
    "-Wunused:privates", // Warn if a private member is unused.
    "-Wunused:locals", // Warn if a local definition is unused.
    "-Wunused:explicits", // Warn if an explicit parameter is unused.
    "-Wunused:implicits", // Warn if an implicit parameter is unused.
    "-Wunused:params", // Enable -Wunused:explicits,implicits.
    //"-Wvalue-discard", // Warn when non-Unit expression results are unused. <-- be good to fix these errors

    // ********** Warnings to ignore ***********************************************
    "-Wconf:src=target/.*:s,cat=deprecation:s",

    // ********** -Xlint: Enable recommended warnings ****************************
    "-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
    //"-Xlint:by-name-right-associative",  // By-name parameter of right associative operator.
    "-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
    "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
    //"-Xlint:deprecation", // Enable linted deprecations.
    "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
    "-Xlint:eta-sam", // Warn on eta-expansion to meet a Java-defined functional interface that is not explicitly annotated with @FunctionalInterface.
    "-Xlint:eta-zero", // Warn on eta-expansion (rather than auto-application) of zero-ary method.
    "-Xlint:implicit-not-found", // Check @implicitNotFound and @implicitAmbiguous messages.
    "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
    "-Xlint:infer-any", // Warn when a type argument is inferred to be Any.
    "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
    "-Xlint:nonlocal-return", // A return statement used an exception for flow control.
    //"-Xlint:nullary-override",  // Warn when non-nullary `def f()' overrides nullary `def f'.
    //"-Xlint:nullary-unit", // Warn when nullary methods return Unit.
    "-Xlint:option-implicit", // Option.apply used implicit view.
    "-Xlint:package-object-classes", // Class or object defined in package object.
    "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
    "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
    "-Xlint:serial", // @SerialVersionUID on traits and non-serializable classes.
    "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
    "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
    //"-Xlint:unsound-match",  // Pattern match may not be typesafe.
    "-Xlint:valpattern" // Enable pattern checks in val definitions.
  )
}
