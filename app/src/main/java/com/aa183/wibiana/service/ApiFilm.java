package com.aa183.wibiana.service;


import com.aa183.wibiana.model.ResponseData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiFilm {

    @FormUrlEncoded
    @POST("insert.php")
    Call<ResponseData> addData(
            @Field("judul") String judul,
            @Field("tanggal") String tanggal,
            @Field("gambar") String gambar,
            @Field("sutradara") String sutradara,
            @Field("penulis") String penulis,
            @Field("sinopsis") String sinopsis,
            @Field("link") String link
    );

    @FormUrlEncoded
    @POST("update.php")
    Call<ResponseData> editData(
            @Field("id") String id,
            @Field("judul") String judul,
            @Field("tanggal") String tanggal,
            @Field("gambar") String gambar,
            @Field("sutradara") String sutradara,
            @Field("penulis") String penulis,
            @Field("sinopsis") String sinopsis,
            @Field("link") String link
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponseData> deleteData(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("search.php")
    Call<ResponseData> searchData(
            @Field("search") String keyword
    );

    @GET("getdata.php")
    Call<ResponseData> getData();
}