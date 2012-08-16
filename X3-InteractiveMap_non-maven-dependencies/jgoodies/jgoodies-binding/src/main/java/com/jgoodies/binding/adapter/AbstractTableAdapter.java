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

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;

/**
 * An abstract implementation of the {@link javax.swing.table.TableModel}
 * interface that converts a {@link javax.swing.ListModel} of row elements.<p>
 *
 * This class provides default implementations for the {@code TableModel}
 * methods {@code #getColumnCount()} and {@code #getColumnName(int)}.
 * To use these methods you must use the constructor that accepts an
 * array of column names and this array must not be {@code null}.
 * If a subclass constructs itself with the column names set to {@code null}
 * it must override the methods {@code #getColumnCount()} and
 * {@code #getColumnName(int)}.<p>
 *
 * <strong>Example:</strong> API users subclass {@code AbstractTableAdapter}
 * and just implement the method {@code TableModel#getValueAt(int, int)}.<p>
 *
 * The following example implementation is based on a list of customer rows
 * and exposes the first and last name as well as the customer ages:<pre>
 * public class CustomerTableModel extends AbstractTableAdapter {
 *
 *     public CustomerTableModel() {
 *         super("Last Name", "First Name", "Age");
 *     }
 *
 *     public Object getValueAt(int rowIndex, int columnIndex) {
 *         Customer customer = (Customer) getRow(rowIndex);
 *         switch (columnIndex) {
 *             case 0 : return customer.getLastName();
 *             case 1 : return customer.getFirstName();
 *             case 2 : return customer.getAge();
 *             default: return null;
 *         }
 *     }
 *
 * }
 * </pre>
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.16 $
 *
 * @see javax.swing.ListModel
 * @see javax.swing.JTable
 *
 * @param <E>   the type of the ListModel elements
 */
public abstract class AbstractTableAdapter<E> extends AbstractTableModel
    implements ListModelBindable {

    /**
     * Listens to ListModel updates and fires TableModel change events.
     *
     * @see #createChangeHandler()
     */
    private final ListDataListener changeHandler;


    /**
     * Holds an optional array of column names that is used by the
     * default implementation of the TableModel methods
     * {@code #getColumnCount()} and {@code #getColumnName(int)}.
     *
     * @see #getColumnCount()
     * @see #getColumnName(int)
     */
    private final String[]  columnNames;


    /**
     * Refers to the ListModel that holds the table row elements
     * and reports changes in the structure and content. The elements of
     * the list model can be requested using {@code #getRow(int)}.
     * A typical subclass will use the elements to implement the
     * TableModel method {@code #getValueAt(int, int)}.
     *
     * @see #getRow(int)
     * @see #getRowCount()
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    private ListModel listModel;


    // Instance Creation ******************************************************

    /**
     * Constructs an AbstractTableAdapter with no ListModel set
     * and no predefined column names.
     *
     * @since 2.2
     */
    public AbstractTableAdapter() {
        this(null, (String[]) null);
    }


    /**
     * Constructs an AbstractTableAdapter on the given ListModel.
     * Subclasses that use this constructor must override the methods
     * {@code #getColumnCount()} and {@code #getColumnName(int)}.
     *
     * @param listModel   the ListModel that holds the row elements
     */
    public AbstractTableAdapter(ListModel listModel) {
        this(listModel, (String[]) null);
    }


    /**
     * Constructs an AbstractTableAdapter with the given column names.
     *
     * @param columnNames   the predefined column names
     *
     * @since 2.2
     */
    public AbstractTableAdapter(String... columnNames) {
        this(null, columnNames);
    }


    /**
     * Constructs an AbstractTableAdapter on the given ListModel using
     * the specified table column names. If the column names array is
     * non-{@code null}, it is copied to avoid external mutation.<p>
     *
     * Subclasses that invoke this constructor with a {@code null} column
     * name array must override the methods {@code #getColumnCount()} and
     * {@code #getColumnName(int)}.
     *
     * @param listModel   the ListModel that holds the row elements
     * @param columnNames optional column names
     */
    public AbstractTableAdapter(ListModel listModel, String... columnNames) {
        this.changeHandler = createChangeHandler();
        setListModel(listModel);

        if (columnNames == null || columnNames.length == 0) {
            this.columnNames = null;
        } else {
            this.columnNames = new String[columnNames.length];
            System.arraycopy(columnNames, 0, this.columnNames, 0,
                    columnNames.length);
        }
    }


    // Data *******************************************************************

    /**
     * @return the underlying ListModel
     *
     * @since 2.2
     */
    @Override
	public ListModel getListModel() {
        return listModel;
    }


    /**
     * Sets the given ListModel as new underlying ListModel.
     * Removes the list data listener from the previously set ListModel
     * - if any - and adds it to the new ListModel.
     *
     * @since 2.2
     */
    @Override
	public void setListModel(ListModel newListModel) {
        ListModel oldListModel = getListModel();
        if (oldListModel == newListModel) {
            return;
        }
        if (oldListModel != null) {
            oldListModel.removeListDataListener(changeHandler);
        }
        listModel = newListModel;
        fireTableDataChanged();
        if (newListModel != null) {
            newListModel.addListDataListener(changeHandler);
        }
    }


    // TableModel Implementation **********************************************

    /**
     * Returns the number of columns in the model. A JTable uses
     * this method to determine how many columns it should create and
     * display by default.<p>
     *
     * Subclasses must override this method if they don't provide an
     * array of column names in the constructor.
     *
     * @return the number of columns in the model
     * @throws NullPointerException  if the optional column names array
     *     has not been set in the constructor. In this case API users
     *     must override this method.
     *
     * @see #getColumnName(int)
     * @see #getRowCount()
     */
    @Override
	public int getColumnCount() {
        return columnNames.length;
    }


    /**
     * Returns the name of the column at the given column index.
     * This is used to initialize the table's column header name.
     * Note: this name does not need to be unique; two columns in a table
     * can have the same name.<p>
     *
     * Subclasses must override this method if they don't provide an
     * array of column names in the constructor.
     *
     * @param columnIndex   the index of the column
     * @return  the name of the column
     * @throws NullPointerException  if the optional column names array
     *     has not been set in the constructor. In this case API users
     *     must override this method.
     *
     * @see #getColumnCount()
     * @see #getRowCount()
     */
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }


    /**
     * Returns the number of rows in the model. A
     * {@code JTable} uses this method to determine how many rows it
     * should display.  This method should be quick, as it
     * is called frequently during rendering.
     *
     * @return the number of rows in the model
     *
     * @see #getRow(int)
     */
    @Override
	public final int getRowCount() {
        return listModel == null ? 0 : listModel.getSize();
    }


    // Misc *******************************************************************

    /**
     * Returns the row at the specified row index.
     *
     * @param index   row index in the underlying list model
     * @return the row at the specified row index.
     */
    public final E getRow(int index) {
        return (E) listModel.getElementAt(index);
    }


    // Event Handling *********************************************************

    /**
     * Creates and returns a listener that handles changes
     * in the underlying list model.
     *
     * @return the listener that handles changes in the underlying ListModel
     */
    protected ListDataListener createChangeHandler() {
        return new ListDataChangeHandler();
    }


    /**
     * Listens to subject changes and fires a contents change event.
     */
    private final class ListDataChangeHandler implements ListDataListener {

        /**
         * Sent after the indices in the index0,index1
         * interval have been inserted in the data model.
         * The new interval includes both index0 and index1.
         *
         * @param evt  a {@code ListDataEvent} encapsulating the
         *    event information
         */
        @Override
		public void intervalAdded(ListDataEvent evt) {
            fireTableRowsInserted(evt.getIndex0(), evt.getIndex1());
        }


        /**
         * Sent after the indices in the index0,index1 interval
         * have been removed from the data model.  The interval
         * includes both index0 and index1.
         *
         * @param evt  a {@code ListDataEvent} encapsulating the
         *    event information
         */
        @Override
		public void intervalRemoved(ListDataEvent evt) {
            fireTableRowsDeleted(evt.getIndex0(), evt.getIndex1());
        }


        /**
         * Sent when the contents of the list has changed in a way
         * that's too complex to characterize with the previous
         * methods. For example, this is sent when an item has been
         * replaced. Index0 and index1 bracket the change.
         *
         * @param evt  a {@code ListDataEvent} encapsulating the
         *    event information
         */
        @Override
		public void contentsChanged(ListDataEvent evt) {
            int firstRow = evt.getIndex0();
            int lastRow = evt.getIndex1();
            fireTableRowsUpdated(firstRow, lastRow);
        }

    }

}
