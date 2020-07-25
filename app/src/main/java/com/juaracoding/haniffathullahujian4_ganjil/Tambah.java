package com.juaracoding.haniffathullahujian4_ganjil;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.juaracoding.haniffathullahujian4_ganjil.model.GetData;
import com.juaracoding.haniffathullahujian4_ganjil.model.PostData;
import com.juaracoding.haniffathullahujian4_ganjil.model.Variant;
import com.juaracoding.haniffathullahujian4_ganjil.service.APIClient;
import com.juaracoding.haniffathullahujian4_ganjil.service.APIInterfacesRest;
import com.juaracoding.haniffathullahujian4_ganjil.service.AppUtil;

import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tambah extends AppCompatActivity {
    List<String> listVariant;
    Spinner spnVariant;
    ImageView gambar;
    File file1;
    Button btnGallery,btnCamera,btnSend;
    EditText product,price,desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        spnVariant=findViewById(R.id.spnVariant);
        gambar=findViewById(R.id.imageView2);
        product=findViewById(R.id.editProduct);
        price=findViewById(R.id.editPrice);
        desc=findViewById(R.id.editDesc);
        btnCamera=findViewById(R.id.btnCamera);
        btnGallery=findViewById(R.id.btnGallery);
        btnSend=findViewById(R.id.btnSend);
//        getData();
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(Tambah.this)
                        .cameraOnly()
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(Tambah.this)
                        .galleryOnly()
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataWithImage();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK ) {
                gambar.setImageURI(data.getData());
                file1 = ImagePicker.Companion.getFile(data);
        }
    }

    APIInterfacesRest apiInterface;
    ProgressDialog progressDialog;
    public void getData(){

        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        progressDialog = new ProgressDialog(Tambah.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();
        Call<GetData> call3 = apiInterface.getData();
        call3.enqueue(new Callback<GetData>() {
            @Override
            public void onResponse(Call<GetData> call, Response<GetData> response) {
                progressDialog.dismiss();
                GetData data = response.body();
                //Toast.makeText(LoginActivity.this,userList.getToken().toString(),Toast.LENGTH_LONG).show();
                if (data !=null) {
                    for(int i = 0; i < data.getData().getVariant().size(); i++){
                        listVariant.add(data.getData().getVariant().get(i).getVariant());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Tambah.this,
                            android.R.layout.simple_spinner_item, listVariant);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnVariant.setAdapter(adapter);


                }else{

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(Tambah.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(Tambah.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<GetData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Maaf koneksi bermasalah",Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });




    }
    //send post data with image
    private void sendDataWithImage() {


        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        progressDialog = new ProgressDialog(Tambah.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();



        byte[] bImg1 = AppUtil.FiletoByteArray(file1);
        RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"),bImg1);
        //  RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"), compressCapture(bImg1, 900));
        MultipartBody.Part bodyImg1 =
                MultipartBody.Part.createFormData("image", file1.getName() + ".jpg", requestFile1);

        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);

//"Pocari","10","Drama Horror","Hanif","Desta","XXX","2"
        Call<PostData> postAdd = apiInterface.sendDataWithImage(

                toRequestBody(product.getText().toString()),
                toRequestBody(price.getText().toString()),
                toRequestBody(desc.getText().toString()),
                toRequestBody(spnVariant.getSelectedItem().toString()),
                bodyImg1);
        postAdd.enqueue(new Callback<PostData>() {
            @Override
            public void onResponse(Call<PostData> call, Response<PostData> response) {

                progressDialog.dismiss();

                PostData responServer = response.body();

                if (responServer != null) {

                    Toast.makeText(Tambah.this,responServer.getMessage(),Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<PostData> call, Throwable t) {

                progressDialog.show();
                Toast.makeText(Tambah.this, "Maaf koneksi bermasalah", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });

    }


    //change string to requestbody
    public RequestBody toRequestBody(String value) {
        if (value == null) {
            value = "";
        }
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }
}
