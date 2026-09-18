# Emsal Defterim

Avukatlar için beğendikleri emsal kararları ve kişisel notlarını kaydedip
arayabilecekleri, tamamen cihaz üzerinde çalışan (offline-first) bir Android
uygulaması.

## Özellikler

- Emsal karar ekleme/düzenleme: başlık, mahkeme/daire, karar no, karar
  tarihi, özet, emsal metni, kişisel notlar ve etiketler
- Başlık, mahkeme, özet, metin, not ve etiketlerde anlık arama
- Favori işaretleme
- Detay ekranında emsal metnini panoya kopyalama
- Tüm veriler yalnızca cihazda (Room/SQLite) saklanır, internet gerekmez

## Teknoloji

- Kotlin, Jetpack Compose (Material 3)
- Room (yerel veritabanı)
- Navigation Compose, ViewModel + StateFlow

## Geliştirme ortamı

- Android Studio (Koala veya üzeri önerilir)
- JDK 17
- minSdk 26, targetSdk/compileSdk 34

Projeyi Android Studio ile açıp senkronize ettikten sonra doğrudan
çalıştırabilirsiniz. Bu proje bir sandbox ortamında (Google'ın Maven
deposuna ağ erişimi olmadan) hazırlandığı için burada `gradlew build`
ile derleme doğrulanamadı; ilk derlemeyi Android Studio'da yapmanız
gerekir.
