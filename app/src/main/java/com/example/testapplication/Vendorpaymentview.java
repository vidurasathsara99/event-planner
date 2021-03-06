package com.example.testapplication;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.adapter.VendorPaymentAdapter;
import com.example.testapplication.db.budget.Budget_Impl;
import com.example.testapplication.db.budget.Ibudget;

public class Vendorpaymentview extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendorpayview);

        Toolbar toolbar = findViewById(R.id.abbb_menu);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Intent i = new Intent(getApplicationContext(),GraphActivity.class);
                //  startActivity(i);
            }
        });
        Bundle b=getIntent().getExtras();
        int id=b.getInt("id",0);
        int eid=b.getInt("eid",0);
        Ibudget budget = new Budget_Impl(this);//Budget Interface

        VendorPaymentAdapter adapter = new VendorPaymentAdapter(this,id,eid); //Budget Adapter
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_ev);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));//item to item decoration
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    /**
     * Helper method which will generate a basic ArrayList of 100 items
     *
     * @return A List of SimpleViewModels

    private List<SimpleViewModel> generateSimpleList() {
    List<SimpleViewModel> simpleViewModelList = new ArrayList<>();

    for (int i = 0; i < 20; i++) {
    simpleViewModelList.add(new SimpleViewModel(String.format(Locale.US, "", i)));
    }

    return simpleViewModelList;
    }
     */

        /*
        ((Button)findViewById(R.id.lb_btn)).setText("Julia");
        ((TextView)findViewById(R.id.lb_desc)).setText("B:200000.00\nP:0.00");
        //((TextView)findViewById(R.id.lb_desc)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.lb_desc)).setTextColor(getResources().getColor(R.color.black));

         */

    public void handleOnClick(View v){
        if(v.getId()==R.id.lv_add_vendor){
            Intent i = new Intent(getApplicationContext(),Addvendor.class);
            startActivity(i);
        }
        /*
        if(v.getId()==R.id.lb_add_budget){
            Intent i = new Intent(getApplicationContext(),AddBudgetActivity.class);
            startActivity(i);
        }
        if(v.getId()==R.id.lb_btn){
            Intent i = new Intent(getApplicationContext(),AddBudgetActivity.class);
            Bundle b = new Bundle();
            b.putString("title","Edit Budget");
            b.putString("name","Julia");
            b.putString("amount","200000.00");
            b.putString("balance","200000.00");
            b.putString("paid","0.00");
            i.putExtras(b);
            startActivity(i);
        }

         */
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_settings){
            //Settings btn
            Intent i = new Intent(getApplicationContext(), AppSettingsActivity.class);
            Bundle b = new Bundle();
            b.putString("pre_activity","listBudget");
            i.putExtras(b);
            startActivity(i);
            //finish();//check
        }
        /*
        if(item.getItemId()==R.id.action_settings){
            //About us page
        }

         */
        return super.onOptionsItemSelected(item);
    }


}

