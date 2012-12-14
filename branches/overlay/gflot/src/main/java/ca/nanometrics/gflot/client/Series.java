/*
 * Copyright (c) 2008 Nanometrics Inc.
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a copy
 *	of this software and associated documentation files (the "Software"), to deal
 *	in the Software without restriction, including without limitation the rights
 *	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *	copies of the Software, and to permit persons to whom the Software is
 *	furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in
 *	all copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *	THE SOFTWARE.
 */
package ca.nanometrics.gflot.client;

import ca.nanometrics.gflot.client.options.CommonSeriesOptions;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Represent a single series
 * 
 * @author AlexanderDeleon
 */
public class Series
    extends CommonSeriesOptions<Series>
{
    /**
     * Creates a {@link Series}
     */
    public static final Series create()
    {
        return JavaScriptObject.createObject().cast();
    }

    private static final String DATA_KEY = "data";
    private static final String COLOR_KEY = "color";
    private static final String LABEL_KEY = "label";
    private static final String X_AXIS_KEY = "xaxis";
    private static final String Y_AXIS_KEY = "yaxis";
    private static final String CLICKABLE_KEY = "clickable";
    private static final String HOVERABLE_KEY = "hoverable";
    private static final String PERCENT_KEY = "percent";
    private static final String ANGLE_KEY = "angle";
    private static final String FILL_BETWEEN_KEY = "fillBetween";
    private static final String ID_KEY = "id";

    protected Series()
    {
    }

    final Series setData( SeriesData data )
    {
        put( DATA_KEY, data );
        return this;
    }

    /**
     * Returns the data associated to this series.
     * <p>
     * Be careful, the data can be null if the method setVisible(false) has been called on its associated SeriesHandler.
     * In this case, use the method getDate() of the SeriesHandler
     * <p>
     */
    public final SeriesData getData()
    {
        return getJsObject( DATA_KEY );
    }

    /**
     * Binds this series to a different X axis, i.e. "xaxis: 2"
     * 
     * @param axis the axis number such as 2, 3, etc.
     */
    public final Series setXAxis( int axis )
    {
        assert axis > 0 : "axis starts at 1";
        put( X_AXIS_KEY, axis );
        return this;
    }

    /**
     * @return the x axis number
     */
    public final int getXAxis()
    {
        Integer axis = getInteger( X_AXIS_KEY );
        if ( null == axis )
        {
            return 1;
        }
        return axis;
    }

    /**
     * Binds this series to a different Y axis, i.e. "yaxis: 2"
     * 
     * @param axis the axis number such as 2, 3, etc.
     */
    public final Series setYAxis( int axis )
    {
        assert axis > 0 : "axis starts at 1";
        put( Y_AXIS_KEY, axis );
        return this;
    }

    /**
     * @return the y axis number
     */
    public final int getYAxis()
    {
        Integer axis = getInteger( Y_AXIS_KEY );
        if ( null == axis )
        {
            return 1;
        }
        return axis;
    }

    /**
     * Set the color. If you don't specify color, the series will get a color from the auto-generated colors.
     */
    public final Series setColor( String color )
    {
        put( COLOR_KEY, color );
        return this;
    }

    /**
     * @return the color
     */
    public final String getColor()
    {
        return getString( COLOR_KEY );
    }

    /**
     * Set which of auto-generated colors to select, e.g. 0 will get color no. 0, etc. It is mostly useful if you let
     * the user add and remove series, in which case you can hard-code the color index to prevent the colors from
     * jumping around between the series.
     */
    public final Series setAutoGeneratedColor( int color )
    {
        put( COLOR_KEY, color );
        return this;
    }

    /**
     * @return the auto-generated color to select
     */
    public final Integer getAutoGeneratedColor()
    {
        return getInteger( COLOR_KEY );
    }

    /**
     * Set the label. The label is used for the legend, if you don't specify one, the series will not show up in the
     * legend.
     */
    public final Series setLabel( String label )
    {
        put( LABEL_KEY, label );
        return this;
    }

    /**
     * @return the label
     */
    public final String getLabel()
    {
        return getString( LABEL_KEY );
    }

    /**
     * Set if this series will listen for click events.
     */
    public final Series setClickable( boolean clickable )
    {
        put( CLICKABLE_KEY, clickable );
        return this;
    }

    /**
     * @return true if the series is clickable
     */
    public final Boolean getClickable()
    {
        return getBoolean( CLICKABLE_KEY );
    }

    /**
     * Set if this series will listen for mouse move events.
     */
    public final Series setHoverable( boolean hoverable )
    {
        put( HOVERABLE_KEY, hoverable );
        return this;
    }

    /**
     * @return true if the series is hoverable
     */
    public final Boolean getHoverable()
    {
        return getBoolean( HOVERABLE_KEY );
    }

    /**
     * Only available for pie plot!
     * 
     * @return the percent of the series
     */
    public final Double getPercent()
    {
        return getDouble( PERCENT_KEY );
    }

    /**
     * Only available for pie plot!
     * 
     * @return the angle of the series
     */
    public final Double getAngle()
    {
        return getDouble( ANGLE_KEY );
    }

    /**
     * Provides the identifier of another series which is used to fill the area between these two series. If this
     * identifier was given as a number that doesn't appear as an id in the series, it is interpreted as the index in
     * the array instead (so fillBetween: 0 can also mean the first series). Only for the fillbetween plugin!
     * 
     * @return an identifier of another series (a {@link String}, {@link Integer} or null).
     */
    public final Object getFillBetween()
    {
        return getObject( FILL_BETWEEN_KEY );
    }

    /**
     * Provides the identifier of another series which is used to fill the area between these two series. If this
     * identifier was given as a number that doesn't appear as an id in the series, it is interpreted as the index in
     * the array instead (so fillBetween: 0 can also mean the first series). Only for the fillbetween plugin!
     * 
     * @return an identifier of another series or null if it is not present or not a String.
     */
    public final String getFillBetweenAsString()
    {
        Object fillBetween = getFillBetween();
        return (String) ( fillBetween instanceof String ? fillBetween : null );
    }

    /**
     * Provides the identifier of another series which is used to fill the area between these two series. If this
     * identifier was given as a number that doesn't appear as an id in the series, it is interpreted as the index in
     * the array instead (so fillBetween: 0 can also mean the first series). Only for the fillbetween plugin!
     * 
     * @return an identifier of another series or null if it is not present or not a Integer.
     */
    public final Integer getFillBetweenAsInteger()
    {
        Object fillBetween = getFillBetween();
        return (Integer) ( fillBetween instanceof Integer ? fillBetween : null );
    }

    /**
     * Defines the ID associated with another series which is used to fill the area between these two series. Only for
     * the fillbetween plugin!
     * 
     * @param seriesId the ID identifying another series.
     */
    public final Series setFillBetween( String seriesId )
    {
        put( FILL_BETWEEN_KEY, seriesId );
        return this;
    }

    /**
     * Defines another series which is used to fill the area between these two series. If this identifier was given as a
     * number that doesn't appear as an id in the series, it is interpreted as the index in the array instead (so
     * fillBetween: 0 can also mean the first series). Only for the fillbetween plugin!
     * 
     * @param seriesIndex an identifier of another series.
     */
    public final Series setFillBetween( int seriesIndex )
    {
        put( FILL_BETWEEN_KEY, seriesIndex );
        return this;
    }

    /**
     * Provides the ID given to this series. Only for the fillbetween plugin!
     * 
     * @return the series ID
     */
    public final String getId()
    {
        return getString( ID_KEY );
    }

    /**
     * Defines the ID for this series. Only for the fillbetween plugin!
     * 
     * @param id the series ID
     */
    public final Series setId( String id )
    {
        put( ID_KEY, id );
        return this;
    }

}
