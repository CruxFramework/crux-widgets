/*
 * Copyright 2014 cruxframework.org.
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
package org.cruxframework.crux.widgets.client.paging;

import org.cruxframework.crux.widgets.client.WidgetMsgFactory;
import org.cruxframework.crux.widgets.client.event.paging.PageEvent;
import org.cruxframework.crux.widgets.client.event.paging.PageHandler;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

/**
 * Base implementation for a Pager
 * @author Gesse S. F. Dafe
 * @see org.cruxframework.crux.core.client.dataprovider.pager.AbstractPager<T>
 */
@Deprecated
public abstract class AbstractPager extends Composite implements Pager
{
	private Pageable pageable;
	private int currentPage = 0;
	private boolean isLastPage = true;
	private boolean enabled = true;
	
	/**
	 * @see org.cruxframework.crux.widgets.client.event.paging.HasPageHandlers#addPageHandler(org.cruxframework.crux.widgets.client.event.paging.PageHandler)
	 */
	public HandlerRegistration addPageHandler(PageHandler handler)
	{
		return addHandler(handler, PageEvent.getType());
	}
	
	/**
	 * @see org.cruxframework.crux.widgets.client.paging.Pager#update(int, boolean)
	 */
	public void update(int currentPage, boolean isLastPage)
	{
		this.currentPage = currentPage;
		this.isLastPage = isLastPage;
		hideLoading();		
		onUpdate();
	}
	
	/**
	 * Refreshes the pager
	 */
	protected abstract void onUpdate();

	/**
	 * @param pageable the pageable to set
	 */
	public final void setPageable(Pageable pageable)
	{
		this.pageable = pageable;
		pageable.setPager(this);
	}

	/**
	 * Moves the pageable's cursor to the previous page
	 */
	protected void previousPage()
	{
		checkPageable();
		showLoading();
		getPageable().previousPage();
	}
	
	/**
	 * Moves the pageable's cursor to the next page
	 */
	protected void nextPage()
	{
		checkPageable();
		showLoading();
		getPageable().nextPage();
	}
	
	/**
	 * Moves the pageable's cursor to the first page
	 */
	protected void firstPage()
	{
		checkPageable();
		showLoading();
		getPageable().goToPage(1);
	}
	
	/**
	 * Moves the pageable's cursor to the last page
	 */
	protected void lastPage()
	{
		checkPageable();
		showLoading();
		getPageable().goToPage(getPageable().getPageCount());
	}
	
	/**
	 * Moves the pageable's cursor to the an arbitrary page
	 */
	protected void goToPage(int page)
	{
		checkPageable();
		showLoading();
		getPageable().goToPage(page);
	}
	
	/**
	 * Shows some information to tell user that operation is in progress
	 */
	protected abstract void showLoading();
	
	/**
	 * Hides the loading information
	 */
	protected abstract void hideLoading();

	/**
	 * @return the enabled
	 */
	public boolean isEnabled()
	{
		return enabled;
	}
	
	/**
	 * @param enabled
	 */
	public final void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
		update(this.currentPage, this.isLastPage);
	}

	/**
	 * If there is no pageable set, throws {@link IllegalStateException}
	 */
	protected void checkPageable()
	{
		if(this.pageable == null)
		{
			throw new IllegalStateException(WidgetMsgFactory.getMessages().pagerNoPageableSet());
		}		
	}

	/**
	 * @return the pageable
	 */
	private Pageable getPageable()
	{
		return pageable;
	}

	/**
	 * @return the currentPage
	 */
	public int getCurrentPage()
	{
		return currentPage;
	}

	/**
	 * @return the isLastPage
	 */
	public boolean isLastPage()
	{
		return isLastPage;
	}

	/**
	 * Returns -1 if unknown
	 */
	public int getPageCount()
	{
		return this.pageable != null && this.pageable.isDataLoaded() ? pageable.getPageCount() : -1;
	}	
}