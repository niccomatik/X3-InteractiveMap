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

package com.jgoodies.binding.value;

import static com.jgoodies.common.base.Preconditions.checkArgument;
import static com.jgoodies.common.base.Preconditions.checkNotNull;

import java.text.Format;
import java.text.ParseException;


/**
 * A factory that vends ValueModels that convert types, for example
 * Dates to Strings. More formally, a converting ValueModel <i>VM1</i>
 * converts the type <i>T2</i> of an object being held as a value in
 * one ValueModel <i>VM2</i> into another type <i>T1</i>.
 * When reading a value from VM1, instances of T2 are read from VM2
 * and are converted to T1. When storing a new value to VM1,
 * the type converter will perform the inverse conversion and
 * will convert an instance of T1 to T2.<p>
 *
 * Type converters should be used judiciously. To bind non-Strings 
 * to a text UI component
 * you should favor to use a {@link javax.swing.JFormattedTextField}.
 * They provide a powerful means to convert Strings to objects
 * and handle many cases that arise around invalid input.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.12 $
 *
 * @see     ValueModel
 * @see     Format
 * @see     javax.swing.JFormattedTextField
 */
public final class ConverterFactory {

    private ConverterFactory() {
        // Overrides default constructor; prevents instantiation.
    }

    // Factory Methods ********************************************************

    /**
     * Creates and returns a ValueModel that negates Booleans and leaves
     * <code>null</code> unchanged.
     *
     * @param booleanSource  a Boolean ValueModel
     * @return a ValueModel that inverts Booleans
     *
     * @throws NullPointerException if the {@code booleanSource} is {@code null}
     */
    public static ValueModel createBooleanNegator(ValueModel booleanSource) {
        return new ConverterValueModel(
        		booleanSource,
        		new BooleanNegator());
    }


    /**
     * Creates and returns a ValueModel that converts Booleans
     * to the associated of the two specified strings, and vice versa.
     * {@code null} is mapped to an empty string.
     * Ignores cases when setting a text.
     *
     * @param booleanSubject  a Boolean ValueModel
     * @param trueText      the text associated with {@code Boolean.TRUE}
     * @param falseText     the text associated with {@code Boolean.FALSE}
     *
     * @return a ValueModel that converts boolean to the associated text
     *
     * @throws NullPointerException if {@code booleanSource}, 
     *     {@code trueText}, {@code falseText} or {@code nullText} is {@code null}
     * @throws IllegalArgumentException if {@code trueText} equals {@code falseText}
     */
    public static ValueModel createBooleanToStringConverter(
            ValueModel booleanSubject,
            String trueText,
            String falseText) {
        return createBooleanToStringConverter(
                booleanSubject,
                trueText,
                falseText,
                "");
    }


    /**
     * Creates and returns a ValueModel that converts Booleans
     * to the associated of the two specified strings, and vice versa.
     * {@code null} is mapped to the specified text.
     * Ignores cases when setting a text.
     *
     * @param booleanSource  a Boolean ValueModel
     * @param trueText      the text associated with {@code Boolean.TRUE}
     * @param falseText     the text associated with {@code Boolean.FALSE}
     * @param nullText      the text associated with {@code null}
     *
     * @return a ValueModel that converts boolean to the associated text
     *
     * @throws NullPointerException if {@code booleanSource}, 
     *     {@code trueText}, {@code falseText} or {@code nullText} is {@code null}
     * @throws IllegalArgumentException if {@code trueText} equals {@code falseText}
     */
    public static ValueModel createBooleanToStringConverter(
            ValueModel booleanSource,
            String trueText,
            String falseText,
            String nullText) {
        return new ConverterValueModel(
        		booleanSource,
        		new BooleanToStringConverter(trueText, falseText, nullText));
    }
    
    
    /**
     * Creates and returns a ValueModel that converts Doubles using the
     * specified multiplier.<p>
     *
     * <strong>Examples:</strong>
     * multiplier=100, Double(1.23) -> Double(123),
     * multiplier=1000, Double(1.23) -> Double(1230)
     *
     * @param doubleSource  a Double ValueModel
     * @param multiplier the multiplier used for the conversion
     *
     * @return a ValueModel that converts Doubles using the specified multiplier
     *
     * @throws NullPointerException if {@code doubleSource} is {@code null}
     *
     * @since 1.0.2
     */
    public static ValueModel createDoubleConverter(
            ValueModel doubleSource, double multiplier) {
        return new ConverterValueModel(
        		doubleSource,
        		new DoubleConverter(multiplier));
    }


    /**
     * Creates and returns a ValueModel that converts Doubles to Integer,
     * and vice versa.
     *
     * @param doubleSource  a Double ValueModel
     *
     * @return a ValueModel that converts Doubles to Integer
     *
     * @throws NullPointerException if {@code doubleSource} is {@code null}
     */
    public static ValueModel createDoubleToIntegerConverter(
            ValueModel doubleSource) {
        return createDoubleToIntegerConverter(doubleSource, 1);
    }


    /**
     * Creates and returns a ValueModel that converts Doubles to Integer,
     * and vice versa. The multiplier can be used to convert Doubles
     * to percent, permill, etc. For a percentage, set the multiplier to be 100,
     * for a permill, set the multiplier to be 1000.<p>
     *
     * <strong>Examples:</strong>
     * multiplier=100, Double(1.23) -> Integer(123),
     * multiplier=1000, Double(1.23) -> Integer(1230)
     *
     * @param doubleSource  a Double ValueModel
     * @param multiplier the multiplier used to convert the Double to Integer
     *
     * @return a ValueModel that converts Doubles to Integer
     *
     * @throws NullPointerException if {@code doubleSource} is {@code null}
     */
    public static ValueModel createDoubleToIntegerConverter(
            ValueModel doubleSource, int multiplier) {
        return new ConverterValueModel(
        		doubleSource, 
        		new DoubleToIntegerConverter(multiplier));
    }


    /**
     * Creates and returns a ValueModel that converts Floats using the
     * specified multiplier.<p>
     *
     * <strong>Examples:</strong>
     * multiplier=100, Float(1.23) -> Float(123),
     * multiplier=1000, Float(1.23) -> Float(1230)
     *
     * @param floatSource  a Float ValueModel
     * @param multiplier the multiplier used for the conversion
     *
     * @return a ValueModel that converts Float using the specified multiplier
     *
     * @throws NullPointerException if {@code floatSource} is {@code null}
     *
     * @since 1.0.2
     */
    public static ValueModel createFloatConverter(
            ValueModel floatSource, float multiplier) {
        return new ConverterValueModel(
        		floatSource,
        		new FloatConverter(multiplier));
    }


    /**
     * Creates and returns a ValueModel that converts Floats to Integer,
     * and vice versa.
     * s
     * @param floatSource  a Float ValueModel
     *
     * @return a ValueModel that converts Floats to Integer
     *
     * @throws NullPointerException if {@code floatSource} is {@code null}
     */
    public static ValueModel createFloatToIntegerConverter(ValueModel floatSource) {
        return createFloatToIntegerConverter(floatSource, 1);
    }


    /**
     * Creates and returns a ValueModel that converts Floats to Integer,
     * and vice versa. The multiplier can be used to convert Floats
     * to percent, permill, etc. For a percentage, set the multiplier to be 100,
     * for a permill, set the multiplier to be 1000.
     *
     * @param floatSource  a Float ValueModel
     * @param multiplier the multiplier used to convert the Float to Integer
     *
     * @return a ValueModel that converts Floats to Integer
     *
     * @throws NullPointerException if {@code floatSource} is {@code null}
     */
    public static ValueModel createFloatToIntegerConverter(
            ValueModel floatSource, int multiplier) {
        return new ConverterValueModel(
        		floatSource,
        		new FloatToIntegerConverter(multiplier));
    }


    /**
     * Creates and returns a ValueModel that converts Integers using the
     * specified multiplier.<p>
     *
     * Examples: multiplier=100, Integer(3) -> Integer(300),
     * multiplier=1000, Integer(3) -> Integer(3000)
     *
     * @param integerSource  a Integer ValueModel
     * @param multiplier the multiplier used for the conversion
     *
     * @return a ValueModel that converts Integers using the specified multiplier
     *
     * @throws NullPointerException if {@code integerSource} is {@code null}
     *
     * @since 1.0.2
     */
    public static ValueModel createIntegerConverter(
            ValueModel integerSource, double multiplier) {
        return new ConverterValueModel(
        		integerSource,
        		new IntegerConverter(multiplier));
    }


    /**
     * Creates and returns a ValueModel that converts Long using the
     * specified multiplier.<p>
     *
     * Examples: multiplier=100, Long(3) -> Long(300),
     * multiplier=1000, Long(3) -> Long(3000)
     *
     * @param longSource  a Long ValueModel
     * @param multiplier the multiplier used for the conversion
     *
     * @return a ValueModel that converts Longs using the specified multiplier
     *
     * @throws NullPointerException if {@code longSource} is {@code null}
     *
     * @since 1.0.2
     */
    public static ValueModel createLongConverter(
            ValueModel longSource, double multiplier) {
        return new ConverterValueModel(
        		longSource, 
        		new LongConverter(multiplier));
    }


    /**
     * Creates and returns a ValueModel that converts Longs to Integer
     * and vice versa.<p>
     *
     * <strong>Constraints:</strong> The subject is of type {@code Long},
     * values written to the converter are of type {@code Integer}.
     *
     * @param longSubject  a Long ValueModel
     * @return a ValueModel that converts Longs to Integer
     *
     * @throws NullPointerException if the subject is {@code null}
     */
    public static ValueModel createLongToIntegerConverter(ValueModel longSubject) {
        return createLongToIntegerConverter(longSubject, 1);
    }


    /**
     * Creates and returns a ValueModel that converts Longs to Integer
     * and vice versa.<p>
     *
     * <strong>Constraints:</strong> The subject is of type {@code Long},
     * values written to the converter are of type {@code Integer}.
     *
     * @param longSource  a Long ValueModel
     * @param multiplier   used to multiply the Long when converting to Integer
     * @return a ValueModel that converts Longs to Integer
     *
     * @throws NullPointerException if {@code longSource} is {@code null}
     */
    public static ValueModel createLongToIntegerConverter(
            ValueModel longSource, int multiplier) {
        return new ConverterValueModel(
        		longSource, 
        		new LongToIntegerConverter(multiplier));
    }


    /**
     * Creates and returns a ValueModel that converts objects to Strings
     * and vice versa. The conversion is performed by a {@code Format}.<p>
     *
     * <strong>Constraints:</strong> The source is of type {@code Object};
     * it must be formattable and parsable via the given {@code Format}.
     *
     * @param source  the underlying ValueModel.
     * @param format   the {@code Format} used to format and parse
     *
     * @return a ValueModel that converts objects to Strings and vice versa
     *
     * @throws NullPointerException if {@code source} or {@code format}
     *         is {@code null}
     */
    public static ValueModel createStringConverter(ValueModel source, Format format) {
        return new ConverterValueModel(
        		source, new StringConverter(format));
    }


    // Converter Implementations **********************************************

    /**
     * Negates Booleans leaving null unchanged. Maps Boolean.TRUE
     * to Boolean.FALSE, Boolean.FALSE to Boolean.TRUE, and null to null.
     */
    public static final class BooleanNegator implements BindingConverter {

        /**
         * Negates Booleans leaving null unchanged.
         * Maps Boolean.TRUE to Boolean.FALSE,
         * Boolean.FALSE to Boolean.TRUE, and null to null.
         *
         * @param sourceValue   the subject value to invert
         * @return the text that represents the subject value
         *
         * @throws ClassCastException if the subject's value is not a Boolean
         */
        @Override
        public Object targetValue(Object sourceValue) {
            return negate(sourceValue);
        }


        /**
         * Inverts the given Boolean and sets it as the subject's new value.
         *
         * @param targetValue the value to be inverted and set as new subject value
         * @throws ClassCastException if the new value is not a Boolean
         * @throws IllegalArgumentException if the new value does neither match
         *     the trueText nor the falseText
         */
        @Override
		public Object sourceValue(Object targetValue) {
            return negate(targetValue);
        }


        /**
         * Negates Booleans leaving null unchanged.
         * Maps Boolean.TRUE to Boolean.FALSE ,
         * Boolean.FALSE to Boolean.TRUE, and null to null.
         *
         * @param value   the value to invert
         * @return the inverted Boolean value, or null if value is null
         *
         * @throws ClassCastException if the value is not a Boolean
         */
        private static Boolean negate(Object value) {
            if (value == null) {
                return null;
            } else if (Boolean.TRUE.equals(value)) {
                return Boolean.FALSE;
            } else if (Boolean.FALSE.equals(value)) {
                return Boolean.TRUE;
            } else {
                throw new ClassCastException("The value must be a Boolean.");
            }
        }

    }


    /**
     * Converts Booleans to Strings and vice-versa using given texts for
     * true, false, and null. Throws a ClassCastException if the value
     * to convert is not a Boolean, or not a String for the reverse conversion.
     */
    public static final class BooleanToStringConverter implements BindingConverter {

        private final String trueText;
        private final String falseText;
        private final String nullText;

        BooleanToStringConverter(
                String trueText,
                String falseText,
                String nullText) {
            this.trueText  = checkNotNull(trueText, "The trueText must not be null.");
            this.falseText = checkNotNull(falseText, "The falseText must not be null.");
            this.nullText  = checkNotNull(nullText, "The nullText must not be null.");
            checkArgument(!trueText.equals(falseText),
                    "The trueText and falseText must be different.");
        }


        /**
         * Converts the subject value to associated text representation.
         * Rejects non-Boolean values.
         *
         * @param sourceValue the subject's new value
         * @return the text that represents the subject value
         *
         * @throws ClassCastException if the subject's value is not a Boolean
         */
        @Override
        public Object targetValue(Object sourceValue) {
            if (Boolean.TRUE.equals(sourceValue)) {
                return trueText;
            } else if (Boolean.FALSE.equals(sourceValue)) {
                return falseText;
            } else if (sourceValue == null) {
                return nullText;
            } else {
                throw new ClassCastException(
                "The subject value must be of type Boolean.");
            }
        }


        /**
         * Converts the given String and sets the associated Boolean as
         * the subject's new value. In case the new value equals neither
         * this class' trueText, nor the falseText, nor the nullText,
         * an IllegalArgumentException is thrown.
         *
         * @param targetValue  the value to be converted and set as new subject value
         * @throws ClassCastException if the new value is not a String
         * @throws IllegalArgumentException if the new value does neither match
         *     the trueText nor the falseText nor the nullText
         */
        @Override
		public Object sourceValue(Object targetValue) {
            if (!(targetValue instanceof String)) {
                throw new ClassCastException("The new value must be a string.");
            }

            String newString = (String) targetValue;
            if (trueText.equalsIgnoreCase(newString)) {
                return Boolean.TRUE;
            } else if (falseText.equalsIgnoreCase(newString)) {
                return Boolean.FALSE;
            } else if (nullText.equalsIgnoreCase(newString)) {
                return null;
            } else {
                throw new IllegalArgumentException(
                        "The new value must be one of: "
                      + trueText + '/'
                      + falseText + '/'
                      + nullText);
            }
        }

    }


    /**
     * Converts Doubles using a given multiplier.
     */
    public static final class DoubleConverter implements BindingConverter {

        private final double multiplier;

        DoubleConverter(double multiplier) {
            this.multiplier = multiplier;
        }

        /**
         * Converts the subject's value and returns a
         * corresponding {@code Double} using the multiplier.
         *
         * @param sourceValue  the subject's value
         * @return the converted subjectValue
         * @throws ClassCastException if the subject value is not of type
         *     {@code Double}
         */
        @Override
        public Object targetValue(Object sourceValue) {
            double doubleValue = ((Double) sourceValue).doubleValue();
            return Double.valueOf(doubleValue * multiplier);
        }

        /**
         * Converts a {@code Double} using the multiplier
         * and sets it as new value.
         *
         * @param targetValue  the {@code Double} object that shall be converted
         * @throws ClassCastException if the new value is not of type
         *     {@code Double}
         */
        @Override
		public Object sourceValue(Object targetValue) {
            double doubleValue = ((Double) targetValue).doubleValue();
            return Double.valueOf(doubleValue / multiplier);
        }

    }

    /**
     * Converts Doubles to Integers and vice-versa.
     */
    public static final class DoubleToIntegerConverter implements BindingConverter {

        private final int multiplier;

        DoubleToIntegerConverter(int multiplier) {
            this.multiplier = multiplier;
        }

        /**
         * Converts the subject's value and returns a
         * corresponding {@code Integer} value using the multiplier.
         *
         * @param sourceValue  the subject's value
         * @return the converted subjectValue
         * @throws ClassCastException if the subject value is not of type
         *     {@code Double}
         */
        @Override
        public Object targetValue(Object sourceValue) {
            double doubleValue = ((Double) sourceValue).doubleValue();
            if (multiplier != 1) {
                doubleValue *= multiplier;
            }
            return Integer.valueOf((int) Math.round(doubleValue));
        }

        /**
         * Converts a {@code Double} using the multiplier
         * and sets it as new value.
         *
         * @param targetValue  the {@code Integer} object that shall
         *     be converted
         * @throws ClassCastException if the new value is not of type
         *     {@code Integer}
         */
        @Override
		public Object sourceValue(Object targetValue) {
            double doubleValue = ((Integer) targetValue).doubleValue();
            if (multiplier != 1) {
                doubleValue /= multiplier;
            }
            return Double.valueOf(doubleValue);
        }

    }

    /**
     * Converts Floats using a given multiplier.
     */
    public static final class FloatConverter implements BindingConverter {

        private final float multiplier;

        FloatConverter(float multiplier) {
            this.multiplier = multiplier;
        }

        /**
         * Converts the subject's value and returns a
         * corresponding {@code Float} using the multiplier.
         *
         * @param sourceValue  the subject's value
         * @return the converted subjectValue
         * @throws ClassCastException if the subject value is not of type
         *     {@code Float}
         */
        @Override
        public Object targetValue(Object sourceValue) {
            float floatValue = ((Float) sourceValue).floatValue();
            return Float.valueOf(floatValue * multiplier);
        }

        /**
         * Converts a {@code Float} using the multiplier
         * and sets it as new value.
         *
         * @param targetValue  the {@code Float} object that shall be converted
         * @throws ClassCastException if the new value is not of type
         *     {@code Float}
         */
        @Override
		public Object sourceValue(Object targetValue) {
            float floatValue = ((Float) targetValue).floatValue();
            return Float.valueOf(floatValue / multiplier);
        }

    }

    /**
     * Converts Floats to Integers and vice-versa.
     */
    public static final class FloatToIntegerConverter implements BindingConverter {

        private final int multiplier;

        FloatToIntegerConverter(int multiplier) {
            this.multiplier = multiplier;
        }


        /**
         * Converts the subject's value and returns a
         * corresponding {@code Integer} using the multiplier.
         *
         * @param sourceValue  the subject's value
         * @return the converted subjectValue
         * @throws ClassCastException if the subject value is not of type
         *     {@code Float}
         */
        @Override
        public Object targetValue(Object sourceValue) {
            float floatValue = ((Float) sourceValue).floatValue();
            if (multiplier != 1) {
                floatValue *= multiplier;
            }
            return Integer.valueOf(Math.round(floatValue));
        }


        /**
         * Converts a {@code Float} using the multiplier and
         * sets it as new value.
         *
         * @param sourceValue  the {@code Integer} object that shall be converted
         * @throws ClassCastException if the new value is not of type
         *     {@code Integer}
         */
        @Override
		public Object sourceValue(Object sourceValue) {
            float floatValue = ((Integer) sourceValue).floatValue();
            if (multiplier != 1) {
                floatValue /= multiplier;
            }
            return Float.valueOf(floatValue);
        }

    }


    /**
     * Converts Longs using a given multiplier.
     */
    public static final class LongConverter implements BindingConverter {

        private final double multiplier;

        LongConverter(double multiplier) {
            this.multiplier = multiplier;
        }

        /**
         * Converts the subject's value and returns a
         * corresponding {@code Long} using the multiplier.
         *
         * @param sourceValue  the subject's value
         * @return the converted sourceValue
         * @throws ClassCastException if the subject value is not of type
         *     {@code Long}
         */
        @Override
        public Object targetValue(Object sourceValue) {
            double doubleValue = ((Long) sourceValue).doubleValue();
            return Long.valueOf((long) (doubleValue * multiplier));
        }

        /**
         * Converts a {@code Long} using the multiplier
         * and sets it as new value.
         *
         * @param targetValue  the {@code Long} object that shall be converted
         * @throws ClassCastException if the new value is not of type
         *     {@code Long}
         */
        @Override
		public Object sourceValue(Object targetValue) {
            double doubleValue = ((Long) targetValue).doubleValue();
            return Long.valueOf((long) (doubleValue / multiplier));
        }

    }

    /**
     * Converts Integers using a given multiplier.
     */
    public static final class IntegerConverter implements BindingConverter {

        private final double multiplier;

        IntegerConverter(double multiplier) {
            this.multiplier = multiplier;
        }

        /**
         * Converts the subject's value and returns a
         * corresponding {@code Integer} using the multiplier.
         *
         * @param sourceValue  the subject's value
         * @return the converted subjectValue
         * @throws ClassCastException if the subject value is not of type
         *     {@code Integer}
         */
        @Override
        public Object targetValue(Object sourceValue) {
            double doubleValue = ((Integer) sourceValue).doubleValue();
            return Integer.valueOf((int) (doubleValue * multiplier));
        }

        /**
         * Converts a {@code Integer} using the multiplier
         * and sets it as new value.
         *
         * @param newValue  the {@code Integer} object that shall be converted
         * @throws ClassCastException if the new value is not of type
         *     {@code Integer}
         */
        @Override
		public Object sourceValue(Object newValue) {
            double doubleValue = ((Integer) newValue).doubleValue();
            return Integer.valueOf((int) (doubleValue / multiplier));
        }

    }

    /**
     * Converts Longs to Integers and vice-versa.
     */
    public static final class LongToIntegerConverter implements BindingConverter {

        private final int multiplier;

        LongToIntegerConverter(int multiplier) {
            this.multiplier = multiplier;
        }

        /**
         * Converts the subject's value and returns a
         * corresponding {@code Integer}.
         *
         * @param sourceValue  the subject's value
         * @return the converted sourceValue
         * @throws ClassCastException if {@code sourceValue} is not of type
         *     {@code Float}
         */
        @Override
        public Object targetValue(Object sourceValue) {
            int intValue = ((Long) sourceValue).intValue();
            if (multiplier != 1) {
                intValue *= multiplier;
            }
            return Integer.valueOf(intValue);
        }

        /**
         * Converts an Integer to Long and sets it as new value.
         *
         * @param targetValue  the {@code Integer} object that represents
         *     the percent value
         * @throws ClassCastException if {@code targetValue} is not of type
         *     {@code Integer}
         */
        @Override
		public Object sourceValue(Object targetValue) {
            long longValue = ((Integer) targetValue).longValue();
            if (multiplier != 1) {
                longValue /= multiplier;
            }
            return Long.valueOf(longValue);
        }

    }


    /**
     * Converts Values to Strings and vice-versa using a given Format.
     */
    public static final class StringConverter implements BindingConverter {

        /**
         * Holds the {@code Format} used to format and parse.
         */
        private final Format format;


        // Instance Creation **************************************************

        /**
         * Constructs a {@code StringConverter} on the given
         * subject using the specified {@code Format}.
         *
         * @param format   the {@code Format} used to format and parse
         * @throws NullPointerException if the subject or the format is null.
         */
        StringConverter(Format format) {
            this.format = checkNotNull(format, "The format must not be null.");
        }


        // Implementing Abstract Behavior *************************************

        /**
         * Formats the source value and returns a String representation.
         *
         * @param sourceValue  the source value
         * @return the formatted sourceValue
         */
        @Override
        public Object targetValue(Object sourceValue) {
            return format.format(sourceValue);
        }


        /**
         * Parses the given String encoding and sets it as the subject's
         * new value. Silently catches {@code ParseException}.
         *
         * @param targetValue  the value to be converted and set as new subject value
         */
        @Override
		public Object sourceValue(Object targetValue) {
            try {
                return format.parseObject((String) targetValue);
            } catch (ParseException e) {
                throw new IllegalArgumentException("Cannot parse the target value " + targetValue, e);
            }
        }

    }


}
