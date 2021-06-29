package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Food extends AppCompatActivity {

    private Toolbar toolbar;
    private String user;
    private ProgressDialog pDialog;
    private SearchView sv;
    private MenuItem searchBar;

    private static final String appKey = "7f277e1bb0cb1d3b0b756e3cf375365c";
    private static final String appId = "7da7c17c";
    private String baseURL = "https://api.nutritionix.com/v1_1/search/";
    private String baseURL2 = "?fields=item_name%2Citem_id%2Cbrand_name%2Cnf_calories%2Cnf_total_fat&appId=" +
            appId + "&appKey=" + appKey;
    private String query;

    ArrayList<String> foodItems;
    ArrayList<String> brandNames;
    ArrayList<String> nf_calories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        query = "";

        foodItems = new ArrayList<>();
        brandNames = new ArrayList<>();
        nf_calories = new ArrayList<>();
        pDialog = new ProgressDialog(Food.this);

        toolbar = findViewById(R.id.food_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user = getIntent().getStringExtra("displayName");

        new GetFood().execute();
    }

    private class GetFood extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog.setMessage("Searching...");
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();

            String jsonStr = sh.makeServiceCall(baseURL + query + baseURL2);

            if(jsonStr != null)
                try{
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray hits = jsonObject.getJSONArray("hits");
                    foodItems.clear();
                    brandNames.clear();
                    nf_calories.clear();
                    for(int i = 0; i < hits.length(); i++){
                        JSONObject d = hits.getJSONObject(i);
                        JSONObject fields = d.getJSONObject("fields");
                        String itemName = fields.getString("item_name");
                        String brandName = fields.getString("brand_name");
                        String calories = fields.getString("nf_calories");

                        foodItems.add(itemName);
                        brandNames.add(brandName);
                        nf_calories.add(calories);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            if(pDialog.isShowing()) {
                pDialog.dismiss();
            }

            RecyclerView rv = findViewById(R.id.food_list);
            LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
            rv.setLayoutManager(llm);
            rv.setAdapter(new SimpleItemRecyclerViewAdapter(foodItems, brandNames, nf_calories));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        menu.findItem(R.id.action_settings).setVisible(false);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        sv = (SearchView) searchItem.getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String s) {
                String userSearchParse = s.replace(" ", "%20");
                query = userSearchParse;
                new GetFood().execute();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                Intent home = new Intent(getApplicationContext(), Navigation.class);
                home.putExtra("displayName", user);
                finish();
                startActivity(home);
                break;
        }
        return true;
    }

    //Recyclerview adapter class
    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter <SimpleItemRecyclerViewAdapter.ViewHolder> {
        private ArrayList<String> foodItems;
        private ArrayList<String> brandNames;
        private ArrayList<String> nf_calories;

        SimpleItemRecyclerViewAdapter(ArrayList<String> foodItems, ArrayList<String> brandNames, ArrayList<String> nf_calories) {
            this.foodItems = foodItems;
            this.brandNames = brandNames;
            this.nf_calories = nf_calories;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout,
                    parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.foodName.setText(foodItems.get(position));
            holder.brandView.setText("Brand: " + brandNames.get(position));
            holder.calorieView.setText("Calories: " + nf_calories.get(position));


        }

        @Override
        public int getItemCount() {
            return foodItems.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            View mView;
            TextView foodName;
            TextView brandView;
            TextView calorieView;

            public ViewHolder(View itemView) {
                super(itemView);
                mView = itemView;
                foodName = itemView.findViewById(R.id.item_name);
                brandView = itemView.findViewById(R.id.brand_name);
                calorieView = itemView.findViewById(R.id.nf_calories);

            }
        }
    }
}