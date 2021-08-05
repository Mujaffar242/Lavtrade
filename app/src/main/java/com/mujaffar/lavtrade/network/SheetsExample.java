package com.mujaffar.lavtrade.network;

import android.content.Context;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.mujaffar.lavtrade.MainApplication;
import com.mujaffar.lavtrade.utils.Appconstants;
import com.mujaffar.lavtrade.utils.SharedPrefHelper;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SheetsExample {

   public static Context context;


    public SheetsExample(Context context) {
        this.context = context;
    }

    public static void main(String args[]) throws IOException, GeneralSecurityException {
        // TODO: Assign values to desired fields of `requestBody`:
        Spreadsheet requestBody = new Spreadsheet();

        Sheets sheetsService = createSheetsService(context);
        Sheets.Spreadsheets.Create request = sheetsService.spreadsheets().create(requestBody);

        Spreadsheet response = request.execute();

        // TODO: Change code below to process the `response` object:
        System.out.println(response);
    }

    public static Sheets createSheetsService(Context context) throws IOException, GeneralSecurityException {
        //HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        HttpTransport httpTransport   = new com.google.api.client.http.javanet.NetHttpTransport();

        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        // TODO: Change placeholder below to generate authentication credentials. See
        // https://developers.google.com/sheets/quickstart/java#step_3_set_up_the_sample
        //
        // Authorize using one of the following scopes:
        //   "https://www.googleapis.com/auth/drive"
        //   "https://www.googleapis.com/auth/drive.file"
        //   "https://www.googleapis.com/auth/spreadsheets"


        GoogleCredential credential = null;
        return new Sheets.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName("Google-SheetsSample/0.1")
                .build();
    }





    public static GoogleCredential createCredentialWithAccessTokenOnly(TokenResponse tokenResponse) {
        return new GoogleCredential().setFromTokenResponse(tokenResponse);
    }
}

