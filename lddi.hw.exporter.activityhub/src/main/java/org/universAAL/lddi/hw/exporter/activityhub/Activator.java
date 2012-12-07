package org.universAAL.lddi.hw.exporter.activityhub;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.lddi.hw.exporter.activityhub.util.LogTracker;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;


/**
 * This bundle provides drivers for ActivityHub device services in OSGi registry.
 * It provides access to ActivityHub devices by offering/registering services on the uAAL service bus.
 * It also sends context events to the uAAL context bus for arising ActivityHub sensor messages.
 * 
 * @author Thomas Fuxreiter (foex@gmx.at)
 *
 */

public class Activator implements BundleActivator {
	
    public static BundleContext context = null;
    public static ModuleContext mc = null;
    private AHManager ahManager;
//    private AHServiceProvider serviceProvider;
//    private AHContextPublisher contextProvider;
    private LogTracker logTracker;
	private Thread thread;

	public void start(BundleContext context) throws Exception {
		Activator.context = context;
		Activator.mc = uAALBundleContainer.THE_CONTAINER
			.registerModule(new Object[] { context });
		
		//use a service Tracker for LogService
		logTracker = new LogTracker(context);
		logTracker.open();
		
		// init server
		ahManager = new AHManager(context, logTracker);
		
		// start uAAL service provider
		MyThread runnable = new MyThread(); 
		thread=new Thread(runnable);
		thread.start();
	}

	public void stop(BundleContext arg0) throws Exception {
		thread.interrupt();
	}

	/**
	 * Runnable helper class for access to running servers/threads.
	 * 
	 * @author Thomas Fuxreiter (foex@gmx.at)
	 */
	class MyThread implements Runnable{
		public MyThread() {
		}
		public void run() {
//			serviceProvider = 
				new AHServiceProvider(mc, ahManager);
//			contextProvider = 
				new AHContextPublisher(mc, ahManager);
		}
	}
}
