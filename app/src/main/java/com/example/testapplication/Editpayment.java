package com.example.testapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapplication.db.category.Category;
import com.example.testapplication.db.category.ICategory;
import com.example.testapplication.db.vendor.Vendor_pay_Impl;

public class Editpayment extends AppCompatActivity {

    RadioGroup rdpStatus;
    RadioButton rdPaid,rdPending;
    private String has_title="Add Payment";// name="null",desc="null",amt="0.00",paid="0.00",balance="0.00",category=null;
    private int id = 0,eid=0,vid=0;


    private class VendorLayoutClass {
        private Context c;
        private int vid = 0;

        public VendorLayoutClass(Context c, int vid) {
            vendor = new Vendor_pay_Impl(c);
            this.c = c;
            this.vid = vid;

        }

        public void InitVariables() {
            rdpStatus = (RadioGroup) findViewById(R.id.rdpStatus);
            rdPaid = (RadioButton) findViewById(R.id.rdpaid);
            rdPending = (RadioButton) findViewById(R.id.rdPending);

        }

        public Vendor_pay_Impl vendor;

        public void loadValuesFromLayout() {
            this.vendor.name = ((EditText) findViewById(R.id.name1)).getText().toString();
            // this.vendor.category = ((Spinner)findViewById(R.id.category_v)).getSelectedItem().toString();
            this.vendor.amount = ((EditText) findViewById(R.id.amount1)).getText().toString();
            this.vendor.note = ((EditText) findViewById(R.id.note)).getText().toString();
            //  vendor.addVendor();
        }

        public void setValuesToLayout(int id) {
            this.vendor = vendor.getVendorPaybyid(id, vid, eid);
            ICategory category = new Category(c);
            ((EditText) findViewById(R.id.name1)).setText(this.vendor.name, TextView.BufferType.EDITABLE);
            // ((EditText)findViewById(R.id.category_v)).setText(this.vendor.category, TextView.BufferType.EDITABLE);
            ((EditText) findViewById(R.id.amount1)).setText(this.vendor.amount, TextView.BufferType.EDITABLE);
            ((EditText) findViewById(R.id.note)).setText(this.vendor.note, TextView.BufferType.EDITABLE);

          /*  String[] defaultOrder = (String[]) category.getAllCategory().toArray(new String[0]);
            for(int i=0;i<defaultOrder.length;i++){
                if(defaultOrder[i].equals(category)){ //from bundle
                    //Log.d("AddBudgetAct>>","setting default category as " + category);
                    String temp = defaultOrder[i];
                    defaultOrder[i] = defaultOrder[0];
                    defaultOrder[0] = temp;
                    break; //for loop
                }
            }
            final String[] defaultCat = defaultOrder;
            Spinner cat = findViewById(R.id.category_v); //spinner
            ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(c,android.R.layout.simple_spinner_item,defaultCat);
            catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cat.setAdapter(catAdapter);
            if(category!=null){
                //set category
            }*/
            if (vendor.status != null) {
                if (vendor.status.equalsIgnoreCase("paid")) {

                    rdPaid.setChecked(true);
                } else if (vendor.status.equalsIgnoreCase("pending")) {
                    rdPending.setChecked(true);
                }
            }
        }

        public void setRadioEvents() {
            rdpStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // find which radio button is selected
                    if (checkedId == R.id.rdpaid) {
                        vendor.status = "paid";
                    } else if (checkedId == R.id.rdPending) {
                        vendor.status = "pending";
                    }
                }
            });
        }
    }

    VendorLayoutClass vendorlayout;
    Button closeButton;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpayment);

        final Context context = this;
        Bundle b = getIntent().getExtras();
        if(b!=null) {
            has_title = b.getString("title", "Add Budget");
            /*name=b.getString("name","Enter name");
            desc=b.getString("desc","Enter description");
            amt=b.getString("amount","0.00");
            category = b.getString("cat",null);
            balance=b.getString("balance","0.00");*/
            id = b.getInt("id");
            vid = b.getInt("vid");
            Log.d("Addpayactivity>>", "id -> " + id + " vid ->" + vid);

        }
        //Added
        Log.d("Edit Payment", "id = " + id);
        Toolbar toolbar = findViewById(R.id.toolbar2); //set toolbar
        setSupportActionBar(toolbar);
        //set toolbar title
        toolbar.setTitle("Edit Payment");//either default or from bundle
        //for back button
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //default previous intent
                Intent i = new Intent(getApplicationContext(),Vendorview.class);
                startActivity(i);
            }
        });

        ((Button)findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vendorlayout.vendor.removePayment(id);
                Intent i=new Intent(getApplicationContext(),Vendorview.class);
                startActivity(i);
            }
        });

        vendorlayout = new VendorLayoutClass(this, vid);
        vendorlayout.InitVariables();
        vendorlayout.setValuesToLayout(id);
        vendorlayout.setRadioEvents();
        closeButton = (Button) findViewById(R.id.button);
        builder = new AlertDialog.Builder(this);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vendorlayout.loadValuesFromLayout();
                vendorlayout.vendor.updatePayment(vendorlayout.vendor);
                Intent i=new Intent(getApplicationContext(),Vendorview.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//clear stack
                startActivity(i);
                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to save the save the changes and exit ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("AlertDialogExample");
                alert.show();
            }
        });
    }
}

