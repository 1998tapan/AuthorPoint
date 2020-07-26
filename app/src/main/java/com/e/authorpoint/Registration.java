package com.e.authorpoint;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Registration extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    EditText edt_bdate,fName,lName,userAdd,email,aadharNo,pwd,confirmPwd,mobile,uname,mdlName;
    Button regButton;
    Spinner categorySpinner;
    String categoryString;
    RadioGroup genderGrp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fName = findViewById(R.id.firstName);
        mdlName = findViewById(R.id.middleName);
        lName =findViewById(R.id.lastName);
        uname = findViewById(R.id.userName);
        userAdd = findViewById(R.id.userAddress);
        email= findViewById(R.id.emailAddress);
        aadharNo = findViewById(R.id.userAadhar);
        pwd = findViewById(R.id.password);
        confirmPwd = findViewById(R.id.confirmPassword);
        mobile = findViewById(R.id.userMobile);
        edt_bdate=findViewById(R.id.userBirthDate);
        regButton=findViewById(R.id.registerButton);
        categorySpinner=findViewById(R.id.userCategorySpinner);
        genderGrp = findViewById(R.id.genderRadioGrp);


        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fNameString = fName.getText().toString();
                String mdlNameString = mdlName.getText().toString();
                String lNameString = lName.getText().toString();
                String unameString = uname.getText().toString();
                String addressString = userAdd.getText().toString();
                String birthString  = edt_bdate.getText().toString();
                String emailString = email.getText().toString();
                String aadharString = aadharNo.getText().toString();
                String pwdString = pwd.getText().toString();
                String conPwdString = confirmPwd.getText().toString();
                String mobString = mobile.getText().toString();

                int selectedGenderId = genderGrp.getCheckedRadioButtonId();
                RadioButton g = findViewById(selectedGenderId);
                String gender = g.getText().toString();



                if(!validate(fNameString,mdlNameString,lNameString,unameString,gender,addressString,birthString,emailString,aadharString,pwdString,conPwdString,mobString)){
                    return;
                }
                // check firebase reg
                int result = 1;
                if(result != -1) {

                    Toast.makeText(getApplicationContext(),"Registration Successfull !",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error inserting data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        String[] userCategories = {"Student","Employee","Others"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,userCategories);
        //categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryString = categorySpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edt_bdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                new DatePickerDialog(Registration.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                v.clearFocus();
            }
        });

    }



    private boolean validate(String fNameString, String mdlNameString, String lNameString, String unameString, String gender, String addressString, String birthString, String emailString, String aadharString, String pwdString, String conPwdString, String mobString) {
        boolean valid = true;

        if(fNameString.isEmpty()){
            valid = false;
            fName.setError("Required");
        }

        if(mdlNameString.isEmpty()){
            valid = false;
            mdlName.setError("Required");
        }

        if(lNameString.isEmpty()){
            valid = false;
            lName.setError("Required");
        }

        if(unameString.isEmpty()){
            valid = false;
            uname.setError("Required");
        }

        if(gender.isEmpty()){
            valid = false;
            Toast.makeText(getApplicationContext(),"Gender required",Toast.LENGTH_SHORT).show();
            //genderGrp.setError("Required");
        }

        if(addressString.isEmpty()){
            valid = false;
            userAdd.setError("Required");
        }

        if(birthString.isEmpty()){
            valid = false;
            edt_bdate.setError("Required");
        }

        if(emailString.isEmpty()){
            valid = false;
            email.setError("Required");
        }

        if(aadharString.isEmpty()){
            valid = false;
            aadharNo.setError("Required");
        }

        if(pwdString.isEmpty()){
            valid = false;
            pwd.setError("Required");
        }

        if(conPwdString.isEmpty()){
            valid = false;
            confirmPwd.setError("Required");
        }

        if(pwdString.length() < 8 || conPwdString.length() < 8){
            valid = false;
            pwd.setError("Password must be more than 8 characters");
            confirmPwd.setError("Password must be more than 8 characters");
        }

        if(!pwdString.equals(conPwdString)){
            valid = false;
            pwd.setError("Password must be equal to Confirm password");
            confirmPwd.setError("Password must be equal to Confirm password");
        }

        if(mobString.isEmpty()){
            valid = false;
            mobile.setError("Required");
        }

        if(mobString.length() < 10){
            valid = false;
            mobile.setError("Mobile must be 10 digits long");
        }

        return  valid;
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edt_bdate.setText(sdf.format(myCalendar.getTime()));
    }
}