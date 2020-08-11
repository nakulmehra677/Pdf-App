package com.app.pdfapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfGenerater {

    private Context context;
    private PdfDocument pdfDocument;
    private Paint paint;
    private Canvas canvas;
    private int colorPrimary, colorLightGray, colorGray, colorDarkGray;

    private int pageWidth, pageHeight;

    private int xItemNumber, xItem, xHsn, xQuantity, xPrice, xDiscount, xAmount;
    private int yTable, yNetAmount;

    public PdfGenerater(Context context) {
        this.context = context;
        pdfDocument = new PdfDocument();
        paint = new Paint();

        colorPrimary = Color.parseColor("#7986CB");
        colorLightGray = Color.parseColor("#ECEFF1");
        colorGray = Color.parseColor("#CFD8DC");
        colorDarkGray = Color.parseColor("#455A64");

        pageWidth = 400;
        pageHeight = 600;

        yTable = 170;
        yNetAmount = yTable + 60;

        xItemNumber = 45;
        xItem = 60;
        xHsn = pageWidth - 220;
        xQuantity = pageWidth - 180;
        xPrice = pageWidth - 130;
        xDiscount = pageWidth - 90;
        xAmount = pageWidth - 45;
    }

    public void createInvoice() {
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(
                pageWidth, pageHeight, 1
        ).create();

        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        canvas = page.getCanvas();

        setCompanyDetail();
        setTitle();
        setRecipientDetail();
        setShipTo();
        setInvoiceDetail();

        setTableTitleRow();

        List<String> list = new ArrayList<>();
        list.add("fff");
        list.add("fff");
        list.add("fff");
        list.add("fff");
        list.add("fff");
        list.add("fff");

        setTableItems(list);

        setAmountInWords();
        setTermsAndConditions();

        setNetAmount();

        setSignature();


        pdfDocument.finishPage(page);

        File file = new File(Environment.getExternalStorageDirectory(), "/hello.pdf");

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("errror", e.getMessage());
        }

        pdfDocument.close();
    }

    private void setCompanyDetail() {
        paint = new Paint();
        paint.setTextSize(10);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Business name", 40, 40, paint);

        paint = new Paint();
        paint.setTextSize(7);
        canvas.drawText("Phone no.:9876543210", 40, 54, paint);

        paint = new Paint();
        paint.setTextSize(7);
        canvas.drawText("New Delhi, Delhi", 40, 64, paint);

        paint = new Paint();
        paint.setTextSize(7);
        canvas.drawText("GSTIN: 3838939584FVE", 40, 74, paint);
    }

    private void setTitle() {
        paint = new Paint();
        paint.setTextSize(14);
        paint.setColor(colorDarkGray);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Tax Invoice", pageWidth / 2, 100, paint);
    }

    private void setInvoiceDetail() {
        paint = new Paint();
        paint.setTextSize(7);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Invoice no.: 15", pageWidth - 40, 40, paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Invoice Date: 16-07-2020", pageWidth - 40, 50, paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Due Date: 16-07-2020", pageWidth - 40, 60, paint);


        paint = new Paint();
        paint.setTextSize(7);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("(0 days from invoice date)", pageWidth - 40, 70, paint);
    }

    private void setRecipientDetail() {
        paint = new Paint();
        paint.setTextSize(8);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Bill To: ", 40, 100, paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("ABC private Ltd.", 40, 114, paint);

        paint = new Paint();
        paint.setTextSize(7);
        canvas.drawText("New Delhi, Delhi", 40, 124, paint);

        paint = new Paint();
        paint.setTextSize(7);
        canvas.drawText("Phone No.: 9876543219", 40, 134, paint);
    }

    private void setShipTo() {
        paint = new Paint();
        paint.setTextSize(8);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Ship To: ", pageWidth - 40, 100, paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("ABC private Ltd.", pageWidth - 40, 114, paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("New Delhi, Delhi", pageWidth - 40, 124, paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Phone No.: 9876543219", pageWidth - 40, 134, paint);
    }

    private void setTableTitleRow() {
        paint = new Paint();
        paint.setColor(colorLightGray);
        canvas.drawRect(40, 160, pageWidth - 40, 175, paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setColor(colorDarkGray);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("#", xItemNumber, 170, paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setColor(colorDarkGray);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Item", xItem, 170, paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setColor(colorDarkGray);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("HSN/SAC", xHsn, 170, paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setColor(colorDarkGray);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Quantity", xQuantity, 170, paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setColor(colorDarkGray);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Price unit", xPrice, 170, paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setColor(colorDarkGray);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Discount", xDiscount, 170, paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setColor(colorDarkGray);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Amount", xAmount, 170, paint);
    }

    private void setTableItems(List<String> list) {

        for (int i = 1; i <= list.size(); i++) {
            paint = new Paint();
            paint.setTextSize(7);
            canvas.drawText("#", xItemNumber, 170 + (i * 20), paint);

            paint = new Paint();
            paint.setTextSize(7);
            canvas.drawText("Item", xItem, 170 + (i * 20), paint);

            paint = new Paint();
            paint.setTextSize(7);
            paint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText("HSN/SAC", xHsn, 170 + (i * 20), paint);

            paint = new Paint();
            paint.setTextSize(7);
            paint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText("Quantity", xQuantity, 170 + (i * 20), paint);

            paint = new Paint();
            paint.setTextSize(7);
            paint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText("Price unit", xPrice, 170 + (i * 20), paint);

            paint = new Paint();
            paint.setTextSize(7);
            paint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText("Discount", xDiscount, 170 + (i * 20), paint);

            paint = new Paint();
            paint.setTextSize(7);
            paint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText("Amount", xAmount, 170 + (i * 20), paint);
        }

        paint = new Paint();
        paint.setColor(colorGray);
        canvas.drawLine(40, 180 + (list.size() * 20), pageWidth - 40, 180 + (list.size() * 20), paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Quantity", xQuantity, 195 + (list.size() * 20), paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Discount", xDiscount, 195 + (list.size() * 20), paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Amount", xAmount, 195 + (list.size() * 20), paint);

        paint = new Paint();
        paint.setColor(colorGray);
        canvas.drawLine(40, 205 + (list.size() * 20), pageWidth - 40, 205 + (list.size() * 20), paint);

        yNetAmount = yTable + 60 + (list.size() * 20);
    }

    private void setAmountInWords() {
        paint = new Paint();
        paint.setTextSize(7);
        paint.setColor(colorDarkGray);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("INVOICE AMOUNT IN WORDS", xItemNumber, yNetAmount, paint);

        paint = new Paint();
        paint.setColor(colorLightGray);
        canvas.drawRect(40, yNetAmount + 3, pageWidth / 2 - 10, yNetAmount + 14, paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setColor(colorDarkGray);
        canvas.drawText("TWENTY FIVE THOUSAND", xItemNumber, yNetAmount + 11, paint);
    }

    private void setTermsAndConditions() {
        paint = new Paint();
        paint.setTextSize(7);
        paint.setColor(colorDarkGray);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("TERMS AND CONDITIONS", xItemNumber, yNetAmount + 23, paint);

        paint = new Paint();
        paint.setColor(colorLightGray);
        canvas.drawRect(40, yNetAmount + 26, pageWidth / 2 - 10, yNetAmount + 37, paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setColor(colorDarkGray);
        canvas.drawText("Thank you for doing buisness with us.", xItemNumber, yNetAmount + 34, paint);
    }

    private void setNetAmount() {
        paint = new Paint();
        paint.setTextSize(7);
        canvas.drawText("Sub total", pageWidth / 2 + 15, yNetAmount, paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("\u20B9 " + "5000.00", pageWidth - 45, yNetAmount, paint);

        paint = new Paint();
        paint.setTextSize(7);
        canvas.drawText("Discount", pageWidth / 2 + 15, yNetAmount + 11, paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("\u20B9 " + "1500.00", pageWidth - 45, yNetAmount + 11, paint);

        paint = new Paint();
        paint.setColor(colorLightGray);
        canvas.drawRect(pageWidth / 2 + 10, yNetAmount + 14, pageWidth - 40, yNetAmount + 25, paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setColor(colorDarkGray);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Total", pageWidth / 2 + 15, yNetAmount + 22, paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setColor(colorDarkGray);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("\u20B9 " + "48500.00", pageWidth - 45, yNetAmount + 22, paint);

        paint = new Paint();
        paint.setTextSize(7);
        canvas.drawText("Received", pageWidth / 2 + 15, yNetAmount + 33, paint);

        paint = new Paint();
        paint.setTextSize(7);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("\u20B9 " + "0.00", pageWidth - 45, yNetAmount + 33, paint);

        paint = new Paint();
        paint.setTextSize(7);
        canvas.drawText("Balance", pageWidth / 2 + 15, yNetAmount + 44, paint);


        paint = new Paint();
        paint.setTextSize(7);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("\u20B9 " + "48500.00", pageWidth - 45, yNetAmount + 44, paint);
    }

    private void setSignature() {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.image);
        RectF rectF = new RectF(pageWidth - 150, pageHeight - 100, pageWidth - 40, pageHeight - 50);
        canvas.drawBitmap(bitmap, null, rectF, paint);

        paint = new Paint();
        paint.setTextSize(8);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Authorized signatory", pageWidth - 100, pageHeight - 40, paint);
    }
}
