package com.note.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


public class Listener implements ServletContextListener,HttpSessionListener,HttpSessionAttributeListener{

	public void contextDestroyed(ServletContextEvent arg0) {}

	public void contextInitialized(ServletContextEvent arg0) {}

	public void sessionCreated(HttpSessionEvent arg0) {}

	public void sessionDestroyed(HttpSessionEvent arg0) {}

	public void attributeAdded(HttpSessionBindingEvent arg0) {}

	public void attributeRemoved(HttpSessionBindingEvent arg0) {}

	public void attributeReplaced(HttpSessionBindingEvent arg0) {}

}

