# ccawsme — Kişisel Android Uygulamaları

Bu repo, bir avukatın günlük işini kolaylaştıracak küçük Android
uygulamalarını barındıran çok modüllü (multi-module) bir Gradle projesidir.
Her modül bağımsız bir uygulama olarak derlenip telefona kurulabilir.

## Modüller

### `app` — Emsal Defterim

Beğenilen emsal kararları ve kişisel notları kaydedip arayabileceğiniz,
tamamen cihaz üzerinde çalışan (offline-first) bir uygulama.

- Emsal karar ekleme/düzenleme: başlık, mahkeme/daire, karar no, karar
  tarihi, özet, emsal metni, kişisel notlar ve etiketler
- Başlık, mahkeme, özet, metin, not ve etiketlerde anlık arama
- Favori işaretleme, detayda metni panoya kopyalama
- Veriler yalnızca cihazda (Room/SQLite) saklanır, internet gerekmez

### `davaustasi` — Dava Ustası

Hukuk temalı, Sims tarzı bir kariyer yaşam simülasyonu.

- **Karakter oluşturma**: İsminizi girip cilt tonu, saç rengi ve
  kıyafet rengini seçerek kendi avukat karakterinizi yaratırsınız
  (basit, özelleştirilebilir bir portre — Compose Canvas ile çizilir,
  dış görsel/asset gerekmez)
- **Günlük yaşam döngüsü**: Her gün Sabah / Öğlen / Akşam / Gece
  olmak üzere 4 zaman dilimine bölünür; her dilimde Çalış, Dava Al,
  Dinlen, Ders Çalış, Sosyalleş veya Uyu aktivitelerinden birini
  seçersiniz. Her aktivite ikon kartlarıyla sunulur, uzun metin
  diyalogları yoktur
- **İstatistik yönetimi**: Enerji, Mutluluk ve Bilgi çubuklarını
  dengede tutmanız gerekir; aktiviteler bu değerleri artırıp azaltır,
  enerjiniz yetersizse bazı aktiviteler kapanır
- **Konum sahnesi**: Karakteriniz o an bulunduğu yere (Ev / Ofis /
  Mahkeme / Dışarısı) göre değişen, renk geçişleriyle animasyonlu bir
  sahnede gösterilir
- **Kariyer ilerlemesi**: Kariyer puanı biriktirdikçe otomatik olarak
  terfi alırsınız (Hukuk Stajyeri → Avukat Yardımcısı → Avukat →
  Kıdemli Avukat → Ortak Avukat → Baro Başkanı), her unvan "Çalış"
  gelirinizi artırır
- **Dava Al**: Avukat unvanına ulaştıktan sonra açılan riskli/yüksek
  ödüllü bir aktivite; başarı şansı bilgi ve itibarınıza göre hesaplanır
- Tüm ilerleme cihazda (DataStore) saklanır, internet gerekmez

## Teknoloji

- Kotlin, Jetpack Compose (Material 3)
- `app`: Room (yerel veritabanı), Navigation Compose
- `davaustasi`: DataStore Preferences (oyun durumu kalıcılığı)
- Her iki modülde de ViewModel + StateFlow (MVVM)

## Geliştirme ortamı

- Android Studio (Koala veya üzeri önerilir)
- JDK 17
- minSdk 26, targetSdk/compileSdk 34

Projeyi Android Studio ile açıp senkronize ettikten sonra istediğiniz
modülü (`app` ya da `davaustasi`) doğrudan çalıştırabilirsiniz. Bu proje
bir sandbox ortamında (Google'ın Maven deposuna ağ erişimi olmadan)
hazırlandığı için burada `gradlew build` ile derleme doğrulanamadı;
GitHub Actions üzerindeki "APK Derle" / "Dava Ustası APK Derle"
workflow'ları her push'ta ilgili modülün debug APK'sını derleyip
Artifacts olarak yayınlar — telefonunuza kurmak için oradan
indirebilirsiniz.
