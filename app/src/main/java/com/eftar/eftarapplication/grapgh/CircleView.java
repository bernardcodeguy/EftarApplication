package com.eftar.eftarapplication.grapgh;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class CircleView extends View {
    private int degree;

    public CircleView(Context context) {
        super(context);
        degree = 0;
    }

    public void setDegree(int degree) {
        this.degree = degree;
        invalidate(); // Request a redraw
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLUE);

        RectF rectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        // Draw the full circle
        canvas.drawArc(rectF, 0, 360, false, paint);

        // Draw the arc based on the degree value
        paint.setColor(Color.RED);
        canvas.drawArc(rectF, -90, degree, false, paint);
    }
}
