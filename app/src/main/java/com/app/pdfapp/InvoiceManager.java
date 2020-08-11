package com.app.pdfapp;

import android.content.Context;

public class InvoiceManager {

    private PdfGenerater pdfGenerater;
    private Profile profile;

    public InvoiceManager(Context context) {
        pdfGenerater = new PdfGenerater(context);
    }

    public void createInvoice() {

    }
}
