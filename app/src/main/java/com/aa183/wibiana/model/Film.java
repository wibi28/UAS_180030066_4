package com.aa183.wibiana.model;

import java.util.Date;

public class Film {
    private int idFilm;
    private String judul;
    private Date tanggal;
    private String gambar;
    private String sutradara;
    private String penulis;
    private String Sinopsis;
    private String link;

    public Film(int idFilm, String judul, Date tanggal, String gambar, String sutradara, String penulis, String sinopsis, String link) {
        this.idFilm = idFilm;
        this.judul = judul;
        this.tanggal = tanggal;
        this.gambar = gambar;
        this.sutradara = sutradara;
        this.penulis = penulis;
        this.Sinopsis = sinopsis;
        this.link = link;
    }


    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getSutradara() {
        return sutradara;
    }

    public void setSutradara(String sutradara) { this.sutradara = sutradara;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getSinopsis() { return Sinopsis;
    }

    public void setSinopsis(String Sinopsis) {
        this.Sinopsis = Sinopsis;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
