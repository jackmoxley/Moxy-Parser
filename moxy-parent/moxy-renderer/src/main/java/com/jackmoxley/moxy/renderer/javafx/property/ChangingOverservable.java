package com.jackmoxley.moxy.renderer.javafx.property;

import java.util.HashSet;
import java.util.Set;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class ChangingOverservable<CL> implements Observable {

	public interface ChangeFunction<CL> {

		void apply(CL listener);
	}

	private final Set<InvalidationListener> invalidationListeners = new HashSet<InvalidationListener>();
	private final Set<CL> changeListeners = new HashSet<CL>();
	
	@Override
	public void addListener(InvalidationListener listener) {
		invalidationListeners.add(listener);
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		invalidationListeners.remove(listener);
	}
	
	public void addListener(CL listener) {
		changeListeners.add(listener);
	}

	public void removeListener(CL listener) {
		changeListeners.remove(listener);
	}
	
	protected void invalidate(){
		for(InvalidationListener listener: invalidationListeners){
			listener.invalidated(this);
		}
	}
	
	protected void change(ChangeFunction<CL> function){
		for(CL listener: changeListeners){
			function.apply(listener);
		}
	}
}
