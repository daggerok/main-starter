package com.github.daggerok.infrastructure;

import javax.inject.Qualifier;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * from scaladoc (deprecated in 2.13.0):
 *
 * Annotation classes need to be written in Java in order to be stored in classfiles in a Java-compatible manner
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface LogMe { }
