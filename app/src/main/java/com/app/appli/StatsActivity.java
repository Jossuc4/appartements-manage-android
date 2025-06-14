package com.app.appli;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.List;

import ir.mahozad.android.PieChart;
import ir.mahozad.android.unit.Dimension;

public class StatsActivity extends AppCompatActivity {

    private float dpToPx(float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }

    private float spToPx(float sp) {
        return sp * getResources().getDisplayMetrics().scaledDensity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stats);

        PieChart pieChart = findViewById(R.id.pieChart);

        List<PieChart.Slice> slices = Arrays.asList(
                new PieChart.Slice(
                        0.2f,
                        ContextCompat.getColor(this, R.color.medium_text_color),
                        Color.rgb(31, 199, 255),
                        "Label C",
                        Color.rgb(0, 199, 25), 18f, null, null, null, null, null, null, null, null, null,
                        "legend C", null, null, null, null, null,
                        null, null, null, null, null, 0.1f
                ),
                new PieChart.Slice(
                        0.2f,
                        Color.rgb(0, 162, 216),
                        Color.rgb(31, 199, 255),
                        "Label C",
                        null, null, null, null, null, null, null, null, null, null, null,
                        "legend C", null, null, null, null, null,
                        null, null, null, null, null, 0.1f
                ),
                new PieChart.Slice(
                        0.2f,
                        Color.rgb(0, 162, 216),
                        Color.rgb(31, 199, 255),
                        "Label C",
                        Color.rgb(0, 1, 25), null, null, null, null, null, null, null, null, null, null,
                        "legend C", null, null, null, null, null,
                        null, null, null, null, null, 0.1f
                ),
                new PieChart.Slice(
                        0.2f,
                        Color.rgb(0, 162, 216),
                        Color.rgb(31, 199, 255),
                        "Label C",
                        Color.rgb(0, 100, 25), null, null, null, null, null, null, null, null, null, null,
                        "legend C", null, null, null, null, null,
                        null, null, null, null, null, 0.1f
                ),
                new PieChart.Slice(
                        0.2f,
                        Color.rgb(35, 162, 216),
                        Color.rgb(0, 199, 25),
                        "Label C",
                        Color.rgb(0, 199, 25), 12f, null, null, null, null, null, null, null, null, null,
                        "legend C", Color.rgb(125,12,22), 15f, null, null, null,
                        null, null, null, null, null, 0.1f
                )
        );


        pieChart.setSlices(slices);
        pieChart.setStartAngle(0);
        pieChart.setLabelType(PieChart.LabelType.OUTSIDE_CIRCULAR_INWARD);
        pieChart.setLabelIconsPlacement(PieChart.IconPlacement.START);
        pieChart.setGradientType(PieChart.GradientType.RADIAL);
        pieChart.setLegendEnabled(true);
        pieChart.setLegendPosition(PieChart.LegendPosition.TOP);
        pieChart.setLegendArrangement(PieChart.LegendArrangement.VERTICAL);
        pieChart.setLegendBoxBorderEnabled(true);
        pieChart.setLegendBoxBorder(new Dimension.PX(2));
        pieChart.setLegendBoxBorderCornerRadius(new Dimension.PX(8));
        pieChart.setLegendTitleMargin(new Dimension.PX(10));
        pieChart.setLegendsMargin(new Dimension.PX(8));
        pieChart.setLegendsPercentageMargin(new Dimension.PX(8));
        pieChart.setLegendsSize(new Dimension.PX(11));
        pieChart.setLegendsPercentageSize(new Dimension.PX(11));
        pieChart.setLegendsIcon(PieChart.DefaultIcons.SLICE2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}