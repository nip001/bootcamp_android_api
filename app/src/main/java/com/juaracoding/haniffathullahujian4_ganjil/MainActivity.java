package com.juaracoding.haniffathullahujian4_ganjil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.juaracoding.haniffathullahujian4_ganjil.Adapter.GetDataAdapter;
import com.juaracoding.haniffathullahujian4_ganjil.model.TampilData.Data;
import com.juaracoding.haniffathullahujian4_ganjil.model.TampilData.GetAllData;
import com.juaracoding.haniffathullahujian4_ganjil.model.TampilData.Product;
import com.juaracoding.haniffathullahujian4_ganjil.service.APIClient;
import com.juaracoding.haniffathullahujian4_ganjil.service.APIInterfacesRest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button bt;
    RecyclerView rc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rc = findViewById(R.id.rc);
        bt=findViewById(R.id.button);
        callDaftarFilm();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Tambah.class);
                startActivity(intent);
             }
        });
   /*     txtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Toast.makeText(MainActivity.this,txtSearch.getText().toString(),Toast.LENGTH_SHORT).show();
             //
                //   callDaftarFilm(txtSearch.getText().toString());
                return false;
            }
        });*/

    }

    APIInterfacesRest apiInterface;
    ProgressDialog progressDialog;

    public void callDaftarFilm() {

        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();
        Call<GetAllData> call3 = apiInterface.getAllData();
        call3.enqueue(new Callback<GetAllData>() {
            @Override
            public void onResponse(Call<GetAllData> call, Response<GetAllData> response) {
                progressDialog.dismiss();
                GetAllData dataFilm = response.body();
                //Toast.makeText(LoginActivity.this,userList.getToken().toString(),Toast.LENGTH_LONG).show();
                if (dataFilm != null ) {

                    loadData(dataFilm.getData().getProduct());

                } else {

                    Toast.makeText(MainActivity.this, "Film tidak ditemukan", Toast.LENGTH_LONG).show();
                  /*  try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(MainActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }*/
                }

            }

            @Override
            public void onFailure(Call<GetAllData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Maaf koneksi bermasalah", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });


    }

    public void loadData(List<Product> searchs){

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rc.setLayoutManager(mLayoutManager);
        rc.setItemAnimator(new DefaultItemAnimator());
        GetDataAdapter fa = new GetDataAdapter(searchs);
        rc.setAdapter(fa);
    }
}
