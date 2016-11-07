package com.example.recon1.phoneinput;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class MainActivity extends AppCompatActivity {

    final String TAG = "phoneAPP ***";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView idTv = (TextView) findViewById(R.id.c_name_tv);  //Country Id View
        TextView codeTv = (TextView) findViewById(R.id.c_code_tv);  // Country code View
        final EditText phoneInput = (EditText) findViewById(R.id.phoneInput);

        final String countryID = GetCountryID();
        final String countryZipCode = GetCountryZipCode();

        idTv.setText(countryID);
        codeTv.setText(countryZipCode);


        final  Button verifyBtn = (Button) findViewById(R.id.verifyNumBtn);
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                PhoneNumberUtils phoneUtil;
//                String phnNum = PhoneNumberUtils.formatNumber(phoneInput.getText().toString());
                PhoneNumberUtil pnu = PhoneNumberUtil.getInstance();
                try {
                    Phonenumber.PhoneNumber pn = pnu.parse(phoneInput.getText().toString(), GetCountryID());
                    if  (!pnu.isValidNumber(pn)) {

                        Toast toast = Toast.makeText(getApplicationContext(), "Please type a valid phone number", Toast.LENGTH_LONG);
                        toast.show();

                    }else {
                        String phnNum = pnu.format(pn, PhoneNumberUtil.PhoneNumberFormat.E164);
                        Toast toast = Toast.makeText(getApplicationContext() ,phnNum ,Toast.LENGTH_LONG);
                        toast.show();
                    }
                } catch (NumberParseException e) {
                    e.printStackTrace();
                }
            }
        });


    }


    public String GetCountryID(){
        String CountryID="";
        String CountryZipCode="";

        TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID= manager.getSimCountryIso().toUpperCase();
        return CountryID;
    }


    public String GetCountryZipCode(){
        String CountryID="";
        String CountryZipCode="";

        TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID= manager.getSimCountryIso().toUpperCase();
        String[] rl=this.getResources().getStringArray(R.array.CountryCodes);
        for(int i=0;i<rl.length;i++){
            String[] g=rl[i].split(",");
            if(g[1].trim().equals(CountryID.trim())){
                CountryZipCode=g[0];
                break;
            }
        }
        return CountryZipCode;
    }


}
