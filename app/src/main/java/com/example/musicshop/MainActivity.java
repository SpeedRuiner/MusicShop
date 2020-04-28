package com.example.musicshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int quantity = 0;
    Spinner spinner;
    ArrayList spinnerArrayList;
    ArrayAdapter spinnerAdapter;
    HashMap goodsMap;
    String goodsName;
    double price;
    EditText userNameOrderEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameOrderEditText = findViewById(R.id.nameEditText);

        createSpinner();

        createMap();

    }

    void createSpinner(){
        spinner = findViewById(R.id.spinnerSelectItem);
        spinnerArrayList = new ArrayList();
        spinner.setOnItemSelectedListener(this);

        spinnerArrayList.add("Nexus");
        spinnerArrayList.add("Redmi Pro 10");
        spinnerArrayList.add("Samsung");

        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerArrayList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

    }

    void createMap(){
        goodsMap = new HashMap();
        goodsMap.put("Nexus", 450.0);
        goodsMap.put("Redmi Pro 10", 1050.0);
        goodsMap.put("Samsung", 1400.0);
    }

    public void increaseQuantity(View view) {
        quantity+= 1;
        TextView quantityTextView = findViewById(R.id.priceTextView2);
        quantityTextView.setText("" + quantity);
        TextView priceTextView = findViewById(R.id.priceTextView);
        priceTextView.setText("" + quantity * price);
    }

    public void decreaseQuantity(View view) {
        quantity-= 1;
        if (quantity < 0){
            quantity = 0;
        }
        TextView quantityTextView = findViewById(R.id.priceTextView2);
        quantityTextView.setText("" + quantity);
        TextView priceTextView = findViewById(R.id.priceTextView);
        priceTextView.setText("" + quantity * price);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        goodsName = spinner.getSelectedItem().toString();
        price = (double) goodsMap.get(goodsName);
        TextView priceTextView = findViewById(R.id.priceTextView);
        priceTextView.setText("" + quantity * price);

        ImageView goodsImageView = findViewById(R.id.goodsImageView);

       switch (goodsName){
           case "Nexus":
               goodsImageView.setImageResource(R.drawable.nexus);
               break;
           case "Redmi Pro 10":
               goodsImageView.setImageResource(R.drawable.xiaomi_pro_10);
               break;
           case "Samsung":
               goodsImageView.setImageResource(R.drawable.samsung);
               break;
           default:
               goodsImageView.setImageResource(R.drawable.samsung);
           break;
       }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addToCart(View view) {

        Order order = new Order();

        order.userNameOrder = userNameOrderEditText.getText().toString();
        order.goodsNameOrder = goodsName;
        order.quantityOrder = quantity;
        order.orderPrice = quantity * price;
        order.price = price;

        Intent orderIntent = new Intent(MainActivity.this, OrderActivity.class);
        orderIntent.putExtra("userNameOrderForIntent", order.userNameOrder);
        orderIntent.putExtra("goodsNameOrderForIntent", order.goodsNameOrder);
        orderIntent.putExtra("quantityOrderForIntent", order.quantityOrder);
        orderIntent.putExtra("orderPriceForIntent", order.orderPrice);
        orderIntent.putExtra("priceForIntent", order.price);
        startActivity(orderIntent);
    }
}
