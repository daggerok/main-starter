package com.github.daggerok;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationScoped
@ApplicationPath("api") // only for path
public class ApplicationConfig extends Application {}
