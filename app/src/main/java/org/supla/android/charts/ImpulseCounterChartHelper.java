package org.supla.android.charts;

/*
 Copyright (C) AC SOFTWARE SP. Z O.O.

 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License
 as published by the Free Software Foundation; either version 2
 of the License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.supla.android.R;
import org.supla.android.Trace;
import org.supla.android.db.DbHelper;
import org.supla.android.db.SuplaContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ImpulseCounterChartHelper extends ChartHelper {

    private String Unit = "";

    public ImpulseCounterChartHelper(Context context) {
        super(context);
    }

    protected Cursor getCursor(DbHelper DBH,
                               SQLiteDatabase db, int channelId, String dateFormat) {
        return DBH.getImpulseCounterMeasurements(db, channelId, dateFormat);
    }

    protected float[] getValues(Cursor c) {
        float[] phases = new float[1];

        phases[0] = (float) c.getDouble(
                c.getColumnIndex(
                        SuplaContract.ImpulseCounterLogEntry.COLUMN_NAME_CALCULATEDVALUE));

        return phases;
    }

    protected long getTimestamp(Cursor c) {
        return c.getLong(c.getColumnIndex(
                SuplaContract.ImpulseCounterLogEntry.COLUMN_NAME_TIMESTAMP));
    }

    protected String[] getStackLabels() {
        return new String[]{Unit};
    }

    protected List<Integer> getColors() {
        Resources res = context.getResources();

        List<Integer> Colors = new ArrayList<Integer>(1);
        Colors.add(res.getColor(R.color.ic_chart_value));

        return Colors;
    }

    public void loadImpulseCounterMeasurements(int channelId, ChartType ctype) {

        if (!this.ctype.equals(ctype)) {
            this.ctype = ctype;
        }

        switch (ctype) {
            case Bar_Minutely:
            case Bar_Hourly:
            case Bar_Daily:
            case Bar_Monthly:
            case Bar_Yearly:
                loadBarChart(channelId, ctype);
                break;
        }

    }

    public void loadImpulseCounterMeasurements(int channelId) {
        loadBarChart(channelId, ctype);
    }

    public void setUnit(String unit) {
        Unit = unit;
    }
}