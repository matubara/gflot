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

import java.util.HashMap;
import java.util.Map;

import ca.nanometrics.gflot.client.PlotModel.PlotModelListener;
import ca.nanometrics.gflot.client.event.PlotClickListener;
import ca.nanometrics.gflot.client.event.PlotHoverListener;
import ca.nanometrics.gflot.client.event.PlotSelectedListener;
import ca.nanometrics.gflot.client.event.PlotSelectingListener;
import ca.nanometrics.gflot.client.event.PlotUnselectedListener;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Alexander De Leon
 */
public class PlotWithInteractiveLegend
    extends Composite
    implements PlotWidget, PlotModelListener
{

    protected final PlotWidget plot;
    protected Panel legendPanel;
    private final Map<SeriesHandler, LegendItem> legend;

    public PlotWithInteractiveLegend( PlotWidget plot )
    {
        legend = new HashMap<SeriesHandler, LegendItem>();
        this.plot = plot;
        initWidget( createUi() );
        buildLegendFromModel( plot.getModel() );
    }

    private void buildLegendFromModel( PlotModel model )
    {
        for ( SeriesHandler seriesHandler : model.getHandlers() )
        {
            addSeriesToLegend( seriesHandler );
        }
        model.addListener( this );
    }

    public void addClickListener( PlotClickListener listener, boolean onlyOnDatapoint )
    {
        plot.addClickListener( listener, onlyOnDatapoint );
    }

    public void addHoverListener( PlotHoverListener listener, boolean onlyOnDatapoint )
    {
        plot.addHoverListener( listener, onlyOnDatapoint );
    }

    public void addSelectedListener( PlotSelectedListener listener )
    {
        plot.addSelectedListener( listener );
    }

    @Override
    public void addSelectingListener( final PlotSelectingListener listener )
    {
        plot.addSelectingListener( listener );
    }

    @Override
    public void addUnselectedListener( final PlotUnselectedListener listener )
    {
        plot.addUnselectedListener( listener );
    }

    @Override
    public PlotSelectionArea getSelection()
    {
        return plot.getSelection();
    }

    @Override
    public void setSelection( PlotSelectionArea area )
    {
        plot.setSelection( area );
    }

    @Override
    public void setSelection( PlotSelectionArea area, boolean preventEvent )
    {
        plot.setSelection( area, preventEvent );
    }

    @Override
    public void clearSelection()
    {
        plot.clearSelection();
    }

    @Override
    public void clearSelection( boolean preventEvent )
    {
        plot.clearSelection( preventEvent );
    }

    public int getHeight()
    {
        return plot.getHeight();
    }

    public int getWidth()
    {
        return plot.getWidth();
    }

    public void setHeight( int height )
    {
        plot.setHeight( height );
    }

    public void setLinearSelection( double x1, double x2 )
    {
        plot.setLinearSelection( x1, x2 );
    }

    public void setRectangularSelection( double x1, double y1, double x2, double y2 )
    {
        plot.setRectangularSelection( x1, y1, x2, y2 );
    }

    public void setWidth( int width )
    {
        plot.setWidth( width );
    }

    public void redraw()
    {
        plot.redraw();
    }

    public Widget getWidget()
    {
        return this;
    }

    public PlotModel getModel()
    {
        return plot.getModel();
    }

    public void addLegendWidget( SeriesHandler handler, Widget widget )
    {
        legend.get( handler ).addWidget( widget );
    }

    public void onAddSeries( PlotModel model, SeriesHandler handler )
    {
        addSeriesToLegend( handler );

    }

    public void onRemoveSeries( PlotModel model, SeriesHandler handler )
    {
        legendPanel.remove( legend.get( handler ) );
    }

    /* --------------------- helper methods -- */
    private void addSeriesToLegend( final SeriesHandler handler )
    {
        LegendItem item = createLegendItem( handler.getSeries().getColor(), handler.getSeries().getLabel() );
        item.addValueChangeHandler( new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange( ValueChangeEvent<Boolean> event )
            {
                handler.setVisible( event.getValue() );
                plot.redraw();
            }
        } );
        legend.put( handler, item );
        legendPanel.add( item );
    }

    protected LegendItem createLegendItem( String color, String label )
    {
        return new DefaultLegendItem( color, label );
    }

    protected Widget createUi()
    {
        VerticalPanel panel = new VerticalPanel();
        Widget plotWidget = plot.getWidget();

        legendPanel = new HorizontalPanel();

        panel.add( legendPanel );
        panel.add( plotWidget );

        return panel;
    }

    public void setSerieVisible( SeriesHandler handler, boolean visible, boolean redraw )
    {
        handler.setVisible( visible );
        legend.get( handler ).setValue( visible, false );
        if ( redraw )
        {
            plot.redraw();
        }
    }

    protected abstract class LegendItem
        extends Composite
        implements HasValue<Boolean>
    {

        final String color;

        final String label;

        public LegendItem( String color, String label )
        {
            this.color = color;
            this.label = label;

            init();
        }

        protected abstract void init();

        public abstract void addWidget( Widget widget );

    }

    /**
     * Class for the legend item widget
     */
    protected class DefaultLegendItem
        extends LegendItem
    {

        protected static final String COLOR_BAND_HEIGHT = "3px";

        protected HorizontalPanel m_labelsPanel;

        protected CheckBox checkBox;

        public DefaultLegendItem( String color, String label )
        {
            super( color, label );
        }

        @Override
        protected void init()
        {
            FlexTable table = new FlexTable();

            HTML colorBand =
                new HTML( "<div style=\"width: 100%; height: " + COLOR_BAND_HEIGHT + "; background-color: " + color
                    + ";\"></div>" );
            table.setWidget( 0, 0, colorBand );
            table.getFlexCellFormatter().setColSpan( 0, 0, 2 );

            checkBox = new CheckBox();
            checkBox.setValue( true );
            table.setWidget( 1, 0, checkBox );
            table.getCellFormatter().setHorizontalAlignment( 1, 0, HasHorizontalAlignment.ALIGN_LEFT );

            m_labelsPanel = new HorizontalPanel();
            m_labelsPanel.add( new Label( label ) );
            table.setWidget( 1, 1, m_labelsPanel );
            table.getCellFormatter().setHorizontalAlignment( 1, 1, HasHorizontalAlignment.ALIGN_LEFT );

            initWidget( table );
        }

        @Override
        public void addWidget( Widget widget )
        {
            m_labelsPanel.add( widget );
        }

        @Override
        public HandlerRegistration addValueChangeHandler( ValueChangeHandler<Boolean> handler )
        {
            return checkBox.addValueChangeHandler( handler );
        }

        @Override
        public Boolean getValue()
        {
            return checkBox.getValue();
        }

        @Override
        public void setValue( Boolean value )
        {
            checkBox.setValue( value );
        }

        @Override
        public void setValue( Boolean value, boolean fireEvents )
        {
            checkBox.setValue( value, fireEvents );
        }
    }

}
