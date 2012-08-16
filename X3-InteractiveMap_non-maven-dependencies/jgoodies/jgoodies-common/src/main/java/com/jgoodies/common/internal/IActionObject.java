/*
 * Copyright (c) 2006-2012 JGoodies Karsten Lentzsch. All Rights Reserved.
 *
 * This software is the proprietary information of JGoodies Karsten Lentzsch.
 * Use is subject to license terms.
 *
 */

package com.jgoodies.common.internal;

import javax.swing.Action;


/**
 * Describes an object that can look up an Action for a given Action name.<p>
 * 
 * <strong>Note:</strong> This class is not part of the public JGoodies Common API.
 * It's intended for implementation purposes only.
 * The class's API may change at any time.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.1 $
 * 
 * @since 1.4
 */
public interface IActionObject {


    /**
     * Looks up and returns an Action for the given action name.
     *
     * @param actionName   the string used to look up the Action
     * @return the Action with the given action name - if any
     *
     * @throws NullPointerException if {@code actionName} is {@code null}
     */
    Action getAction(String actionName);


}
