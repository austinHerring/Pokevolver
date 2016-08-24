package com.austin.pokevolver;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.austin.pokevolver.Adapters.VariableAdapter;
import com.austin.pokevolver.Listeners.PokemonQueryListener;
import com.austin.pokevolver.Models.DataBase;
import com.austin.pokevolver.Models.EquationModel;

public class MainActivity extends AppCompatActivity{
    private EquationModel model;
    private CheckBox checkboxTransferOption;
    private TextView evolutionsText, experienceText;
    public FloatingActionButton buttonCalculate;
    public boolean inSuggestMode;
    public ListView listOfSuggestions, listOfVariables;
    public Activity activity;
    public Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = new EquationModel();
        DataBase.db = new DatabaseHelper(this);
        inSuggestMode = false;
        listOfSuggestions = (ListView) findViewById(R.id.suggestions);
        listOfVariables = (ListView) findViewById(R.id.variables);
        buttonCalculate = (FloatingActionButton) findViewById(R.id.buttonCalculate);
        checkboxTransferOption = (CheckBox) findViewById(R.id.checkbox_transfer_option);
        evolutionsText = (TextView) findViewById(R.id.total_evolutions);
        experienceText = (TextView) findViewById(R.id.total_exp);
        activity = this;
        setUpListeners();

        VariableAdapter variableAdapter = new VariableAdapter(this, R.layout.layout_variable_row, EquationModel.variables);
        listOfVariables.setAdapter(variableAdapter);

        //TODO improve banner UI
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        this.menu = menu;
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new PokemonQueryListener(this));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void setUpListeners() {
        checkboxTransferOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EquationModel.setTransferOption(((CheckBox) v).isChecked());
            }
        });

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EquationModel.evaluate();
                evolutionsText.setText(EquationModel.evolutions);
                experienceText.setText(EquationModel.exp);
            }
        });
    }
}
