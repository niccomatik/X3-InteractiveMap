/*
 * Copyright (c) 2002-2012 JGoodies Karsten Lentzsch. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of JGoodies Karsten Lentzsch nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.jgoodies.binding.adapter;

import static com.jgoodies.common.base.Preconditions.checkNotNull;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;

/**
 * A {@link ComboBoxModel} implementation that holds the choice list and a
 * selection. This adapter has two modes that differ primarily in how
 * the selection is kept synchronized with the combo's list.
 * 1) If you construct a ComboBoxAdapter with a {@link SelectionInList},
 * the selection will be guaranteed to be in the list, and the selection
 * will reflect changes in the list.
 * 2) If you construct this adapter with a separate selection holder,
 * the selection won't be affected by any change in the combo's list.<p>
 *
 * In both cases, the combo's list of element will reflect changes in the list,
 * if it's a ListModel and will ignore content changes, if it's a List.<p>
 *
 * <strong>Example:</strong><pre>
 * String[] countries = new String[] { &quot;USA&quot;, &quot;Germany&quot;, &quot;France&quot;, ... };
 *
 * // Using an array and ValueModel
 * ValueModel countryModel = new PropertyAdapter(customer, &quot;country&quot;, true);
 * ComboBoxAdapter adapter = new ComboBoxAdapter(countries, contryModel);
 * JComboBox countryBox    = new JComboBox(adapter);
 *
 * // Using a List and ValueModel
 * List countryList = Arrays.asList(countries);
 * ValueModel countryModel = new PropertyAdapter(customer, &quot;country&quot;, true);
 * ComboBoxAdapter adapter = new ComboBoxAdapter(countryList, contryModel);
 * JComboBox countryBox    = new JComboBox(adapter);
 *
 * // Using a ListModel and ValueModel
 * ListModel countryListModel = new ArrayListModel(Arrays.asList(countries));
 * ValueModel countryModel = new PropertyAdapter(customer, &quot;country&quot;, true);
 * ComboBoxAdapter adapter = new ComboBoxAdapter(countryListModel, contryModel);
 * JComboBox countryBox    = new JComboBox(adapter);
 *
 * // Using a SelectionInList - allows only selection of contained elements
 * ListModel countryListModel = new ArrayListModel(Arrays.asList(countries));
 * ValueModel countryModel = new PropertyAdapter(customer, &quot;country&quot;, true);
 * SelectionInList sil     = new SelectionInList(countryListModel, countryModel);
 * ComboBoxAdapter adapter = new ComboBoxAdapter(sil);
 * JComboBox countryBox    = new JComboBox(adapter);
 *
 * // Using ValueModels for the list holder and the selection holder
 * class Country extends Model {
 *     ListModel getLocales();
 *     Locale getDefaultLocale();
 *     void setDefaultLocale(Locale locale);
 * }
 *
 * BeanAdapter beanAdapter = new BeanAdapter(null, true);
 * ValueModel localesHolder      = beanAdapter.getValueModel(&quot;locales&quot;);
 * ValueModel defaultLocaleModel = beanAdapter.getValueModel(&quot;defaultLocale&quot;);
 * ComboBoxAdapter adapter = new ComboBoxAdapter(
 *         localesHolder, defaultLocaleModel);
 * JComboBox localeBox = new JComboBox(adapter);
 *
 * beanAdapter.setBean(myCountry);
 * </pre>
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.16 $
 *
 * @see javax.swing.JComboBox
 *
 * @param <E> the type of the combo box items
 */
public final class ComboBoxAdapter<E> extends AbstractListModel implements ComboBoxModel {

    /**
     * Holds the list of choices.
     */
    private final ListModel listModel;

    /**
     * Refers to the ValueModel that holds the current selection.
     * In case this adapter is constructed for a SelectionInList
     * or SelectionInListModel, the selection holder will be updated
     * if the SIL or SILModel changes its selection holder.
     */
    private ValueModel selectionHolder;

    /**
     * Holds the listener that handles selection changes.
     */
    private final PropertyChangeListener selectionChangeHandler;


    // Instance creation ******************************************************

    /**
     * Constructs a ComboBoxAdapter for the specified List of items
     * and the given selection holder. Structural changes in the list
     * will be ignored.<p>
     *
     * <strong>Example:</strong><pre>
     * String[] countries = new String[] { &quot;USA&quot;, &quot;Germany&quot;, &quot;France&quot;, ... };
     * List countryList = Arrays.asList(countries);
     * ValueModel countryModel = new PropertyAdapter(customer, &quot;country&quot;, true);
     * ComboBoxAdapter adapter = new ComboBoxAdapter(countryList, contryModel);
     * JComboBox countryBox    = new JComboBox(adapter);
     * </pre>
     *
     * @param items            the list of items
     * @param selectionHolder  holds the selection of the combo
     * @throws NullPointerException if the list of items or the selection holder
     *         is {@code null}
     */
    public ComboBoxAdapter(List<E> items, ValueModel selectionHolder) {
        this(new ListModelAdapter<E>(items), selectionHolder);
        checkNotNull(items, "The list must not be null.");
    }


    /**
     * Constructs a ComboBoxAdapter for the given ListModel and selection
     * holder. Structural changes in the ListModel will be reflected by
     * this adapter, but won't affect the selection.<p>
     *
     * <strong>Example:</strong><pre>
     * String[] countries = new String[] { &quot;USA&quot;, &quot;Germany&quot;, &quot;France&quot;, ... };
     * ListModel countryList = new ArrayListModel(Arrays.asList(countries));
     * ValueModel countryModel = new PropertyAdapter(customer, &quot;country&quot;, true);
     * ComboBoxAdapter adapter = new ComboBoxAdapter(countryList, contryModel);
     * JComboBox countryBox    = new JComboBox(adapter);
     * </pre>
     *
     * @param listModel         the initial list model
     * @param selectionHolder   holds the selection of the combo
     * @throws NullPointerException if the list of items or the selection holder
     *         is {@code null}
     */
    public ComboBoxAdapter(ListModel listModel, ValueModel selectionHolder) {
        this.listModel = checkNotNull(listModel, "The ListModel must not be null.");
        this.selectionHolder = checkNotNull(selectionHolder, "The selection holder must not be null.");
        listModel.addListDataListener(new ListDataChangeHandler());
        selectionChangeHandler = new SelectionChangeHandler();
        setSelectionHolder(selectionHolder);
    }


    /**
     * Constructs a ComboBoxAdapter for the specified List of items and the
     * given selection holder. Structural changes in the list will be ignored.
     * <p>
     *
     * <strong>Example:</strong><pre>
     * String[] countries = new String[] { &quot;USA&quot;, &quot;Germany&quot;, &quot;France&quot;, ... };
     * ValueModel countryModel = new PropertyAdapter(customer, &quot;country&quot;, true);
     * ComboBoxAdapter adapter = new ComboBoxAdapter(countries, contryModel);
     * JComboBox countryBox    = new JComboBox(adapter);
     * </pre>
     *
     * @param items             the list of items
     * @param selectionHolder   holds the selection of the combo
     * @throws NullPointerException if the list of items or the selection holder
     *         is {@code null}
     */
    public ComboBoxAdapter(E[] items, ValueModel selectionHolder) {
        this(new ListModelAdapter<E>(items), selectionHolder);
    }


    /**
     * Constructs a ComboBoxAdapter for the given SelectionInList. Note that
     * selections which are not elements of the list will be rejected.<p>
     *
     * <strong>Example:</strong><pre>
     * String[] countries = new String[] { &quot;USA&quot;, &quot;Germany&quot;, &quot;France&quot;, ... };
     * List countryList = Arrays.asList(countries);
     * ValueModel countryModel = new PropertyAdapter(customer, &quot;country&quot;, true);
     * SelectionInList sil     = new SelectionInList(countryList, countryModel);
     * ComboBoxAdapter adapter = new ComboBoxAdapter(sil);
     * JComboBox countryBox    = new JComboBox(adapter);
     * </pre>
     *
     * @param selectionInList        provides the list and selection
     * @throws NullPointerException if the {@code selectionInList} is
     *         {@code null}
     */
    public ComboBoxAdapter(SelectionInList<E> selectionInList) {
        this(selectionInList, selectionInList);
        selectionInList.addPropertyChangeListener(
                SelectionInList.PROPERTY_SELECTION_HOLDER,
                new SelectionHolderChangeHandler());
    }


    // ComboBoxModel API ****************************************************

    /**
     * Returns the selected item by requesting the current value from the
     * either the selection holder or the SelectionInList's selection.
     *
     * @return The selected item or {@code null} if there is no selection
     */
    @Override
	public E getSelectedItem() {
        return (E) selectionHolder.getValue();
    }


    /**
     * Sets the selected item. The implementation of this method should notify
     * all registered {@code ListDataListener}s that the contents has
     * changed.
     *
     * @param object the list object to select or {@code null} to clear
     *        the selection
     */
    @Override
	public void setSelectedItem(Object object) {
        selectionHolder.setValue(object);
    }


    /**
     * Returns the length of the item list.
     *
     * @return the length of the list
     */
    @Override
	public int getSize() {
        return listModel.getSize();
    }


    /**
     * Returns the value at the specified index.
     *
     * @param index the requested index
     * @return the value at {@code index}
     */
    @Override
	public E getElementAt(int index) {
        return (E) listModel.getElementAt(index);
    }


    // Helper Code ************************************************************

    private void setSelectionHolder(ValueModel newSelectionHolder) {
        ValueModel oldSelectionHolder = selectionHolder;
        if (oldSelectionHolder != null) {
            oldSelectionHolder.removeValueChangeListener(selectionChangeHandler);
        }
        selectionHolder = checkNotNull(newSelectionHolder,
                "The selection holder must not be null.");
        newSelectionHolder.addValueChangeListener(selectionChangeHandler);
    }


    // Event Handling *********************************************************

    private void fireContentsChanged() {
        fireContentsChanged(this, -1, -1);
    }


    /**
     * Listens to selection changes and fires a contents change event.
     */
    private final class SelectionChangeHandler implements PropertyChangeListener {

        /**
         * The selection has changed. Notifies all
         * registered listeners about the change.
         *
         * @param evt the property change event to be handled
         */
        @Override
		public void propertyChange(PropertyChangeEvent evt) {
            fireContentsChanged();
        }

    }


    /**
     * Handles ListDataEvents in the list model.
     */
    private final class ListDataChangeHandler implements ListDataListener {

        /**
         * Sent after the indices in the index0, index1 interval have been
         * inserted in the data model. The new interval includes both index0 and
         * index1.
         *
         * @param evt a {@code ListDataEvent} encapsulating the event
         *        information
         */
        @Override
		public void intervalAdded(ListDataEvent evt) {
            fireIntervalAdded(ComboBoxAdapter.this,
                              evt.getIndex0(),
                              evt.getIndex1());
        }


        /**
         * Sent after the indices in the index0, index1 interval have been
         * removed from the data model. The interval includes both index0 and
         * index1.
         *
         * @param evt a {@code ListDataEvent} encapsulating the event
         *        information
         */
        @Override
		public void intervalRemoved(ListDataEvent evt) {
            fireIntervalRemoved(ComboBoxAdapter.this,
                                evt.getIndex0(),
                                evt.getIndex1());
        }


        /**
         * Sent when the contents of the list has changed in a way that's too
         * complex to characterize with the previous methods. For example, this
         * is sent when an item has been replaced. Index0 and index1 bracket the
         * change.
         *
         * @param evt a {@code ListDataEvent} encapsulating the event
         *        information
         */
        @Override
		public void contentsChanged(ListDataEvent evt) {
            fireContentsChanged(ComboBoxAdapter.this,
                                evt.getIndex0(),
                                evt.getIndex1());
        }
    }


    /**
     * Listens to changes of the selection holder and updates our internal
     * reference to it.
     */
    private final class SelectionHolderChangeHandler implements PropertyChangeListener {

        /**
         * The SelectionInList has changed the selection holder.
         * Update our internal reference to the new holder and
         * notify registered listeners about a selection change.
         *
         * @param evt the property change event to be handled
         */
        @Override
		public void propertyChange(PropertyChangeEvent evt) {
            setSelectionHolder((ValueModel) evt.getNewValue());
            fireContentsChanged();
        }
    }


    // Helper Classes *********************************************************

    /**
     * Converts a List to ListModel by wrapping the underlying list.
     */
    private static final class ListModelAdapter<E> extends AbstractListModel {

        private final List<E> aList;

        ListModelAdapter(List<E> list) {
            this.aList = list;
        }

        ListModelAdapter(E[] elements) {
            this(Arrays.asList(elements));
        }

       /**
        * Returns the length of the list.
        * @return the length of the list
        */
        @Override
		public int getSize() { return aList.size(); }

        /**
         * Returns the value at the specified index.
         * @param index the requested index
         * @return the value at {@code index}
         */
        @Override
		public E getElementAt(int index) { return aList.get(index); }

    }


}
