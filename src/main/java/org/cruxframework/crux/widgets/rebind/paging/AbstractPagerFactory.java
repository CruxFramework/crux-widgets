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
package org.cruxframework.crux.widgets.rebind.paging;

import org.cruxframework.crux.core.client.utils.EscapeUtils;
import org.cruxframework.crux.core.rebind.AbstractProxyCreator.SourcePrinter;
import org.cruxframework.crux.core.rebind.CruxGeneratorException;
import org.cruxframework.crux.core.rebind.screen.widget.WidgetCreator;
import org.cruxframework.crux.core.rebind.screen.widget.WidgetCreatorContext;
import org.cruxframework.crux.core.rebind.screen.widget.declarative.ProcessingTime;
import org.cruxframework.crux.core.rebind.screen.widget.declarative.TagAttribute;
import org.cruxframework.crux.core.rebind.screen.widget.declarative.TagAttributeDeclaration;
import org.cruxframework.crux.core.rebind.screen.widget.declarative.TagAttributes;
import org.cruxframework.crux.core.rebind.screen.widget.declarative.TagAttributesDeclaration;
import org.cruxframework.crux.core.rebind.screen.widget.declarative.TagEvent;
import org.cruxframework.crux.core.rebind.screen.widget.declarative.TagEvents;
import org.cruxframework.crux.widgets.client.paging.Pageable;
import org.cruxframework.crux.widgets.rebind.event.PageEvtBind;


/**
 * @author Gesse S. F. Dafe
 */
@TagAttributesDeclaration({
	@TagAttributeDeclaration(value="pageable", required=true, supportsDataBinding=false)
})
@TagAttributes({
	@TagAttribute(value="enabled", type=Boolean.class, processingTime=ProcessingTime.afterAllWidgetsOnView)
})
@TagEvents({
	@TagEvent(PageEvtBind.class)
})
public abstract class AbstractPagerFactory extends WidgetCreator<WidgetCreatorContext>
{
	@Override
	public void processAttributes(SourcePrinter out, WidgetCreatorContext context) throws CruxGeneratorException
	{
		super.processAttributes(out, context);
	
		String widget = context.getWidget();
		String pageableId = context.readWidgetProperty("pageable");

		if(pageableId != null)
		{
			String widgetClassName = getWidgetClassName();
			printlnPostProcessing("final "+widgetClassName+" "+widget+" = ("+widgetClassName+")"+ getViewVariable()+".getWidget("+EscapeUtils.quote(context.getWidgetId())+");");
			printlnPostProcessing("assert("+getViewVariable()+".getWidget("+EscapeUtils.quote(pageableId)+") != null):"+EscapeUtils.quote("No pageable widget set for the pager ["+context.getWidgetId()+"], on view ["+getView().getId()+"].")+";");
			printlnPostProcessing(widget+".setPageable(("+Pageable.class.getCanonicalName()+") "+getViewVariable()+".getWidget("+EscapeUtils.quote(pageableId)+"));");
		}
		else
		{
			throw new CruxGeneratorException("No pageable widget set for the pager ["+context.getWidgetId()+"], on view ["+getView().getId()+"]."); 
		}							
	}
	
	@Override
    public WidgetCreatorContext instantiateContext()
    {
	    return new WidgetCreatorContext();
    }
}