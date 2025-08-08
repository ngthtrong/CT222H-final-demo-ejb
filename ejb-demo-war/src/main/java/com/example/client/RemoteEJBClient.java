package com.example.client;

import com.example.api.MySessionBeanRemote;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * Standalone client to demonstrate Remote EJB access from outside the application server
 */
public class RemoteEJBClient {
    
    public static void main(String[] args) {
        System.out.println("==== Remote EJB Client Demo ====");
        
        try {
            // Setup JNDI properties for GlassFish
            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY, 
                "com.sun.enterprise.naming.SerialInitContextFactory");
            props.setProperty(Context.URL_PKG_PREFIXES, 
                "com.sun.enterprise.naming");
            props.setProperty(Context.STATE_FACTORIES, 
                "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
            
            // GlassFish default host and port
            props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
            props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
            
            Context ctx = new InitialContext(props);
            
            // Try to lookup the Remote EJB using actual JNDI names from GlassFish
            String[] possibleJndiNames = {
                // Portable Global JNDI name (preferred)
                "java:global/ejb-demo-ear-1.0-SNAPSHOT/com.example-ejb-demo-ejb-1.0-SNAPSHOT/MySessionBean!com.example.api.MySessionBeanRemote",
                // Legacy JNDI names that GlassFish creates
                "com.example.api.MySessionBeanRemote",
                "com.example.api.MySessionBeanRemote#com.example.api.MySessionBeanRemote",
                // Backup attempts (may not exist)
                "java:global/ejb-demo-ear-1.0-SNAPSHOT/ejb-demo-ejb-1.0-SNAPSHOT/MySessionBean!com.example.api.MySessionBeanRemote",
                "java:global/ejb-demo/ejb-demo-ejb/MySessionBean!com.example.api.MySessionBeanRemote",
                "ejb/MySessionBeanRemote",
                "MySessionBean#com.example.api.MySessionBeanRemote"
            };
            
            MySessionBeanRemote remoteBean = null;
            String successfulJndiName = null;
            
            System.out.println("Attempting to lookup Remote EJB...");
            
            for (String jndiName : possibleJndiNames) {
                try {
                    System.out.println("Trying JNDI name: " + jndiName);
                    remoteBean = (MySessionBeanRemote) ctx.lookup(jndiName);
                    successfulJndiName = jndiName;
                    System.out.println("✓ SUCCESS: Found EJB at " + jndiName);
                    break;
                } catch (NamingException e) {
                    System.out.println("✗ Failed: " + e.getMessage());
                }
            }
            
            if (remoteBean != null) {
                System.out.println("\n==== Testing Remote EJB Methods ====");
                
                // Test the remote method
                String[] testNames = {"Alice", "Bob", "Charlie"};
                
                for (String name : testNames) {
                    String greeting = remoteBean.getGreeting(name);
                    System.out.println("Input: " + name + " -> Output: " + greeting);
                }
                
                System.out.println("\n==== Remote EJB Demo Completed Successfully! ====");
                System.out.println("Used JNDI Name: " + successfulJndiName);
                
            } else {
                System.err.println("\n==== ERROR ====");
                System.err.println("Could not locate Remote EJB using any of the attempted JNDI names.");
                System.err.println("Make sure:");
                System.err.println("1. GlassFish server is running");
                System.err.println("2. EJB application is deployed");
                System.err.println("3. Check actual JNDI names in GlassFish admin console");
            }
            
        } catch (Exception e) {
            System.err.println("Error in Remote EJB Client: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n==== Client Demo Finished ====");
    }
}
