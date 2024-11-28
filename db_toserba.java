import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class db_toserba {
  public static String[][] products = {
      {"1","Panci Multi Guna", "250000", "Panci stainless steel anti karat dengan kapasitas 5 liter, cocok untuk berbagai jenis masakan", "50", "1", "1"},
      {"2","Blender Juice", "450000", "Blender dengan pisau tajam 6 mata, cocok untuk membuat jus dan smoothie", "30", "1", "1"},
      {"3","Earphone Wireless", "899000", "Earphone bluetooth dengan noise cancelling dan battery life 24 jam", "100", "1", "2"},
      {"4","Power Bank 10000mAh", "299000", "Power bank dengan kapasitas 10000mAh, mendukung fast charging", "75", "1", "2"},
      {"5","Serum Vitamin C", "189000", "Serum wajah dengan kandungan Vitamin C untuk mencerahkan kulit", "200", "1", "3"},
      {"6","Sunscreen SPF 50", "85000", "Tabir surya dengan SPF 50 dan PA++++ untuk perlindungan maksimal", "150", "1", "3"},
      {"7","Vitamin D3 1000IU", "125000", "Suplemen Vitamin D3 untuk kesehatan tulang, botol isi 60 tablet", "100", "1", "4"},
      {"8","Masker KN95", "75000", "Masker KN95 5 lapis, isi 10 pcs per pack", "300", "1", "4"},
      {"9","Puzzle 1000 Pcs", "199000", "Puzzle pemandangan alam dengan 1000 kepingan", "25", "1", "5"},
      {"10","Robot RC", "450000", "Robot remote control dengan baterai rechargeable", "15", "1", "5"},
      {"11","Kompor Listrik", "600000", "Kompor listrik 2 tungku dengan pengatur suhu digital, hemat energi", "40", "1", "1"},
      {"12","Setrika Uap", "450000", "Setrika uap berkualitas tinggi dengan kapasitas air 300ml", "35", "1", "1"},
      {"13","Smart TV 32 inch", "2500000", "Smart TV LED resolusi HD dengan fitur WiFi dan streaming", "24", "1", "2"},
      {"14","Laptop Entry Level", "5000000", "Laptop untuk kebutuhan perkantoran dengan prosesor Intel Core i3", "20", "1", "2"},
      {"15","Wireless Charger", "250000", "Charging pad dengan teknologi fast charging untuk smartphone", "60", "1", "2"},
      {"16","Pembersih Wajah", "95000", "Pembersih wajah dengan bahan alami, cocok untuk semua jenis kulit", "100", "1", "3"},
      {"17","Lipstik Matte", "125000", "Lipstik dengan warna tahan lama dan finish matte", "80", "1", "3"},
      {"18","Masker Rambut", "75000", "Masker rambut nutrisi untuk perawatan rambut kering dan rusak", "50", "1", "3"},
      {"19","Minyak Ikan Omega 3", "180000", "Suplemen minyak ikan dengan kandungan EPA dan DHA tinggi, isi 60 kapsul", "74", "1", "4"},
      {"20","Masker KF94", "65000", "Masker KF94 premium, isi 10 pcs, filtrasi tinggi", "198", "1", "4"},
      {"21","Vitamin C 1000mg", "95000", "Suplemen Vitamin C dengan dosis tinggi, mendukung sistem imun", "90", "1", "4"},
      {"22","Drone Pemula", "1200000", "Drone dengan kamera HD dan fitur stabilisasi, cocok untuk pemula", "15", "1", "5"},
      {"23","Board Game Keluarga", "350000", "Board game strategis untuk 2-6 pemain, cocok untuk malam keluarga", "25", "1", "5"},
      {"24","Alat Sulap Profesional", "500000", "Set alat sulap lengkap dengan panduan untuk pemula hingga mahir", "20", "1", "5"},
      {"25","Hand Sanitizer", "85000", "Pembersih tangan, anti bakteri", "0", "0", "4"}
  };

  public static void main(String[] args) {
    menu();
  }

  public static void menu() {
    Scanner scanner = new Scanner(System.in);
    Boolean isRunning = true;

    String[][] orders = new String[100][];
    String[][] orderDetails = new String[100][];

    System.out.print("\nMasukkan ID Anda: ");
    String userId = scanner.nextLine();
    
    if (checkUser(userId)) {
      while (isRunning) {
        System.out.print("\n-------- Menu --------\n1. Tampilkan produk\n2. Beli produk\n3. Tampilkan order\n0. Tutup prompt\n\nPilih nomor: ");
        Integer choice = scanner.nextInt();
  
        switch (choice) {
          case 1:
            displayProducts();
            break;
          case 2:
            System.out.print("Berapa produk yang ingin dipesan: ");
            int numProducts = scanner.nextInt();
            scanner.nextLine();
  
            String[] productName = new String[numProducts];
            Integer[] quantities = new Integer[numProducts];
  
            for (int i = 0; i < numProducts; i++) {
                System.out.printf("Masukkan nama produk ke-%d:\n", i + 1);
                productName[i] = scanner.nextLine();
                System.out.printf("Masukkan jumlah untuk produk ke-%d: ", i + 1);
                quantities[i] = Integer.parseInt(scanner.nextLine());
            }
  
            displayPayments();
            System.out.print("\nPilih pembayaran: ");
            Integer paymentId = scanner.nextInt();
  
            Integer orderId = getNextIndex(orders);
            orders = createOrders(orders, orderId, paymentId, userId);
  
            for (Integer i = 0; i < numProducts; i++) {
              orderDetails = createOrderDetails(orderDetails, orderId, productName[i], quantities[i]);
            }
            break;
          case 3:
            displayOrders(orders, orderDetails);
            break;
          case 0:
            isRunning = false;
            scanner.close();
            break;
          default:
            System.out.println("Pilihan tidak valid. Silakan coba lagi");
            break;
        }
      }
    }
  }

  public static void displayProducts() {
    // String[][] products = getProducts();
    for (int i = 0; i < products.length; i++) {
      if (products[i][4] != "0") {
        System.out.printf(
          "Nomor Produk: %d\nNama Produk: %s\nHarga: %d\nDeskripsi: %s\nKuantitas: %d\n\n",
          Integer.parseInt(products[i][0]),
          products[i][1],
          Integer.parseInt(products[i][2]),
          products[i][3],
          Integer.parseInt(products[i][4])
        );
      }
    } 
  }

  public static void displayPayments() {
    System.out.println();
    String[][] payments = getPayments();
    for (String[] i : payments) {
      System.out.println(i[0] + ". " + i[1]);
    }
  }

  public static void displayOrders(String[][] orders, String[][] orderDetails) {
    String[][] payments = getPayments();
    String[][] status = getStatusOrder();
    // String[][] products = getProducts();

    System.out.println("\n----- Daftar Order -----");

    for (int i = 0; i < orders.length; i++) {
      if (orders[i] != null) {
        System.out.printf("\nOrder ID: %s\nLocation: %s\nExpired at: %s\nCreated at: %s\nUpdated at: %s\nUser ID: %s\nPayment: %s\nStatus: %s\nKurir: %s",
          orders[i][0],
          orders[i][1],
          orders[i][2],
          orders[i][3],
          orders[i][4],
          orders[i][5],
          payments[Integer.parseInt(orders[i][6])-1][1],
          status[Integer.parseInt(orders[i][7])-1][1],
          orders[i][8]
        );

        for (int j = 0; j < orderDetails.length; j++) {
          if (orderDetails[j] != null && orderDetails[j][4].equals(orders[i][0])) {
            System.out.printf("\n\nProduk: %s x%s\nTotal pembayaran: Rp.%d",
              products[Integer.parseInt(orderDetails[j][3])-1][1],
              orderDetails[j][0],
              Integer.parseInt(orderDetails[j][0]) * Integer.parseInt(orderDetails[j][1])
            );
          }
        }
        System.out.println("\n----------------------");
      }
    }
  }

  public static String getTimestampNow() {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return now.format(formatter);
  }

  public static String getDeadlinePayments() {
      LocalDateTime now = LocalDateTime.now();
      LocalDateTime oneHourLater = now.plusHours(1);
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      return oneHourLater.format(formatter);
  }

  public static Integer getNextIndex(String[][] array) {
    for (Integer i=0; i < array.length; i++) {
      if (array[i] == null) {
        return i;
      }
    }
    return -1;
  }

  public static String[][] createOrders(String[][] orders, Integer orderId, Integer paymentId, String userId) {
    String[] orderData = {
      String.valueOf(orderId + 1),
      "Belum dalam pengiriman", 
      getDeadlinePayments(), 
      getTimestampNow(), 
      getTimestampNow(), 
      userId, 
      String.valueOf(paymentId),  
      "1",
      "Belum dalam pengiriman"
    };

    orders[orderId] = orderData;
    return orders;
  }

  public static String[][] createOrderDetails(String[][] orderDetails, Integer orderId, String productName, Integer qty) {
    String[] productByName = getProductsByName(productName, qty);

    String[] orderDetailsData = {
      String.valueOf(productByName[4]),
      String.valueOf(productByName[2]),
      "0",
      String.valueOf(productByName[0]),
      String.valueOf(orderId + 1) 
    };

    Integer detailIndex = getNextIndex(orderDetails);
    orderDetails[detailIndex] = orderDetailsData;
    return orderDetails;
  }

  public static Boolean checkUser(String userId) {
    String[][] user = getUser(); 
    if (Integer.parseInt(userId) > user.length) {
      return false;
    }
    return true;
  }

  public static String[][] getPayments() {
    return new String[][] {
      {"1", "Akun Virtual"},
      {"2", "Paylater"},
      {"3", "COD"}
    };
  }

  // public static String[][] getProducts() {
  //   return new String[][] {
  //     {"1","Panci Multi Guna", "250000", "Panci stainless steel anti karat dengan kapasitas 5 liter, cocok untuk berbagai jenis masakan", "50", "1", "1"},
  //     {"2","Blender Juice", "450000", "Blender dengan pisau tajam 6 mata, cocok untuk membuat jus dan smoothie", "30", "1", "1"},
  //     {"3","Earphone Wireless", "899000", "Earphone bluetooth dengan noise cancelling dan battery life 24 jam", "100", "1", "2"},
  //     {"4","Power Bank 10000mAh", "299000", "Power bank dengan kapasitas 10000mAh, mendukung fast charging", "75", "1", "2"},
  //     {"5","Serum Vitamin C", "189000", "Serum wajah dengan kandungan Vitamin C untuk mencerahkan kulit", "200", "1", "3"},
  //     {"6","Sunscreen SPF 50", "85000", "Tabir surya dengan SPF 50 dan PA++++ untuk perlindungan maksimal", "150", "1", "3"},
  //     {"7","Vitamin D3 1000IU", "125000", "Suplemen Vitamin D3 untuk kesehatan tulang, botol isi 60 tablet", "100", "1", "4"},
  //     {"8","Masker KN95", "75000", "Masker KN95 5 lapis, isi 10 pcs per pack", "300", "1", "4"},
  //     {"9","Puzzle 1000 Pcs", "199000", "Puzzle pemandangan alam dengan 1000 kepingan", "25", "1", "5"},
  //     {"10","Robot RC", "450000", "Robot remote control dengan baterai rechargeable", "15", "1", "5"},
  //     {"11","Kompor Listrik", "600000", "Kompor listrik 2 tungku dengan pengatur suhu digital, hemat energi", "40", "1", "1"},
  //     {"12","Setrika Uap", "450000", "Setrika uap berkualitas tinggi dengan kapasitas air 300ml", "35", "1", "1"},
  //     {"13","Smart TV 32 inch", "2500000", "Smart TV LED resolusi HD dengan fitur WiFi dan streaming", "24", "1", "2"},
  //     {"14","Laptop Entry Level", "5000000", "Laptop untuk kebutuhan perkantoran dengan prosesor Intel Core i3", "20", "1", "2"},
  //     {"15","Wireless Charger", "250000", "Charging pad dengan teknologi fast charging untuk smartphone", "60", "1", "2"},
  //     {"16","Pembersih Wajah", "95000", "Pembersih wajah dengan bahan alami, cocok untuk semua jenis kulit", "100", "1", "3"},
  //     {"17","Lipstik Matte", "125000", "Lipstik dengan warna tahan lama dan finish matte", "80", "1", "3"},
  //     {"18","Masker Rambut", "75000", "Masker rambut nutrisi untuk perawatan rambut kering dan rusak", "50", "1", "3"},
  //     {"19","Minyak Ikan Omega 3", "180000", "Suplemen minyak ikan dengan kandungan EPA dan DHA tinggi, isi 60 kapsul", "74", "1", "4"},
  //     {"20","Masker KF94", "65000", "Masker KF94 premium, isi 10 pcs, filtrasi tinggi", "198", "1", "4"},
  //     {"21","Vitamin C 1000mg", "95000", "Suplemen Vitamin C dengan dosis tinggi, mendukung sistem imun", "90", "1", "4"},
  //     {"22","Drone Pemula", "1200000", "Drone dengan kamera HD dan fitur stabilisasi, cocok untuk pemula", "15", "1", "5"},
  //     {"23","Board Game Keluarga", "350000", "Board game strategis untuk 2-6 pemain, cocok untuk malam keluarga", "25", "1", "5"},
  //     {"24","Alat Sulap Profesional", "500000", "Set alat sulap lengkap dengan panduan untuk pemula hingga mahir", "20", "1", "5"},
  //     {"25","Hand Sanitizer", "85000", "Pembersih tangan, anti bakteri", "0", "0", "4"}
  //   };
  // }

  public static String[] getProductsByName(String productName, Integer qty) {
    String[] detailProduct = null;

    for (String[] product : products) {
        if (product[1].equalsIgnoreCase(productName)) {
            detailProduct = product.clone(); // Buat salinan untuk menghindari referensi langsung
            break;
        }
    }

    if (detailProduct == null) {
        System.out.println("Produk tidak ditemukan!");
        return null;
    }

    int productStocks = Integer.parseInt(detailProduct[4]);

    int booked = Math.min(productStocks, qty);

    for (String[] product : products) {
        if (product[0].equals(detailProduct[0])) {
            product[4] = String.valueOf(productStocks - booked);
            break;
        }
    }

    detailProduct[4] = String.valueOf(booked);
    return detailProduct;
  }

  public static String[][] getStatusOrder() {
    return new String[][] {
      {"1", "Menunggu pembayaran"},
      {"2", "Proses"},
      {"3", "Dikirim"},
      {"4", "Selesai"},
      {"5", "Dikembalikan"}
    };
  }

  public static String[][] getUser() {
    return new String[][] {
      {"1", "Surya", "Wijaya", "Jl. Gatot Subroto No. 123, RT 02 RW 03, Kuningan, Jakarta Selatan"},
      {"2", "Dewi", "Kusuma", "Jl. Diponegoro Blok A5 No. 45, Tegalsari, Surabaya, Jawa Timur"},
      {"3", "Budi", "Santoso", "Komplek Griya Indah Blok C2 No. 17, Medan Selayang, Medan, Sumatera Utara"},
      {"4", "Ratna", "Sari", "Jl. Ahmad Yani Gang Melati No. 8, Denpasar Utara, Bali"},
      {"5", "Ahmad", "Hidayat", "Perumahan Bumi Asri Blok D4 No. 12, Bandung Timur, Jawa Barat"},
      {"6", "Siti", "Rahma", "Jl. Sudirman No. 234, RT 05 RW 02, Palembang, Sumatera Selatan"},
      {"7", "Eko", "Prasetyo", "Jl. Pahlawan No. 56, Malang, Jawa Timur"},
      {"8", "Rina", "Wulandari", "Komplek Villa Sentosa Blok F7 No. 23, Tangerang Selatan, Banten"},
      {"9", "Agus", "Setiawan", "Jl. Pemuda No. 78, RT 03 RW 04, Semarang, Jawa Tengah"},
      {"10", "Maya", "Putri", "Perumahan Graha Asri Blok B3 No. 15, Makassar, Sulawesi Selatan"}
    };
  }
}


