package com.test.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.test.service.TestApplicationService;

@ApplicationPath("/ws")
public class TestApplication extends Application{

	public Set <Class<?>> getClasses(){
		System.out.println("inside getClasses");
		Set<Class<?>> classSet=new HashSet<Class<?>>();
		classSet.add(TestApplicationService.class);
		return classSet;
	}
}
