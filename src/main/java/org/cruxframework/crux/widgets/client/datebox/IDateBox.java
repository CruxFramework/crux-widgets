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
package org.cruxframework.crux.widgets.client.datebox;

import java.util.Date;

import org.cruxframework.crux.widgets.client.datebox.DateBox.CruxFormat;
import org.cruxframework.crux.widgets.client.datepicker.DatePicker;
import org.cruxframework.crux.widgets.client.maskedtextbox.MaskedTextBox;
import org.cruxframework.crux.widgets.client.util.type.CruxWidget;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.UIObject;

/**
 * @author Samuel Almeida Cardoso (samuel@cruxframework.org)
 *
 */
interface IDateBox extends CruxWidget, HasValue<Date>, Focusable, HasValueChangeHandlers<Date>, HasEnabled
{
	public CruxFormat getFormat();
	public UIObject getPopup();
	public int getTabIndex();
	public MaskedTextBox getTextBox();
	public DatePicker getDatePicker();
	public Date getValue();
	public void hideDatePicker();
	public boolean isDatePickerShowing();
	public boolean isEnabled();
	public void setAccessKey(char key);
	public void setEnabled(boolean enabled);
	public void setReadOnly(boolean readOnly);
	public void setFocus(boolean focused);
	public void setFormat(CruxFormat format);
	public void setValue(Date date);
	public void showDatePicker();
	public void setFireNullValues(boolean fires);
}
