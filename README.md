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

Hukuk temalı hibrit bir idle/simülasyon oyunu.

- **Idle katman**: Stajyer, kâtip, paralegal ve ortak avukat işe alıp
  saniyede otomatik gelir (para) kazanırsınız; uygulama kapalıyken de
  gelir birikir (offline ilerleme, azami 8 saat)
- **Aktif dava simülasyonu**: Bir dava türü seçtiğinizde strateji
  (agresif savunma / dengeli / usul itirazı) ve delil (tanık ifadesi /
  belge / bilirkişi raporu) seçimi yaparsınız; bu seçimler başarı
  şansınızı ve ödülü belirler, ardından dava sonuçlanır
  (kazanma/kaybetme)
- İtibar puanı arttıkça yeni ve daha büyük dava türleri açılır
  (Sulh Hukuk → İş Hukuku → Ticari → Ağır Ceza → Yargıtay)
- **Kariyer/prestij sistemi**: Yeterli itibara ulaşınca "Kariyer
  İlerlet" ile para ve kadronuzu sıfırlayıp kalıcı gelir çarpanı
  (ünvan) kazanabilirsiniz (Stajyer Avukat → ... → Baro Başkanı)
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
