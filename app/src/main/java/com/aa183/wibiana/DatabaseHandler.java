package com.aa183.wibiana;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.aa183.wibiana.model.Film;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_film";
    private final static String TABLE_FILM = "t_film";
    private final static String KEY_ID_FILM = "ID_Film";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_TGL = "Tanggal";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_SUTRADARA = "Sutradara";
    private final static String KEY_PENULIS = "Penulis";
    private final static String KEY_SINOPSIS = "Sinopsis";
    private final static String KEY_LINK = "Link";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());
    private Context context;

    public DatabaseHandler (Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FILM = "CREATE TABLE " + TABLE_FILM
                + "(" + KEY_ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_TGL + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_SUTRADARA + " TEXT, "
                + KEY_PENULIS + " TEXT, " + KEY_SINOPSIS + " TEXT, "
                + KEY_LINK + " TEXT);";

        db.execSQL(CREATE_TABLE_FILM);
        inialisasiFilmAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_FILM;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahFilm (Film dataFilm){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_SUTRADARA, dataFilm.getSutradara());
        cv.put(KEY_PENULIS, dataFilm.getPenulis());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        cv.put(KEY_LINK, dataFilm.getLink());

        db.insert(TABLE_FILM, null, cv);
        db.close();
    }

    public void tambahFilm (Film dataFilm, SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_SUTRADARA, dataFilm.getSutradara());
        cv.put(KEY_PENULIS, dataFilm.getPenulis());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        cv.put(KEY_LINK, dataFilm.getLink());

        db.insert(TABLE_FILM, null, cv);
    }

    public void editFilm (Film dataFilm){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_SUTRADARA, dataFilm.getSutradara());
        cv.put(KEY_PENULIS, dataFilm.getPenulis());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        cv.put(KEY_LINK, dataFilm.getLink());

        db.update(TABLE_FILM, cv, KEY_ID_FILM + "=?", new String[]{String.valueOf(dataFilm.getIdFilm())});
        db.close();
    }

    public void hapusFilm (int idFilm){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FILM, KEY_ID_FILM + "=?", new String[]{String.valueOf(idFilm)});
        db.close();
    }

    public ArrayList<Film> getAllFilm(){
        ArrayList<Film> dataFilm = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FILM;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr =  db.rawQuery(query, null);
        if (csr.moveToFirst()){
            do {
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(2));
                } catch (ParseException er){
                    er.printStackTrace();
                }

                Film tempFilm = new Film(
                        csr.getInt ( 0),
                        csr.getString( 1),
                        tempDate,
                        csr.getString( 3),
                        csr.getString( 4),
                        csr.getString( 5),
                        csr.getString( 6),
                        csr.getString( 7)
                );

                dataFilm.add(tempFilm);
            } while (csr.moveToNext());
        }
        return dataFilm;
    }

    private String storeImagesFiles(int id){
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inialisasiFilmAwal(SQLiteDatabase db){
        int idFilm = 0;
        Date tempDate = new Date();

        try {
            tempDate = sdFormat.parse("21/05/2015 00:00");
        }catch (ParseException er){
            er.printStackTrace();
        }



        Film film1 = new Film(
                idFilm,
                "San Andreas",
                tempDate,
                storeImagesFiles(R.drawable.film1),
                "Brad Peyton",
                "Carlton Cuse (screenplay), Andre Fabrizio (story)",
                " San Andreas sendiri merupakan nama sebuah patahan atau tanah retak di California Amerika Serikat yang memiliki panjang 1200 km yang memisahkan lempeng Pasifik dan lempeng Amerika Utara. Hal ini mengakibatkan gempa bumi yang terjadi pada 18 April 1906 jam 5:12 pagi di San Fransisco. Sejumlah 28.000 bangunan hancur berantakan dan 3000 orang tewas dalam kejadian ini.\n" +
                        "\n" +
                        "Kisah dalam film ini tentunya ingin menjadikan gempa bumi jilid kedua di era modern ini. Ray (Dwayne Johnson) sedang mengalami perceraian dengan istrinya yang bernama Emma (Carla Gugino). Hal ini disebabkan karena hubungan yang kurang baik yang timbul setelah kematian anak perempuannya nomor dua. Penyebab kematian itu karena tenggelam dimana Ray tidak dapat menyelamatkannya padahal Ray adalah seorang pekerja penyelamat kebakaran dan SAR. Selanjutnya Emma menjalin hubungan dengan seorang kaya raya yang sedang membangun gedung tertinggi bernama Riddick (Ioan Gruffudd). Blake (Alexandra Daddario) adalah anak nomor satu yang akan ikut tinggal dirumah Riddick menjadi tema sentral kisah ini.\n" +
                        "\n" +
                        "Adegan dibuka dengan kecelakaan mobil yang dikemudikan oleh seorang gadis akibat bertelepon ria. Mobil terguling-guling dan masuk ke jurang dan akhirnya nyangkut dibebatuan. Visualisasi digambarkan dengan sangat menarik dan patut diacungi jempol. Kamera dipasang di dalam mobil pas dihadapan wajah pengemudi sehingga saat mobil terbalik terlihat jelas guncangan dan raut wajah serta uraian rambutnya. Seolah-olah penonton mengalami juga peristiwa tersebut. Ray dan kawan-kawan datang menggunakan helikopter untuk menyelamatkan gadis tersebut. Tidak mudah untuk menyelamatkannya karena lokasinya yang sempit di antara celah-celah bukit dan kondisi mobil yang nyaris jatuh kebawah jurang. Bahkan salah seorang tim terjepit kakinya sehingga makin sulit untuk proses penyelamatan. Pada akhirnya Ray berhasil menyelamatkan mereka walaupun taruhannya helikopternya ikut jatuh.\n" +
                        "\n" +
                        "Seorang ahli gempa bumi bernama Lawrence (Paul Giamatti) bersama tim meneliti tentang apakah gempa bumi bisa diprediksi. Pada akhirnya penelitian tersebut berhasil dan gempa bumi bisa diprediksi kapan akan terjadi. Sayangnya gempa terjadi saat itu juga di sebuah bendungan dan menewaskan temannya yang bernama Kim Park.\n" +
                        "\n" +
                        "Ray mendapat tugas untuk membantu dalam menyelamatkan korban gempa bumi. Emma pergi makan siang di restoran yang berada di atas gedung tinggi. Di saat yang bersamaan Blake dan Riddick sedang pergi ke San Fransisco. Blake menunggu di loby dan Riddick masuk ke dalam kantor. Blake berkenalan dengan Ben (Hugo Johnstone) yang sedang menunggu juga untuk wawancara kerja didampingi dengan Ollie (Art Parkinson), adiknya.\n" +
                        "\n" +
                        "Ketika Blake dan Riddick hendak pulang tiba-tiba terjadi gempa bumi. Mereka berada dalam mobil di lantai basement mencoba untuk menerobos keluar tapi sayangnya mobil mereka tertimpa beton. Sopir tewas, Blake terjepit kakinya dan Riddick berhasil meloloskan diri dari mobil. Selanjutnya Riddick meminta bantuan ke satpam dan pergi meninggalkan gedung. Ben dan Ollie yang mendengar pembicaraan Riddick segera pergi menolong Blake dan berhasil.\n" +
                        "\n" +
                        "Ray menerima telepon dari Emma yang meminta tolong dan segera mendatanginya dengan helikopternya. Untunglah di saat yang kritis Emma berhasil di tolong oleh Ray. Adegan gempa bumi yang menimpa gedung-gedung tinggi ditampilkan dengan bagus. Gedung-gedung yang runtuh, getaran-getaran yang terjadi dan bongkahan-bongkahan beton digambarkan dengan realistis dan alami. Spesial efeknya patut diacungi jempol.\n" +
                        "\n" +
                        "Selanjutnya Blake menelpon ayahnya dan memberitahu keberadaannya. Segera Ray dan istrinya pergi ke San Fransisco tetapi ditengah jalan helikopternya rusak dan mendarat darurat. Terpaksa Ray mencuri mobil untuk melanjutkan perjalanannya. Ditengah jalan mereka terhambat oleh lubang retakan yang terjadi akibat gempa bumi. Atas bantuan sepasang suami istri tua maka mereka barter mobil dengan menunjukkan tempat pelatihan pesawat kecil. Mereka melanjutkan perjalanan dengan pesawat tersebut kemudian berganti lagi dengan kapal boat.\n" +
                        "\n" +
                        "Lawrence melalui siaran TV terbatas berhasil menyiarkan prediksinya akan terjadi gelombang Tsunami. Blake, Ben dan Ollie mendengar berita itu dan pergi menuju ke tempat yang tinggi namun tempat tersebut sudah hancur dan terbakar . Mereka memutuskan pergi ke gedung milik Riddick yang belum jadi karena gedung itu tertinggi. Sementara itu Riddick tewas oleh terjangan Tsunami yang menyeret kapal besar. Ray dan istrinya harus menantang arus gelombang Tsunami agar tidak ikut terseret. Dengan bersusah payah akhirnya mereka berhasil lolos.\n" +
                        "\n" +
                        "Ray dan istrinya mencari Blake kesana kemari dan akhirnya menemukan mereka karena sinar laser yang digunakan oleh Blake. Namun gedung yang ditempati semakin runtuh untuk itu Ben dan Ollie berhasil naik ke lantai atas sedangkan Blake terjebak tidak bisa naik karena pintunya tidak bisa dibuka. Ray berusaha menolong tapi terlambat karena Blake sudah lemas kehabisan napas. Posisi gedung semakin turun sehingga Emma memutuskan untuk menjemput mereka dengan menabrakkan kapal boatnya. Mereka semua berhasil keluar dari gedung tersebut namun Ray tidak berhasil menyelamatkan Blake walaupun sudah memberikan pertolongan pertama. Semua sedih dan menangis. Tiba-tiba Blake bangkit dari tidurnya yang mengagetkan semua orang dan sekaligus menggembirakan semuanya.\n" +
                        "\n" +
                        "Permainan Dwayne Johnson cukup bagus dan mampu menghayati karakternya. Akting Carla Gugino juga lumayan sebagai seorang istri yang hendak bercerai tetapi rujuk kembali. Penampilan Alexandra Daddario, Hugo Johnstone dan Art Parkinson juga lumayan sebagai seorang remaja menghadapi bencana.\n" +
                        "\n" +
                        "Ada beberapa hal yang agak membingungkan dalam film ini. Misalnya mengenai tempat berlindung dibawah meja ketika terjadi gempa. Bukankah hal itu adalah cara lama yang sudah diralat dan dilarang untuk dilakukan. Begitu juga dengan menyelamatkan diri menuju atas atap gedung, bukankah seharusnya menuju kebawah dan keluar gedung",
                "https://terbit21.cool/san-andreas-2015/"
        );

        tambahFilm(film1, db);
        idFilm++;

        try {
            tempDate = sdFormat.parse("02/06/2013 00:00");
        }catch (ParseException er){
            er.printStackTrace();
        }

        Film film2 = new Film(
                idFilm,
                "World War Z",
                tempDate,
                storeImagesFiles(R.drawable.film2),
                "Marc Forster",
                "Skenario\tMatthew Michael Carnahan\n" +
                        "Drew Goddard\n" +
                        "Damon Lindelof\n" +
                        "Cerita\tMatthew Michael Carnahan\n" +
                        "J. Michael Straczynski",
                "Mantan karyawan Perserikatan Bangsa-Bangsa Gerry Lane dan keluarganya sedang terjebak macet di Philadelphia ketika terdengar laporan dari radio bahwa terjadi wabah rabies di seluruh dunia. Mereka diserang ratusan zombi dan korban tergigit juga berubah menjadi zombi. Keluarga Lane berhasil kabur dan berlindung di kompleks apartemen sambil menunggu diselamatkan oleh helikopter yang dikirimkan mantan kolega Gerry di PBB, Thierry.\n" +
                        "\n" +
                        "Keluarganya berhasil diangkut ke kapal milik Angkatan Laut Amerika Serikat di lepas pantai New York City. Di sana, tim analis dan personel militer sedang mempelajari penyebaran wabah tersebut di seluruh dunia. Seorang ahli virus, Dr. Fassbach, berpendapat bahwa wabah tersebut disebabkan oleh virus yang asal usulnya harus ditemukan agar vaksinnya bisa dikembangkan. Komandan kapal memberitahu Gerry bahwa karena pernah menjadi mantan penyidik di PBB, Gerry ditugaskan menyelidiki virus ini dan membantu Fassback menemukan sumbernya. Agar keluarganya bisa tetap tinggal di kapal, ia terpaksa menerimanya dan pergi ke Camp Humphreys, pangkalan militer di Korea Selatan tempat kata \"zombi\" pertama kali digunakan untuk menyebut wabah ini.\n" +
                        "\n" +
                        "Setibanya di pangkalan, mereka diserang dan Fassbach tidak sengaja menembakkan senjatanya ke dirinya sampai tewas. Di sana Gerry mengetahui dari beberapa korban selamat bahwa zombi tertarik dengan suara. Komandannya juga memberitahu Gerry tentang seorang warga lokal yang mulutnya berbusa menggigit dokternya. Ia memperlihatkan sebuah ruangan penuh jasad terbakar, tempat mereka pertama melihat wabah tersebut. Seorang mantan agen CIA yang dipenjara karena berkhianat menyuruh Gerry pergi ke Yerusalem. Di sana, kepala dinas intelijen Israel Mossad, Jurgen Warmbrunn, telah mendirikan zona aman tepat sebelum wabah diakui secara resmi, artinya Israel sebelumnya sudah tahu tentang peristiwa ini.\n" +
                        "\n" +
                        "Di Yerusalem, Gerry bertemu Warmbrunn. Warmbrunn menjelaskan bahwa beberapa bulan sebelumnya Mossad menguping pembicaraan seorang jenderal militer India yang menyatakan bahwa tentara India sedang memerangi sosok \"tak mati\". Dari informasi tersebut, dibantu program yang mencegah Israel kecolongan seperti sebelumnya, zona karantina didirikan di Israel dan mereka mengizinkan warga sipil yang tidak terinfeksi masuk. Akibat keributan di dalam zona tersebut, ribuan korban dan zombi di luar dinding mulai menumpukkan diri dan memanjat. Kekacauan terjadi dan tentara pengawal Gerry, Segen, digigit. Gerry langsung mengamputasi tangannya agar ia tidak berubah menjadi zombi. Kemudian mereka kabur dari Yerusalem dengan menumpang pesawat.\n" +
                        "\n" +
                        "Setelah menghubungi Thierry, pesawat mereka dialihkan ke Cardiff, Wales, supaya bisa mengunjungi fasilitas riset WHO untuk membantu penelitian vaksinnya. Di dalam pesawat, seorang zombi yang menumpang menyerang, sehingga Gerry terpaksa melemparkan granat ke belakang pesawat. Ledakan tersebut menurunkan tekanan kabin dan semua orang tersedot ke luar. Pesawat jatuh dan Segen dan Gerry selamat. Keduanya tiba di fasilitas tersebut dan Gerry mengungkapkan bahwa korban terinfeksi tidak menggigit orang-orang sakit yang sekarat; mereka dianggap tidak cocok sebagai inang reproduksi virus. Ia bersedia menguji patogen fatal namun dapat disembuhkan pada dirinya. Akan tetapi, gedung penyimpanan patogen tersebut dipenuhi zombi setelah salah seorang dokter tak sengaja menginfeksi dirinya sendiri dan teman-temannya. Gerry berhasil mencapai ruang penyimpanan patogen dan menyuntikkan salah satunya ke tubuhnya. Ia berhasil kembali ke laboratorium utama setelah patogen tersebut membuat dirinya \"tersamarkan\" dari penglihatan para zombi. Para dokter bahagia atas penemuan tersebut, lalu menyembuhkan Gerry setelah tiba di gedung utama.\n" +
                        "\n" +
                        "Gerry bertemu kembali dengan keluarganya di zona aman Freeport, Nova Scotia. Sebuah \"vaksin\" yang diambil dari beberapa patogen mematikan langsung dikembangkan. Vaksin tersebut berperan sebagai samaran bagi warga sipil yang mengungsi dan tentara yang memerangi zombi. Rekaman selanjutnya menampilkan perlawanan terhadap zombi di seluruh dunia. Umat manusia memiliki harapan dan Gerry mengatakan bahwa \"Ini belum berakhir; mendekati pun tidak.\" " +
                        "Sersan Dua Wahyu Insyafiadi dijatuhi hukuman penjara seumur hidup, Prajurit Satu Okto Maure dihukum 15 tahun penjara, dan Prajurit Satu Elias K Waromi dijatuhi hukuman 2,5 tahun penjara dipotong masa tahanan.\n",
                "https://terbit21.cool/world-war-z-2013/"
        );

        tambahFilm(film2, db);
        idFilm++;

        try {
            tempDate = sdFormat.parse("10/12/2014 00:00");
        }catch (ParseException er){
            er.printStackTrace();
        }

        Film film3 = new Film(
                idFilm,
                "I Fine..Thank You, Love You",
                tempDate,
                storeImagesFiles(R.drawable.film3),
                "Mez Tharatorn",
                "Chaiyapruek Chalermpornpanich, Benjamaporn Srabua, Mez Tharatorn",
                "Film ini bercerita tentang Pleng, seorang wanita cantik yang bekerja sebgai seorang tutor/ pengajar bahasa inggris yang diminta pertanggung jawabanya oleh seorang cowok bernama Jim, karna Jim menganggap Pleng telah membuat pacarnya yang bernama Kaya pergi meniggalkanaya. Pada awalnya Pleng hanya berniat membantu Kaya untuk memutuskan hubunganya dengan Jim, Namun karna Jim tidak mengerti bahas inggris, Kaya pun meminta bantuan Pleng, dengan membuat sebuah rekaman dan meminta pleng menerjemahkanya ke dalam bahasa yang dimnegerti Jim",
                "http://www.ifinethankyouloveyou.com"
        );

        tambahFilm(film3, db);
        idFilm++;

        try {
            tempDate = sdFormat.parse("05/12/2017 00:00");
        }catch (ParseException er){
            er.printStackTrace();
        }

        Film film4 = new Film(
                idFilm,
                "Jumanji: Welcome to the Jungle",
                tempDate,
                storeImagesFiles(R.drawable.film4),
                "\tJake Kasdan",
                "Chris McKenna",
                "Ceritanya berkisah tentang empat remaja SMA, Spencer (Alex Wolff), Fridge (Ser'Darius Blain), Bethany (Madison Iseman) dan Martha (Morgan Turner), yang dihukum untuk merapikan sebuah ruangan di sekolah. Di dalamnya terdapat banyak barang yang sudah tak terpakai, termasuk satu buah konsol video game dengan kaset berjudul Jumanji. \n" +
                        "\n" +
                        "Lantaran penasaran, Fridge mengajak yang lain untuk mencoba permainan tersebut. Alih-alih bersenang-senang, keempatnya justru tersedot ke dalam permainan tersebut. Uniknya, mereka berubah menjadi tokoh avatar yang dipilih sebelumnya, yaitu Dr. Smolder Bravestone (Dwayne \"The Rock\" Johnson), Profesor Shelly Oberon (Jack Black), Franklin Finbar (Kevin Hart) dan Ruby Roundhouse (Karen Gillan). \n" +
                        "\n" +
                        "Perubahan konsep Jumanji tersebut terbilang menarik dan sukses diterapkan untuk karya terbaru Jake Kasdan ini. Apalagi yang berubah bukan hanya gagasannya, tetapi juga karakter-karakternya. Dan penampilan serta kolaborasi para aktor utama seperti The Rock, Hart, Black dan Gillan benar-benar menjadi kekuatan utama Jumanji: Welcome to the Jungle. \n" +
                        "\n" +
                        "Pasalnya, empat avatar yang mereka lakoni itu memiliki karakter maupun kemampuan yang amat jauh berbeda dengan Spencer cs di dunia nyata. Misalnya saja Fridge. Aslinya, ia adalah remaja bertubuh tinggi kekar dan atlet football Amerika di sekolah. Tapi, di Jumanji, dirinya berubah menjadi Finbar yang berbadan pendek serta memiliki banyak kelemahan. ",
                "https://terbit21.cool/jumanji-welcome-jungle-2017/"
        );

        tambahFilm(film4, db);
    }

}
