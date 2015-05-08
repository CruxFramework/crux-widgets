/*
 * Copyright 2011 cruxframework.org.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.cruxframework.crux.widgets.rebind.slideshow;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.cruxframework.crux.core.rebind.CruxGeneratorException;
import org.cruxframework.crux.core.rebind.context.JClassScanner;
import org.cruxframework.crux.widgets.client.slideshow.Slideshow.Layout;
import org.cruxframework.crux.widgets.client.slideshow.Slideshow.Name;
import org.cruxframework.crux.widgets.client.slideshow.data.AlbumService;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.typeinfo.JClassType;


/**
 * Maps all layoutNames in a module.
 * @author Thiago Bustamante
 *
 */
public class SlideshowScanner 
{
	private boolean initialized = false;
	private JClassScanner jClassScanner;
	private Map<String, String> layoutNames;
	private Map<String, String> serviceNames;

	public SlideshowScanner(GeneratorContext context)
	{
		jClassScanner = new JClassScanner(context);
	}

	/**
	 * @param name
	 * @return
	 */
	public String getLayout(String name)
	{
		initialize();
		return layoutNames.get(name);
	}
	
	/**
	 * @param name
	 * @return
	 */
	public String getService(String name)
	{
		initialize();
		return serviceNames.get(name);
	}

	/**
	 * @return
	 */
	public Iterator<String> iterateLayouts()
	{
		initialize();
		return layoutNames.keySet().iterator();
	}

	/**
	 * @return
	 */
	public Iterator<String> iterateServices()
	{
		initialize();
		return serviceNames.keySet().iterator();
	}


	protected void initialize()
	{
		if (!initialized)
		{
			initializeLayouts();
			initializeServices();
			initialized = true;
		}
	}

	/**
	 * 
	 */
	protected void initializeLayouts()
	{

		layoutNames = new HashMap<String, String>();
		JClassType[] layoutClassTypes;

		try 
		{
			layoutClassTypes =  jClassScanner.searchClassesByInterface(Layout.class.getCanonicalName());
		} 
		catch (Exception e) 
		{
			throw new CruxGeneratorException("Error initializing slideshow layout.",e);
		}
		if (layoutClassTypes != null)
		{
			for (JClassType layoutClass : layoutClassTypes) 
			{
				Name annot = layoutClass.getAnnotation(Name.class);
				if (annot != null)
				{
					if (layoutNames.containsKey(annot.value()))
					{
						throw new CruxGeneratorException("Duplicated alias for Slideshow Layout: ["+annot.value()+"].");
					}

					layoutNames.put(annot.value(), layoutClass.getQualifiedSourceName());
				}
			}
		}
	}


	protected void initializeServices()
	{
		serviceNames = new HashMap<String, String>();
		JClassType[] serviceClassTypes;
		try 
		{
			serviceClassTypes =  jClassScanner.searchClassesByInterface(AlbumService.class.getCanonicalName());
		} 
		catch (Exception e) 
		{
			throw new CruxGeneratorException("Error initializing slideshow Service.",e);
		}
		if (serviceClassTypes != null)
		{
			for (JClassType serviceClass : serviceClassTypes) 
			{
				Name annot = serviceClass.getAnnotation(Name.class);
				if (annot != null)
				{
					if (serviceNames.containsKey(annot.value()))
					{
						throw new CruxGeneratorException("Duplicated alias Slideshow Service: ["+annot.value()+"].");
					}

					serviceNames.put(annot.value(), serviceClass.getQualifiedSourceName());
				}
			}
		}
	}
}
